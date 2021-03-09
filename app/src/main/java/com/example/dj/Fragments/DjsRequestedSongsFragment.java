package com.example.dj.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.dj.Activities.DjActivity;
import com.example.dj.Adapters.SongAdapter;
import com.example.dj.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DjsRequestedSongsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DjsRequestedSongsFragment extends Fragment {
    private ListView requestedSongList;
    private String userId;
    private SongAdapter adapter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DjsRequestedSongsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DjsRequestedSongsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DjsRequestedSongsFragment newInstance(String param1, String param2) {
        DjsRequestedSongsFragment fragment = new DjsRequestedSongsFragment();
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
        View view= inflater.inflate(R.layout.fragment_djs_requested_songs, container, false);
        userId = this.getArguments().getString("data");

        requestedSongList = (ListView) view.findViewById(R.id.idListView);
        initRequestedSongsList(view);

        return view;
    }

    public void initRequestedSongsList(View view) {

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("djPlaylist");
        DatabaseReference songsRef = rootRef.child(userId);//users branch reference from firebase database
        List<String> list = new ArrayList<>();
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {//for each LOOP running through all songs

                    String songName = ds.getKey();
                    list.add(songName);//Adding songs name to the list
                }

                adapter = new SongAdapter(getActivity(), android.R.layout.simple_gallery_item, list);//adapting the data int the array list to listView data
                requestedSongList.setAdapter(adapter);//setting the adapter to the list view
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        songsRef.addListenerForSingleValueEvent(eventListener);



    }



}