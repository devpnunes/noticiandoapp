package com.example.noticiandoapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NoticiandoActivity extends AppCompatActivity implements LoaderCallbacks<List<Noticiando>> {

    private static final String LOG_TAG = NoticiandoActivity.class.getName();

    private static final String GUARDIAN_REQUEST_URL =
            "https://content.guardianapis.com/search?api-key=3bbb2e63-cc36-4736-8777-495b2fc321d2";

    private static final int NOTICIANDO_LOADER_ID = 1;

    private TextView mEmptyStateTextView;

    private NoticiandoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView noticiandoListView = findViewById(R.id.list);

        mEmptyStateTextView = findViewById(R.id.empty_view);
        noticiandoListView.setEmptyView(mEmptyStateTextView);

        mAdapter = new NoticiandoAdapter(this, new ArrayList<Noticiando>());

        noticiandoListView.setAdapter(mAdapter);

        noticiandoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Noticiando currentNoticiando = mAdapter.getItem(position);

                Uri noticiandoUri = Uri.parse(currentNoticiando.getUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, noticiandoUri);

                startActivity(websiteIntent);
            }
        });

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NOTICIANDO_LOADER_ID, null, this);
        } else {
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

    }

    @Override
    public Loader<List<Noticiando>> onCreateLoader(int i, Bundle bundle) {
        return new NoticiandoLoader(this, GUARDIAN_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Noticiando>> loader, List<Noticiando> noticiandos) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        mEmptyStateTextView.setText(R.string.no_noticiandos);

        mAdapter.clear();

        if (noticiandos != null && !noticiandos.isEmpty()) {
            mAdapter.addAll(noticiandos);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Noticiando>> loader) {
        mAdapter.clear();
    }

}

