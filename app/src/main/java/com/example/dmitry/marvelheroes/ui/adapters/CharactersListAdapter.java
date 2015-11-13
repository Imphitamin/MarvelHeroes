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

import static java.util.Collections.EMPTY_LIST;

/**
 * Created by Dmitry on 14.10.2015.
 */

public class CharactersListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int VIEW_CHARACTER = 0;
    private final static int VIEW_PROGRESS = 1;
    private final static String emptyContent = "None";

    List<Character> characters = new ArrayList<>(); // TODO: 13.11.2015 вместо empty_list - new List
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
            return new CharacterViewHolder(inflater.inflate(R.layout.item_character, viewGroup, false)); // TODO: 13.11.2015 вынести в return
        } else {
            return new ProgressViewHolder(inflater.inflate(R.layout.item_character_progressbar, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        //viewHolder.getItemViewType() // TODO: 13.11.2015 получить characters fragment
        // if (viewHolder instanceof CharacterViewHolder) {
        if (viewHolder.getItemViewType() == VIEW_CHARACTER) {
            final CharacterViewHolder charHolder = (CharacterViewHolder) viewHolder;
            Character thisCharacter = characters.get(position);
            charHolder.setImage(thisCharacter.getUrlImage());
            charHolder.setName(thisCharacter.getName());

            /*final Bundle bundle = new Bundle();
            bundle.putString(Constants.HERO_URL_IMAGE, String.valueOf(thisCharacter.getUrlImage()));
            bundle.putString(Constants.ID_KEY, String.valueOf(thisCharacter.getId()));
            bundle.putString(Constants.HERO_NAME, thisCharacter.getName());
            bundle.putInt(CharacterDetailsActivity.CHARACTER_DETAILS_FRAGMENT_TAG, DETAIL_FRAGMENT_ID);
            if (thisCharacter.getDescription().length() > 0) {
                bundle.putString(Constants.HERO_DESC, thisCharacter.getDescription());
            } else {
                bundle.putString(Constants.HERO_DESC, emptyContent);
            }*/
            // TODO: 13.11.2015 разобраться с этой херней
            final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.context);
            final SharedPreferences.Editor editor = prefs.edit();
            editor.putString("heroImage", String.valueOf(thisCharacter.getUrlImage()));
            editor.putString("id", String.valueOf(thisCharacter.getId()));
            editor.putString("heroName", thisCharacter.getName());
            //editor.putInt(CharacterDetailsActivity.CHARACTER_DETAILS_FRAGMENT_TAG, DETAIL_FRAGMENT_ID);
            if (thisCharacter.getDescription().length() > 0) {
                editor.putString("heroDesc", thisCharacter.getDescription());
            } else {
                editor.putString("heroDesc", emptyContent);
            }

            charHolder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //NavigationHelper.startCharacterDetails(((AppCompatActivity) context), bundle);
                    editor.commit();
                    Intent characterDetails = new Intent(context, CharacterDetailsActivity.class);
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

    // Заменю текущие данные адапетра на новые данные коллекций
    /*public void updateList(List<Character> characters) { // TODO: 13.11.2015 можно использовать addItemCollection вместо этого
        this.characters = characters;
        notifyItemInserted(this.characters.size() - 1);
    }*/

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
        //final int startPosition = this.characters.size();
        this.characters.add(null);
        notifyItemInserted(characters.size());
        //notifyDataSetChanged();
    }

    public boolean isProgressViewVisible() {
        return characters.contains(null);
    }

    static class CharacterViewHolder extends RecyclerView.ViewHolder { // TODO: 13.11.2015 можно захерачить static
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
