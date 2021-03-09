package com.example.dj.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.dj.Activities.Clubber2Activity;
import com.example.dj.Activities.Feedback;
import com.example.dj.Activities.User;
import com.example.dj.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeedbackFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedbackFragment extends Fragment {
    private String djName;
    private String djUID;
    private String curUserName;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FeedbackFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedbackFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedbackFragment newInstance(String param1, String param2) {
        FeedbackFragment fragment = new FeedbackFragment();
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
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        // פעולות נעשה פה
        curUserName = this.getArguments().getString("data");


        SubmitFeedbackToDjListener(view);




        return  view;
    }

    public void SubmitFeedbackToDjListener(View view){

        Button submitButton= view.findViewById(R.id.sendFeedback);
        submitButton.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                Clubber2Activity clubber2Activity =(Clubber2Activity)getActivity();

                //extract the djID FROM FIREBASE BASED ON NAME
                djName=clubber2Activity.getNameOfDj();
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference songsRef = rootRef.child("users");//users branch reference from firebase database

                ValueEventListener eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {//for each LOOP running through all songs

                            String fullName = ds.child("fullName").getValue(String.class);
                            if(fullName.equals(djName)){ //
                                djUID=ds.child("id").getValue(String.class); //extrecting dj id
                                //attach the feedback to a dj
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                //going to the relevant branch in the db

                                final DatabaseReference myRef = database.getReference("djFeedback");
                                ValueEventListener eventListener1=(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        final  RatingBar ratingBar=  (RatingBar)view.findViewById(R.id.ratingBar);
                                        final  EditText feedbackText= view.findViewById(R.id.textFeedback);
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        String uid = user.getUid();
                                        //check if there is a dj playlist allready exist
                                        if (snapshot.hasChild(djUID))
                                        {
                                            for(DataSnapshot ds : snapshot.getChildren()){
                                                //update current dj playlist
                                                Feedback feedback=new Feedback(curUserName,String.valueOf((int)ratingBar.getRating()),feedbackText.getText().toString());
                                                Map<String, Object> mHashmap = new HashMap<>();
                                                mHashmap = (Map<String, Object>) snapshot.child(djUID).getValue();
                                                mHashmap.put(uid,feedback);
                                                database.getReference("djFeedback").child(djUID).updateChildren(mHashmap);



                                            }


                                        }
                                        //in case this is the first playlist of this DJ
                                        else{
                                            Feedback feedback=new Feedback(curUserName,String.valueOf((int)ratingBar.getRating()),feedbackText.getText().toString());
                                            Map<String, Object> mHashmap = new HashMap<>();
                                            mHashmap.put(uid,feedback);

                                            //sending the playlist object to database
                                            database.getReference("djFeedback").child(djUID).setValue(mHashmap);
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                                myRef.addListenerForSingleValueEvent(eventListener1);




                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                };
                songsRef.addListenerForSingleValueEvent(eventListener);

            }
        });






    }






}