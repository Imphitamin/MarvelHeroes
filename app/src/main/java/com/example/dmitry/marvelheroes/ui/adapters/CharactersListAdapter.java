package com.example.dmitry.marvelheroes.ui.adapters;

import com.example.dmitry.marvelheroes.R;
import com.example.dmitry.marvelheroes.item.Character;
import com.example.dmitry.marvelheroes.rest.Constants;
import com.example.dmitry.marvelheroes.rest.MarvelApiClient;
import com.example.dmitry.marvelheroes.rest.responseModels.CharacterListResponse;
import com.example.dmitry.marvelheroes.ui.CharacterDetailsActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Dmitry on 14.10.2015.
 */

public class CharactersListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int VIEW_CHARACTER = 0;
    private final static int VIEW_PROGRESS = 1;

    List<Character> characters = new ArrayList<>();
    Context context;
    private LayoutInflater inflater;

    public CharactersListAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getItemViewType(int position) {
        return characters.get(position) != null ? VIEW_CHARACTER : VIEW_PROGRESS;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == VIEW_CHARACTER) {
            return new CharacterViewHolder(inflater.inflate(R.layout.item_character, viewGroup, false));
        } else {
            return new ProgressViewHolder(inflater.inflate(R.layout.item_character_progressbar, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder.getItemViewType() == VIEW_CHARACTER) {
            final CharacterViewHolder charHolder = (CharacterViewHolder) viewHolder;
            final Character thisCharacter = characters.get(position);
            charHolder.setImage(thisCharacter.getUrlImage());
            charHolder.setName(thisCharacter.getName());

            charHolder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent characterDetails = new Intent(context, CharacterDetailsActivity.class);
                    characterDetails.putExtra("characterData", thisCharacter);
                    context.startActivity(characterDetails);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (characters == null) {
            return 0;
        }

        return characters.size();
    }

    // Когда грузим больше героев, добавляется класс ProgressViewHolder
    // Класс EndlessRecyclerOnScrollListener нуждается в реальном количестве предметов в списке
    public int getCharactersItemsCount() {
        if (isProgressViewVisible()) {
            return characters.size() - 1;
        }

        return characters.size();
    }

    // Добавляем предметы к текущему списку героев
    public void addItemCollection(List<Character> characters) {
        final int startPosition = this.characters.size();
        this.characters.addAll(characters);
        notifyItemRangeInserted(startPosition, characters.size());
    }

    // Запрос к Marvel Api - для загрузки еще 25-ти героев
    public void requestForMoreCharacters() {
        showOnLoadViewHolder();
        MarvelApiClient.getInstance(context)
                .requestHeroesList(Constants.CHARACTERS_LIMIT, getCharactersItemsCount(), new Callback<CharacterListResponse>() {
                    @Override
                    public void success(CharacterListResponse characterListResponse, Response response) {
                        characters.remove(characters.size() - 1);
                        notifyItemRemoved(characters.size());
                        addItemCollection(characterListResponse.getCharacters());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        error.printStackTrace();
                    }
                });
    }

    private void showOnLoadViewHolder() {
        this.characters.add(null);
        notifyItemInserted(characters.size());
    }

    public boolean isProgressViewVisible() {
        return characters.contains(null);
    }

    static class CharacterViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.item_Main)
        RelativeLayout item;

        @InjectView(R.id.img_character)
        SimpleDraweeView imgCharacter;

        @InjectView(R.id.text_characterName)
        TextView textName;

        public CharacterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        public void setImage(Uri urlImage) {
            if (!urlImage.equals(Uri.EMPTY)) {
                imgCharacter.setImageURI(urlImage);
            }
        }
        public void setName(String name) {
            textName.setText(name);
        }
    }

    static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressViewHolder(View itemView) {
            super(itemView);
        }
    }
}
