package com.example.dmitry.marvelheroes.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dmitry.marvelheroes.R;
import com.example.dmitry.marvelheroes.item.Character;
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

    public static final String CHARACTER_DATA = "characterData";
    public Context CONTEXT;
    private Character characterData;

    @InjectView(R.id.img_character_details)
    SimpleDraweeView heroImage;
    @InjectView(R.id.heroName)
    TextView heroName;
    @InjectView(R.id.heroDesc)
    TextView heroDesc;


    public CharacterDetailsFragment() {}

    public static CharacterDetailsFragment newInstance(Parcelable parcelable){
        CharacterDetailsFragment mCharacterDetailFragment = new CharacterDetailsFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(CHARACTER_DATA, parcelable);
        mCharacterDetailFragment.setArguments(bundle);

        return mCharacterDetailFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        CONTEXT = context;
        characterData = getArguments().getParcelable(CHARACTER_DATA);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_character_details, container, false);

        ButterKnife.inject(this, rootView);
        initView();
        MarvelApiClient.getInstance(CONTEXT).requestHeroDetails(String.valueOf(characterData.getId()),
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
        heroImage.setImageURI(Uri.parse(String.valueOf(characterData.getUrlImage())));
        heroName.setText(characterData.getName());
        if (TextUtils.isEmpty(characterData.getDescription())) {
            heroDesc.setText(R.string.NO_description);
        } else {
            heroDesc.setText(characterData.getDescription());
        }
    }
}
