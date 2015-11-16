package com.example.dmitry.marvelheroes.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dmitry.marvelheroes.R;
import com.example.dmitry.marvelheroes.rest.Constants;
import com.example.dmitry.marvelheroes.rest.MarvelApiClient;
import com.example.dmitry.marvelheroes.rest.responseModels.CharacterListResponse;
import com.example.dmitry.marvelheroes.ui.adapters.CharactersListAdapter;
import com.example.dmitry.marvelheroes.ui.interfaces.EndlessRecyclerOnScrollListener;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Dmitry on 14.10.2015.
 */

public class CharactersFragment extends Fragment {

    public Context CONTEXT;

    @InjectView(R.id.list_heroes)
    RecyclerView mCharactersList;

    CharactersListAdapter adapter;

    public CharactersFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        CONTEXT = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMarvelApiInfo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_heroes, container, false);
        ButterKnife.inject(this, rootView);

        initListCharacters();
        return rootView;
    }

    private void getMarvelApiInfo() {
        MarvelApiClient.getInstance(CONTEXT)
                .requestHeroesList(Constants.CHARACTERS_LIMIT, Constants.CHARACTERS_INIT_COUNT, new Callback<CharacterListResponse>() {
                    @Override
                    public void success(CharacterListResponse characterListResponse, Response response) {
                        adapter.addItemCollection(characterListResponse.getCharacters());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        error.printStackTrace();
                    }
                });
    }


    private void initListCharacters() {
        //LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        GridLayoutManager glm = new GridLayoutManager(CONTEXT, 2);
        adapter = new CharactersListAdapter(CONTEXT);

        mCharactersList.setLayoutManager(glm);
        mCharactersList.setAdapter(adapter);
        mCharactersList.setOnScrollListener(new EndlessRecyclerOnScrollListener(glm) {
            @Override
            public void onLoadMore(int currentPage) {
                adapter.requestForMoreCharacters();
            }
        });
    }
}
