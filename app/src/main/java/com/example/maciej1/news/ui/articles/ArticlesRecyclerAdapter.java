package com.example.maciej1.news.ui.articles;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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


public class ArticlesRecyclerAdapter extends RecyclerView.Adapter<ArticlesRecyclerAdapter.ArticlesViewHolder> {


    private List<ArticleEntry> articlesList;
    private final View.OnClickListener onClickListener;

    public ArticlesRecyclerAdapter(List<ArticleEntry> articlesList, View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.articlesList = articlesList;
    }

    @Override
    public ArticlesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_item, parent, false);

        return new ArticlesRecyclerAdapter.ArticlesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ArticlesViewHolder holder, int position) {
        final ArticleEntry articleEntry = articlesList.get(position);

        Picasso.with(holder.itemView.getContext())
                .load(articleEntry.getUrlToImage()).into(holder.article_image);
        holder.title.setText(articleEntry.getTitle());
        holder.itemView.setTag(position);
        holder.cardView.setOnClickListener(holder);
    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ArticlesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.article_title)
        TextView title;
        @BindView(R.id.article_image)
        ImageView article_image;
        @BindView(R.id.article_item_card)
        CardView cardView;


        public ArticlesViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View view) {
            onClickListener.onClick(itemView);
        }
    }

    public List<ArticleEntry> getArticlesList() {
        return articlesList;
    }

    public ArticleEntry getArticleEntry(int position) {
        return articlesList.get(position);
    }

}
