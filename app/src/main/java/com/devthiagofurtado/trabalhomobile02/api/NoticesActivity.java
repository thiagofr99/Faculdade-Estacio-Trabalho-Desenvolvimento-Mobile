package com.devthiagofurtado.trabalhomobile02.api;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;


import com.devthiagofurtado.trabalhomobile02.R;
import com.devthiagofurtado.trabalhomobile02.api.adapter.CustomAdapter;
import com.devthiagofurtado.trabalhomobile02.api.listener.OnFetchDataListener;
import com.devthiagofurtado.trabalhomobile02.api.listener.SelectListener;
import com.devthiagofurtado.trabalhomobile02.api.model.NewsApiResponse;
import com.devthiagofurtado.trabalhomobile02.api.model.NewsHeadlines;
import com.devthiagofurtado.trabalhomobile02.api.request.RequestManager;

import java.util.List;

public class NoticesActivity extends AppCompatActivity implements SelectListener {
    RecyclerView recyclerView;
    CustomAdapter customAdapter;
    ProgressDialog dialog;
    String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Fetching news articles...");
        dialog.show();

        tipo = getIntent().getStringExtra("tipo");

        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener,tipo,null);
    }

    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {
            showNews(list);
            dialog.dismiss();
        }

        @Override
        public void onError(String message) {

        }
    };

    private void showNews(List<NewsHeadlines> list) {
        recyclerView = findViewById(R.id.recyler_main);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        customAdapter = new CustomAdapter(this,list,this);
        recyclerView.setAdapter(customAdapter);
    }

    @Override
    public void OnNesClicked(NewsHeadlines headlines) {
        startActivity(new Intent(NoticesActivity.this,
                DetailsActivity.class).putExtra("data", headlines));

    }
}