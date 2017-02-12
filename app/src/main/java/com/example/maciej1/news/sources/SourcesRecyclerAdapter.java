package com.example.maciej1.news.sources;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maciej1.news.R;
import com.example.maciej1.news.data.SourceEntry;

import java.util.List;

import javax.xml.transform.Source;

/**
 * Created by Maciej1 on 2017-02-12.
 */

public class SourcesRecyclerAdapter
        extends RecyclerView.Adapter<SourcesRecyclerAdapter.SourcesViewHolder> {

    private List<SourceEntry> mSourcesList;


    public class SourcesViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView url;

        public SourcesViewHolder(View itemView) {
            super(itemView);

            title = (TextView)itemView.findViewById(R.id.source_item_title);
            url = (TextView)itemView.findViewById(R.id.source_item_url);
        }
    }

    public SourcesRecyclerAdapter(List<SourceEntry> sourcesList){
        this.mSourcesList = sourcesList;
    }

    @Override
    public SourcesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.source_item, parent, false);

        return new SourcesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SourcesViewHolder holder, int position) {
        SourceEntry sourceEntry = mSourcesList.get(position);

        holder.title.setText(sourceEntry.getName());
        holder.url.setText(sourceEntry.getUrl());
    }

    @Override
    public int getItemCount() {
        return mSourcesList.size();
    }


}
