package com.example.sachin.moviezilla;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by SACHIN on 13-05-2016.
 */
public class CustomList extends BaseAdapter {

    private Activity activity;
    private List<ReviewModel> reviewList;
    private LayoutInflater inflater;

    public CustomList(Activity activity, List<ReviewModel> reviewList){
        this.activity = activity;
        this.reviewList = reviewList;
    }

    @Override
    public int getCount() {
        return reviewList.size();
    }

    @Override
    public Object getItem(int position) {
        return reviewList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(inflater == null){
            inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }if(convertView == null){
            convertView = inflater.inflate(R.layout.reviews_layout, null);
        }

        TextView author = (TextView) convertView.findViewById(R.id.textAuthor);
        TextView review = (TextView) convertView.findViewById(R.id.textReview);

        ReviewModel rev = reviewList.get(position);

        author.setText(rev.getAuthor());
        review.setText(rev.getReview());

        return convertView;
    }
}