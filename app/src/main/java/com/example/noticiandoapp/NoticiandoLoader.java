package com.example.noticiandoapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import java.util.List;

public class NoticiandoLoader extends AsyncTaskLoader<List<Noticiando>> {

    private static final String LOG_TAG = NoticiandoLoader.class.getName();

    private String mUrl;

    public NoticiandoLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Noticiando> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        List<Noticiando> noticiandos = QueryUtils.fetchNoticiandoData(mUrl);
        return noticiandos;
    }

}
