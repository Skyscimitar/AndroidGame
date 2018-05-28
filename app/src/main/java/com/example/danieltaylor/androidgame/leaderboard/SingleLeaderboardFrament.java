package com.example.danieltaylor.androidgame.leaderboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.danieltaylor.androidgame.R;
import com.example.danieltaylor.androidgame.firebase.DatabaseManager;
import com.example.danieltaylor.androidgame.firebase.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SingleLeaderboardFrament extends Fragment{

    FirebaseDatabase mDatabase;
    DatabaseManager dm;
    ArrayList<User> userList = new ArrayList<>();
    SingleLeaderBoardArrayAdapter adapter;
    ListView userListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = mDatabase.getReference("User");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                updateUsers(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("SFL", "error recovering data");
            }
        });

        View rootView =  inflater.inflate(R.layout.single_leaderboard_fragment, null);
        userListView = rootView.findViewById(R.id.single_leaderboard_list);
        return rootView;
    }


    private void updateUsers(DataSnapshot dataSnapshot) {
        userList.clear();
        for (DataSnapshot ds:dataSnapshot.getChildren()) {
            userList.add(ds.getValue(User.class));
        }
        adapter = new SingleLeaderBoardArrayAdapter(getContext(), userList);
        userListView.setAdapter(adapter);


    }
}
