package com.example.dmitry.marvelheroes.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dmitry.marvelheroes.R;
import com.example.dmitry.marvelheroes.item.NavDrawItem;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Dmitry on 13.10.2015.
 */

public class NavigationDrawerAdapter  extends ArrayAdapter<NavDrawItem> {

    private List<NavDrawItem> navDrawItems;
    private LayoutInflater layoutInflater;

    @InjectView(R.id.imageViewIconContent)
    ImageView imageViewIcon;

    @InjectView(R.id.textViewTitleContent)
    TextView textViewTitle;

    public NavigationDrawerAdapter(Context context, List<NavDrawItem> navDrawItems) {
        super(context, R.layout.item_navigation_drawer, navDrawItems);
        this.layoutInflater = LayoutInflater.from(context);
        this.navDrawItems = navDrawItems;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.item_navigation_drawer, parent, false);
        ButterKnife.inject(this, view);

        imageViewIcon.setImageResource(navDrawItems.get(position).getIdIcon());
        textViewTitle.setText(navDrawItems.get(position).getIdText());
        return view;
    }
}
