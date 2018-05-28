package com.example.danieltaylor.androidgame.array_adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.danieltaylor.androidgame.firebase.UserMarker;
import com.example.danieltaylor.androidgame.R;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardAdapter extends ArrayAdapter<UserMarker>{

    private Context mContext;
    private List<UserMarker> userList;

    public LeaderboardAdapter(@NonNull Context context, ArrayList<UserMarker> userList) {
        super(context, 0, userList);
        mContext = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.leaderboard_item, parent, false);
        }
        UserMarker user = userList.get(position);
        TextView name = listItem.findViewById(R.id.name);
        name.setText(user.getLogin());
        TextView elo = listItem.findViewById(R.id.elo);
        elo.setText(Integer.toString(user.getElo()));

        return listItem;
    }
}
