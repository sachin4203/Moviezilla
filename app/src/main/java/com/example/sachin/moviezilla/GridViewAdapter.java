package com.example.sachin.moviezilla;

/**
 * Created by SACHIN on 07-03-2016.
 */

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.PersonViewHolder> {

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;

        TextView personAge;
        ImageView personPhoto;
        TextView ratings;


        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            personPhoto = (ImageView)itemView.findViewById(R.id.grid_item_image);
            personAge = (TextView)itemView.findViewById(R.id.person_age);
           ratings = (TextView)itemView.findViewById(R.id.ratings);

        }
    }

    List<GridItem> persons;
    Context mContext;

    GridViewAdapter(List<GridItem> persons,Context mContext){
        this.persons = persons;
        this.mContext=mContext;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_item_layout, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {

        personViewHolder.personAge.setText(persons.get(i).original_title);
        //personViewHolder.personPhoto.setImageResource(persons.get(i).poster_path);
        personViewHolder.ratings.setText(Double.toString(persons.get(i).vote_average));
        Picasso.with(mContext).load(persons.get(i).backdrop_path).into( personViewHolder.personPhoto);
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }
}