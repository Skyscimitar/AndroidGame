package com.example.danieltaylor.androidgame.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.danieltaylor.androidgame.GameElements.Character;

import com.example.danieltaylor.androidgame.R;

public class CharacterSelctionFragment extends Fragment {

    private Character character;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentView v = new FragmentView(getContext(), character);
        v.draw();
        return v;
    }

    /**
     * Default constructor necessary for fragments
     */
    public CharacterSelctionFragment(){}

    /**
     *
     * @param character the fragments character
     * @return returns a fragment with the character as an argument
     */
    public static CharacterSelctionFragment newInstance(Character character) {
        CharacterSelctionFragment csf = new CharacterSelctionFragment();
        csf.character = character;
        //TODO pass the character to the fragment in the bundle (probably not necessary as the frament won't be unloaded and reloaded)
        Bundle args = new Bundle();
        csf.setArguments(args);
        return csf;
    }
}
