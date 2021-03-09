package com.example.dj.Adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.dj.Activities.Feedback;
import com.example.dj.R;

import java.util.List;

public class FeedbackAdapter extends ArrayAdapter<Feedback>{
    private static final String TAG = FeedbackAdapter.class.getSimpleName();

    List<Feedback> feedBackList;

    public FeedbackAdapter(Context context, int resource, List<Feedback> objects) {
        super(context, resource, objects);


        feedBackList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        Feedback currentFeedback = feedBackList.get(position);

        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.feedback_list_item, parent, false);
        }

        TextView nameTextView = (TextView) listItemView.findViewById(R.id.rater_name_text_view);
        nameTextView.setText(currentFeedback.getNameOfRater());

        TextView feedbackTextView = (TextView) listItemView.findViewById(R.id.feedback_text_view);
        feedbackTextView.setText(currentFeedback.getFeedBackText());

       final RatingBar rating=(RatingBar) listItemView.findViewById(R.id.rating);
        rating.setRating(Float.valueOf(currentFeedback.getRating()));





        return listItemView;
    }


}
