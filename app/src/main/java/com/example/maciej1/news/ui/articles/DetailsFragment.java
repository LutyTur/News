package com.example.maciej1.news.ui.articles;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.maciej1.news.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailsFragment extends Fragment {

    @BindView(R.id.details_web_view)
    WebView detailsWebView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        detailsWebView.loadUrl(getArguments().getString("url"));
    }


}
