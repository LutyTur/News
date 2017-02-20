package com.example.maciej1.news.ui.sources;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maciej1.news.NewsApplication;
import com.example.maciej1.news.R;
import com.example.maciej1.news.data.SourceEntry;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SourcesRecyclerAdapter
        extends RecyclerView.Adapter<SourcesRecyclerAdapter.SourcesViewHolder> {

    private List<SourceEntry> sourcesList = new ArrayList<>();


    public List<SourceEntry> getSourcesList() {
        return sourcesList;
    }

    public void setSourcesList(List<SourceEntry> sourcesList) {
        this.sourcesList = sourcesList;
    }


    public class SourcesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.source_item_title)
        TextView title;
        @BindView(R.id.source_logo)
        ImageView source_logo;


        public SourcesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }

    public SourcesRecyclerAdapter(List<SourceEntry> sourcesList) {
        this.sourcesList = sourcesList;
        setHasStableIds(true);
    }

    @Override
    public SourcesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.source_item, parent, false);

        return new SourcesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SourcesViewHolder holder, int position) {
        final SourceEntry sourceEntry = sourcesList.get(position);

        Picasso.with(holder.itemView.getContext())
                .load(sourceEntry.getLogos().getLarge()).into(holder.source_logo);

        holder.source_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = sourceEntry.getName();
                Log.i("Source clicked: ", item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sourcesList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


}
