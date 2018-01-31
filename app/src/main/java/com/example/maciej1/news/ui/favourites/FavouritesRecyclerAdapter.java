package com.example.maciej1.news.ui.favourites;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maciej1.news.R;
import com.example.maciej1.news.data.ArticleEntry;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavouritesRecyclerAdapter extends RecyclerView.Adapter<FavouritesRecyclerAdapter.FavouritesViewHolder> {

    private List<ArticleEntry> favouritesList;
    private final View.OnClickListener onClickListener;

    public FavouritesRecyclerAdapter(List<ArticleEntry> favouritesList, View.OnClickListener onClickListener) {
        this.favouritesList = favouritesList;
        this.onClickListener = onClickListener;
    }

    @Override
    public FavouritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.favourites_item, parent, false);

        return new FavouritesRecyclerAdapter.FavouritesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FavouritesViewHolder holder, int position) {
        final ArticleEntry entry = favouritesList.get(position);

        Picasso.with(holder.itemView
                .getContext())
                .load(entry.getUrlToImage())
                .into(holder.article_image);
        holder.title.setText(entry.getTitle());
        holder.itemView.setTag(position);
        holder.cardView.setOnClickListener(holder);
    }

    @Override
    public int getItemCount() {
        return favouritesList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class FavouritesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.favourite_title)
        TextView title;
        @BindView(R.id.favourite_image)
        ImageView article_image;
        @BindView(R.id.favourite_item_card)
        CardView cardView;

        public FavouritesViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onClick(itemView);
        }
    }
}
