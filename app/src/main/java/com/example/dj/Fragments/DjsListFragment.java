package com.example.dj.Fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.dj.Activities.Clubber2Activity;
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
 * Use the {@link DjsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class DjsListFragment extends Fragment {

    ListView djList;
    SearchView searchView;
    ArrayAdapter<String> adapter;



   // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Clubber_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DjsListFragment newInstance(String param1, String param2) {
        DjsListFragment fragment = new DjsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DjsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
// כל הפעולות נעשה ב ONCREATEVIEW
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_djs_list, container, false);
        // פעולות נעשה פה
        initDjsNameListView(view);//display all the dj users in the database

        return  view;
    }


    //handling Dj names list
   public void initDjsNameListView(View view){
       //display djs name in the list view
       djList = (ListView) view.findViewById(R.id.idListView);//saving listitem from fragment view into djList param
       DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
       DatabaseReference usersRef = rootRef.child("users");//users branch reference from firebase database
       List<String> list = new ArrayList<>();//primitive array list

       ValueEventListener eventListener = new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {

               for(DataSnapshot ds : dataSnapshot.getChildren()) {//for each LOOP running on all users

                   String fullName = ds.child("fullName").getValue(String.class);
                   String userType = ds.child("userType").getValue(String.class);
                   if(userType.equals("DJ")){ //check if the current user is a DJ
                       list.add(fullName);//Adding only DJs full name to the list
                   }
               }
               adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_gallery_item,list);//adapting the data int the array list to listView data
               djList.setAdapter(adapter);//setting the adapter to the list view

           }

           @Override
           public void onCancelled(DatabaseError databaseError) {}
       };
       usersRef.addListenerForSingleValueEvent(eventListener);



       //adding onItemClickListener- when clicking on a DJ
       djList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               String djFullName=list.get(position);
               Intent intent = new Intent(getActivity(), Clubber2Activity.class);
               intent.putExtra("KEY2",djFullName);
               startActivity(intent);


           }
       });


    }


}