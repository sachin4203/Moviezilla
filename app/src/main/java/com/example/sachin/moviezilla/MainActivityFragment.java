package com.example.sachin.moviezilla;

import android.content.ContentProviderOperation;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.sachin.moviezilla.data.FavouriteMovieColumns;
import com.example.sachin.moviezilla.data.PlanetProvider;
import com.example.sachin.moviezilla.model.Movie;
import com.twotoasters.jazzylistview.JazzyGridView;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public MainActivityFragment() {
    }
    RestClient restClient;
    private static final String LOG_TAG = MainActivityFragment.class.getSimpleName();
    private JazzyGridView mGridView;
    private ProgressBar mProgressBar;
    private GridViewAdapter mGridAdapter;
    private ArrayList<GridItem> mGridData;
    private RelativeLayout mRelativeLayout;
    private static final int CURSOR_LOADER_ID = 0;
    private RecyclerView rv;
    String downloadType;

   /* @Override
    public void onActivityCreated(Bundle savedInstanceState){
       *//* Cursor c = getActivity().getContentResolver().query(PlanetProvider.FavouriteMovies.CONTENT_URI,
                null, null, null, null);
        Log.i(LOG_TAG, "cursor count: " + c.getCount());
        if (c == null || c.getCount() == 0){
         //   insertData();
        }*//*


       // getLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
    }
*/



    @Override
    public void onCreate(Bundle savedInstanceState) {
       Cursor c = getActivity().getContentResolver().query(PlanetProvider.FavouriteMovies.CONTENT_URI,
        null, null, null, null);
        Log.i(LOG_TAG, "cursor count: " + c.getCount());
        if (c == null || c.getCount() == 0){
            // insertData();
            Toast.makeText(getActivity(), "Zerooooo", Toast.LENGTH_LONG).show();
        }


         getLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);
      super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
       // setHasOptionsMenu(true);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        rv=(RecyclerView)view.findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

       // mGridView = (JazzyGridView) view.findViewById(R.id.gridView);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mRelativeLayout= (RelativeLayout)view.findViewById(R.id.container);
        //  mGridView.setTransitionEffect(JazzyHelper.HELIX);
        mProgressBar.setVisibility(View.VISIBLE);
        mRelativeLayout.setVisibility(View.GONE);
        //Initialize with empty data
        mGridData = new ArrayList<>();
        mGridAdapter = new GridViewAdapter(mGridData,getActivity() );
       rv.setAdapter(mGridAdapter);

        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click
                        Intent intent = new Intent(getActivity(), MovieDetail.class);
                        intent.putExtra(getString(R.string.in_title), mGridData.get(position).getOriginal_title());
                        intent.putExtra(getString(R.string.in_poster), mGridData.get(position).getPoster_path());
                        intent.putExtra(getString(R.string.in_release), mGridData.get(position).getRelease_date());
                        intent.putExtra(getString(R.string.in_overview), mGridData.get(position).getOverview());
                        intent.putExtra(getString(R.string.in_vote), mGridData.get(position).getVote_average());
                        intent.putExtra(getString(R.string.in_backdrop), mGridData.get(position).getBackdrop_path());
                        //insertData();
                        startActivity(intent);
                    }
                })
        );


       /* mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent = new Intent(getActivity(), MovieDetail.class);
                intent.putExtra(getString(R.string.in_title), mGridData.get(position).getOriginal_title());
                intent.putExtra(getString(R.string.in_poster), mGridData.get(position).getPoster_path());
                intent.putExtra(getString(R.string.in_release), mGridData.get(position).getRelease_date());
                intent.putExtra(getString(R.string.in_overview), mGridData.get(position).getOverview());
                intent.putExtra(getString(R.string.in_vote), mGridData.get(position).getVote_average());
                intent.putExtra(getString(R.string.in_backdrop), mGridData.get(position).getBackdrop_path());
                insertData();
                startActivity(intent);
            }
        });*/

        return view;

    }

    public void fetchMov(Movie movie){
        mProgressBar.setVisibility(View.GONE);
        GridItem item;
        if (movie.results != null && movie.results.size() > 0) {
            for (int i = 0; i < movie.results.size(); i++) {
                        /*result += "Title: " + movie.results.get(i).original_title +'\n' +
                                "Overveiw :" + movie.results.get(i).overview +'\n';*/
                item = new GridItem();
                String poster = "http://image.tmdb.org/t/p/w300" + movie.results.get(i).poster_path;
                item.setPoster_path(poster);
                String backdrop = "http://image.tmdb.org/t/p/w500" + movie.results.get(i).backdrop_path;
                item.setBackdrop_path(backdrop);
                String title = movie.results.get(i).original_title;
                item.setOriginal_title(title);
                String overview = movie.results.get(i).overview;
                item.setOverview(overview);
                double voteAverage = movie.results.get(i).vote_average;
                item.setVote_average(voteAverage);
                String releaseDate = movie.results.get(i).release_date;
                item.setRelease_date(releaseDate);
                mGridData.add(item);

            }
        }

        //mGridAdapter.setGridData(mGridData);
        mGridAdapter = new GridViewAdapter(mGridData,getActivity() );
        rv.setAdapter(mGridAdapter);
    }



    @Override
    public void onResume(){
        super.onResume();
        Log.d(LOG_TAG, "resume called");

        getLoaderManager().restartLoader(CURSOR_LOADER_ID, null, this);
        restClient = new RestClient();
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getActivity());

        downloadType = SP.getString(getString(R.string.movieKey),(getString(R.string.type_popular)));
        if(downloadType!=null && downloadType.equalsIgnoreCase((getString(R.string.type_highRated))))
        {
            mGridData.clear();
            restClient.getService().fetchMovies(new Callback<Movie>() {
                @Override
                public void success(Movie movie, Response response) {
                /*String result = "";*/

                    mProgressBar.setVisibility(View.GONE);
                    mRelativeLayout.setVisibility(View.VISIBLE);
                    fetchMov(movie);

                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getActivity(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });

        }
        else if(downloadType!=null && downloadType.equalsIgnoreCase((getString(R.string.type_popular))))
        {
            mGridData.clear();
            restClient.getService().fetchPopularMovies(new Callback<Movie>() {
                @Override
                public void success(Movie movie, Response response) {
                /*String result = "";*/
                    mProgressBar.setVisibility(View.GONE);
                    mRelativeLayout.setVisibility(View.VISIBLE);
                    fetchMov(movie);

                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getActivity(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });

        }






    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {


            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void insertData(){
        Log.d(LOG_TAG, "insert");
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>(mGridData.size());

        for (GridItem planet : mGridData){
            ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                    PlanetProvider.FavouriteMovies.CONTENT_URI);
            builder.withValue(FavouriteMovieColumns.TITLE, planet.getOriginal_title());
            builder.withValue(FavouriteMovieColumns.OVERVIEW, planet.getOverview());
            builder.withValue(FavouriteMovieColumns.POSTER_PATH, planet.getPoster_path());
            builder.withValue(FavouriteMovieColumns.RELEASE_DATE, planet.getRelease_date());
            builder.withValue(FavouriteMovieColumns.VOTE_AVERAGE, planet.getVote_average());
            batchOperations.add(builder.build());
        }

        try{
            getActivity().getContentResolver().applyBatch(PlanetProvider.AUTHORITY, batchOperations);
        } catch(RemoteException | OperationApplicationException e){
            Log.e(LOG_TAG, "Error applying batch insert", e);

        }

    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args){
        return new CursorLoader(getActivity(), PlanetProvider.FavouriteMovies.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data){
       // mCursorAdapter.swapCursor(data);
        if(downloadType!=null && downloadType.equalsIgnoreCase((getString(R.string.type_favourite)))) {
            Toast.makeText(getActivity(), "Holoaaaaa HAHAAH", Toast.LENGTH_LONG).show();
            mProgressBar.setVisibility(View.GONE);
            mRelativeLayout.setVisibility(View.VISIBLE);
            data.moveToFirst();
            GridItem item;
            mGridData.clear();
            while (!data.isAfterLast()) {

                item = new GridItem();
                String poster = data.getString(data.getColumnIndex(
                        FavouriteMovieColumns.POSTER_PATH));
                item.setPoster_path(poster);
                String backdrop = data.getString(data.getColumnIndex(
                        FavouriteMovieColumns.BACK_DROP));
                item.setBackdrop_path(backdrop);
                String title = data.getString(data.getColumnIndex(
                        FavouriteMovieColumns.TITLE));
                item.setOriginal_title(title);
                String overview = data.getString(data.getColumnIndex(
                        FavouriteMovieColumns.OVERVIEW));
                item.setOverview(overview);
                double voteAverage = data.getDouble(data.getColumnIndex(
                        FavouriteMovieColumns.VOTE_AVERAGE));
                String releaseDate = data.getString(data.getColumnIndex(
                        FavouriteMovieColumns.RELEASE_DATE));
                item.setRelease_date(releaseDate);
                mGridData.add(item);




                data.moveToNext();
            }
            mGridAdapter = new GridViewAdapter(mGridData,getActivity() );
            rv.setAdapter(mGridAdapter);

        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader){
       // mCursorAdapter.swapCursor(null);
        mGridAdapter = new GridViewAdapter(mGridData,getActivity() );
        rv.setAdapter(mGridAdapter);
    }
}




