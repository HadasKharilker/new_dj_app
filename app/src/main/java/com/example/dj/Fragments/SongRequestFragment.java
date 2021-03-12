package com.example.dj.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.dj.Activities.Clubber2Activity;
import com.example.dj.Activities.Playlist;
import com.example.dj.Activities.User;
import com.example.dj.R;
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
 * Use the {@link SongRequestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SongRequestFragment extends Fragment {

    private ListView songList;
    private SearchView searchView;
    private ArrayAdapter<String> adapter;
    private String selectedSong;
    private String djUID;
    private String djName;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SongRequestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SongRequestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SongRequestFragment newInstance(String param1, String param2) {
        SongRequestFragment fragment = new SongRequestFragment();
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
        View view = inflater.inflate(R.layout.fragment_song_request, container, false);
        songList = (ListView) view.findViewById(R.id.idListView);
        initSongsList(view);
        requestSongButtonListener( view);

        return view;
    }

    public void initSongsList(View view) {

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference songsRef = rootRef.child("songs");//users branch reference from firebase database
        List<String> list = new ArrayList<>();
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {//for each LOOP running through all songs

                    String songName = ds.getValue(String.class);
                    list.add(songName);//Adding songs name to the list
                }

                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_gallery_item, list);//adapting the data int the array list to listView data
                songList.setAdapter(adapter);//setting the adapter to the list view
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        songsRef.addListenerForSingleValueEvent(eventListener);


        //adding onItemClickListener- when clicking on a song
        songList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedSong = list.get(position);


            }
        });

    }




    public void requestSongButtonListener(View view){
        Button b = view.findViewById(R.id.SongRequestButton);
        b.setOnClickListener(new View.OnClickListener() {
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


                                //attach the song to dj playlist

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                //going to the relevant branch in the db

                                final DatabaseReference myRef = database.getReference("djPlaylist");
                                ValueEventListener eventListener1=(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        //check if there is a dj playlist allready exist
                                        if (snapshot.hasChild(djUID))
                                        {
                                            for(DataSnapshot ds : snapshot.getChildren()){
                                                String key = ds.getKey();
                                                Map<String, Object> mHashmap = new HashMap<>();
                                                mHashmap = (Map<String, Object>) snapshot.child(djUID).getValue();
                                                mHashmap.put(selectedSong,true);
                                                database.getReference("djPlaylist").child(djUID).updateChildren(mHashmap);
                                                Toast.makeText(getActivity(), "Your song has been sended!", Toast.LENGTH_SHORT).show();



                                            }


                                        }
                                        //in case this is the first playlist
                                        else{
                                            Map<String, Object> mHashmap = new HashMap<>();
                                            mHashmap.put(selectedSong,true);

                                            //sending the playlist object to database
                                            database.getReference("djPlaylist").child(djUID).setValue(mHashmap);
                                            Toast.makeText(getActivity(), "Your song has been sended!", Toast.LENGTH_SHORT).show();
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

