package com.example.dmitry.marvelheroes.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dmitry.marvelheroes.R;
import com.example.dmitry.marvelheroes.item.*;
import com.example.dmitry.marvelheroes.item.Character;
import com.example.dmitry.marvelheroes.ui.adapters.ComicsListAdapter;
import com.example.dmitry.marvelheroes.ui.interfaces.OnComicsRecycleViewScrollListener;

/**
 * Created by Dmitry on 09.11.2015.
 */

public class CharacterDetailsComicsFragment extends Fragment {

    private RecyclerView recyclerViewComics;
    private ComicsListAdapter comicsListAdapter;
    private Context CONTEXT;
    private Character characterData;

    public CharacterDetailsComicsFragment() {}

    public static CharacterDetailsComicsFragment newInstance(Parcelable parcelable){
        CharacterDetailsComicsFragment mCharacterDetailsComicsFragment = new CharacterDetailsComicsFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable("characterData", parcelable);
        mCharacterDetailsComicsFragment.setArguments(bundle);

        return mCharacterDetailsComicsFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        CONTEXT = context;
        characterData = getArguments().getParcelable("characterData");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recyclerViewComics = (RecyclerView) inflater.inflate(R.layout.fragment_character_comics, container, false);
        return recyclerViewComics;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComicsList();
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    private void initComicsList() {
        GridLayoutManager glm = new GridLayoutManager(CONTEXT, 2);
        comicsListAdapter = new ComicsListAdapter(CONTEXT);
        recyclerViewComics.setLayoutManager(glm);
        recyclerViewComics.setAdapter(comicsListAdapter);
        recyclerViewComics.setOnScrollListener(new OnComicsRecycleViewScrollListener(glm) {
            @Override
            public void onLoadMore() {
                comicsListAdapter.requestForComicsByCharacterId(String.valueOf(characterData.getId()));
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        comicsListAdapter.requestForComicsByCharacterId(String.valueOf(characterData.getId()));
    }
}
