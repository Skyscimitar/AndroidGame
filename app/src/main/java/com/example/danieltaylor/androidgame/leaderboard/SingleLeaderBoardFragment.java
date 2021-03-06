package com.example.danieltaylor.androidgame.leaderboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.danieltaylor.androidgame.R;
import com.example.danieltaylor.androidgame.firebase.DatabaseManager;
import com.example.danieltaylor.androidgame.firebase.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SingleLeaderBoardFragment extends Fragment{

    FirebaseDatabase mDatabase;
    ArrayList<User> userList = new ArrayList<>();
    SingleLeaderBoardArrayAdapter adapter;
    ListView userListView;
    Query query;
    ValueEventListener listener;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.single_leaderboard_fragment, null);
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                updateUsers(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("SFL", "error recovering data");
            }
        };
        userListView = rootView.findViewById(R.id.single_leaderboard_list);
        progressBar = rootView.findViewById(R.id.single_progress);
        return rootView;
    }

    /**
     * We need to override the onPause and onStart methods to make sure the app doesn't try and
     * update the fragment views when they aren't active.
     */
    @Override
    public void onStart(){
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);
        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = mDatabase.getReference("User");
        query = ref.orderByChild("singlePlayerScore");
        query.addValueEventListener(listener);
    }

    @Override
    public void onPause(){
        query.removeEventListener(listener);
        super.onPause();
    }


    private void updateUsers(DataSnapshot dataSnapshot) {
        userList.clear();
        for (DataSnapshot ds:dataSnapshot.getChildren()) {
            userList.add(ds.getValue(User.class));
        }
        adapter = new SingleLeaderBoardArrayAdapter(getContext(), userList);
        //because firebase doesn't provide decreasing order, we have to reverse the list
        userList = (ArrayList) reverse(userList);
        userListView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);

    }

    public static <T> List<T> reverse(List<T> arrayList) {
        return reverse(arrayList,0,arrayList.size()-1);
    }
    public static <T> List<T> reverse(List<T> arrayList,int startIndex,int lastIndex) {

        if(startIndex<lastIndex) {
            T t=arrayList.get(lastIndex);
            arrayList.set(lastIndex,arrayList.get(startIndex));
            arrayList.set(startIndex,t);
            startIndex++;
            lastIndex--;
            reverse(arrayList,startIndex,lastIndex);
        }
        return arrayList;
    }

}
