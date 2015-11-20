package com.example.dmitry.marvelheroes.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dmitry.marvelheroes.R;
import com.example.dmitry.marvelheroes.item.About;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Denisov-DV on 20.11.2015.
 */
public class AboutActivityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int VIEW_ABOUT = 0;
    private final static int VIEW_PROGRESS = 1;

    List<About> abouts = new ArrayList<>();
    Context context;
    private LayoutInflater inflater;

    public AboutActivityAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getItemViewType(int position) {
        return abouts.get(position) != null ? VIEW_ABOUT : VIEW_PROGRESS;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == VIEW_ABOUT) {
            return new AboutViewHolder(inflater.inflate(R.layout.item_about, viewGroup, false));
        } else {
            return new ProgressViewHolder(inflater.inflate(R.layout.item_about_progressbar, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder.getItemViewType() == VIEW_ABOUT) {
            final AboutViewHolder charHolder = (AboutViewHolder) viewHolder;
            final About thisAbout = abouts.get(position);
            charHolder.setName(thisAbout.getText());

            charHolder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {}
            });
        }
    }

    @Override
    public int getItemCount() {
        if (abouts == null) {
            return 0;
        }

        return abouts.size();
    }


    // Добавляем предметы к текущему списку
    public void addItemCollection(List<About> abouts) {
        final int startPosition = this.abouts.size();
        this.abouts.addAll(abouts);
        notifyItemRangeInserted(startPosition, abouts.size());
    }

    static class AboutViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.item_aboutMain)
        RelativeLayout item;

        @InjectView(R.id.text_About)
        TextView textName;

        public AboutViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
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

