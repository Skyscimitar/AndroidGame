package com.example.danieltaylor.androidgame.leaderboard;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.danieltaylor.androidgame.R;
import com.example.danieltaylor.androidgame.firebase.User;

import java.util.ArrayList;

public class MultiLeaderBoardArrayAdapter extends ArrayAdapter<User> {

    private Context mContext;
    private ArrayList<User> userList;
    Resources res;

    public MultiLeaderBoardArrayAdapter(@NonNull Context context, ArrayList<User> list){
        super(context, 0, list);
        this.mContext = context;
        this.userList = list;
        res = context.getResources();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.leaderboard_item, parent, false);
        }

        TextView name = listItem.findViewById(R.id.leaderboard_user_name);
        TextView score = listItem.findViewById(R.id.leaderboard_user_score);
        User u = userList.get(position);
        name.setText(res.getString(R.string.leaderboard_player_name, u.getName()));
        score.setText(res.getString(R.string.leaderboard_player_score, u.getMultiPlayerScore()));

        return listItem;
    }
}