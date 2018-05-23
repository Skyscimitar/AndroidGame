package com.example.danieltaylor.androidgame.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.danieltaylor.androidgame.R;

public class CharacterSelctionFragment extends Fragment {

    private Character character;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.character_selection_window, container, false);
        prepareView(v);
        return v;
    }

    public CharacterSelctionFragment(){}

    public static CharacterSelctionFragment newInstance(Character character) {
        CharacterSelctionFragment csf = new CharacterSelctionFragment();
        csf.character = character;
        //TODO pass the character to the fragment in the bundle (probably not necessary as the frament won't be unloaded and reloaded)
        Bundle args = new Bundle();
        csf.setArguments(args);
        return csf;
    }

    private void prepareView(View v){

    }
}
