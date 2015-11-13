package com.example.dmitry.marvelheroes.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dmitry.marvelheroes.R;
import com.example.dmitry.marvelheroes.rest.Constants;
import com.example.dmitry.marvelheroes.rest.MarvelApiClient;
import com.example.dmitry.marvelheroes.rest.responseModels.CharacterDetailsResponse;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Dmitry on 13.10.2015.
 */
public class CharacterDetailsFragment extends Fragment {

    public Context CONTEXT;
    private SharedPreferences superPrefs;

    @InjectView(R.id.img_character_details)
    SimpleDraweeView heroImage;
    @InjectView(R.id.heroName)
    TextView heroName;
    @InjectView(R.id.heroDesc)
    TextView heroDesc;


    public CharacterDetailsFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        CONTEXT = context;
    }

    /*public static CharacterDetailsFragment getInstance(Bundle bundle) {
        CharacterDetailsFragment mCharacterDetailsFragment = new CharacterDetailsFragment();
        mCharacterDetailsFragment.setArguments(bundle);
        return mCharacterDetailsFragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_character_details, container, false);
        superPrefs = PreferenceManager.getDefaultSharedPreferences(this.CONTEXT);

        ButterKnife.inject(this, rootView);
        initView();
        //MarvelApiClient.getInstance(CONTEXT).requestHeroDetails(this.getArguments().getString(Constants.ID_KEY),
        MarvelApiClient.getInstance(CONTEXT).requestHeroDetails(superPrefs.getString("id", "no id"),
                new Callback<CharacterDetailsResponse>() {
            @Override
            public void success(CharacterDetailsResponse characterDetailsResponse, Response response) {
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
        return rootView;
    }

    private void initView() {
        //heroImage.setImageURI(Uri.parse(this.getArguments().getString(Constants.HERO_URL_IMAGE)));
        //heroName.setText(this.getArguments().getString(Constants.HERO_NAME));
        //heroDesc.setText(this.getArguments().getString(Constants.HERO_DESC));

        heroImage.setImageURI(Uri.parse(superPrefs.getString("heroImage", "no image")));
        heroName.setText(superPrefs.getString("heroName", "no name"));
        heroDesc.setText(superPrefs.getString("heroDesc", "no description"));
    }
}
