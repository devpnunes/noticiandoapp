package com.example.noticiandoapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NoticiandoAdapter extends ArrayAdapter<Noticiando> {
    private static final String LOG_TAG = NoticiandoAdapter.class.getSimpleName();

    public NoticiandoAdapter(Activity context, ArrayList<Noticiando> noticiando) {
        super(context, 0, noticiando);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Noticiando currentNoticiando = getItem(position);

        Date dateObject = new Date(currentNoticiando.getTimeInMilliseconds());

        TextView dateView = listItemView.findViewById(R.id.publicationDate);
        String formattedDate = formatDate(dateObject);
        dateView.setText(formattedDate);

        TextView headlineView = listItemView.findViewById(R.id.newsHeadline);
        headlineView.setText(currentNoticiando.getNewsHeadline());

        TextView sectionView = listItemView.findViewById(R.id.sectionName);
        sectionView.setText(currentNoticiando.getSectionName());

        return listItemView;
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

}
