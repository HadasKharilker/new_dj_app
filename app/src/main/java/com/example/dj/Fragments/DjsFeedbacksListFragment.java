package com.example.dj.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.dj.Activities.Feedback;
import com.example.dj.Activities.User;
import com.example.dj.Adapters.FeedbackAdapter;
import com.example.dj.Adapters.SongAdapter;
import com.example.dj.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DjsFeedbacksListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DjsFeedbacksListFragment extends Fragment {
    private ListView feedbacksList;
    private String userId;
    private static String nameOfRater;
    private FeedbackAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DjsFeedbacksListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DjsFeedbacksListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DjsFeedbacksListFragment newInstance(String param1, String param2) {
        DjsFeedbacksListFragment fragment = new DjsFeedbacksListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_djs__feedback_list__fregment, container, false);


        userId = this.getArguments().getString("data");

        feedbacksList = (ListView) view.findViewById(R.id.idListView);
        initFeedbacksList(view);

        return view;

    }

    private void initFeedbacksList(View view) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("djFeedback");
        DatabaseReference songsRef = rootRef.child(userId);//users branch reference from firebase database
        List<Feedback> list = new ArrayList<>();
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {//for each LOOP running through all feedbacks
                    Map<String, Object> mHashmap = new HashMap<>();
                    mHashmap = (Map<String, Object>)ds.getValue();//all the feedbacks of the current dj
                    String nameOfRater=String.valueOf(mHashmap.get("nameOfRater"));

                    String feedBackText= String.valueOf(mHashmap.get("feedBackText"));
                    String rating=String.valueOf(mHashmap.get("rating"));

                    Feedback feedback = new Feedback(nameOfRater,rating,feedBackText);
                    list.add(feedback);//Adding songs name to the list


                }

                adapter = new FeedbackAdapter(getActivity(), android.R.layout.simple_gallery_item, list);//adapting the data int the array list to listView data
                feedbacksList.setAdapter(adapter);//setting the adapter to the list view
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        songsRef.addListenerForSingleValueEvent(eventListener);


    }
}