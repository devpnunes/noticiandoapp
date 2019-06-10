package com.example.noticiandoapp;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    public static List<Noticiando> fetchNoticiandoData(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Noticiando> noticiandos = extractResponseFromJson(jsonResponse);

        return noticiandos;
    }


    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(streamReader);
            String output = reader.readLine();
            while (output != null) {
                builder.append(output);
                output = reader.readLine();
            }
        }
        return builder.toString();
    }

    private static List<Noticiando> extractResponseFromJson(String noticiandoJSON) {
        if (TextUtils.isEmpty(noticiandoJSON)) {
            return null;
        }

        List<Noticiando> noticiandos = new ArrayList<>();

        try {

            JSONObject baseJsonResponse = new JSONObject(noticiandoJSON);

            JSONArray noticiandoArray = baseJsonResponse.getJSONArray("response");

            for (int i = 0; i < noticiandoArray.length(); i++) {

                JSONObject currentNoticiando = noticiandoArray.getJSONObject(i);

                JSONObject results = currentNoticiando.getJSONObject("results");

                long date = results.getLong("webPublicationDate");
                String title = results.getString("webTitle");
                String section = results.getString("sectionName");
                String url = results.getString("url");

                Noticiando noticiando = new Noticiando(date, title, section, url);

                noticiandos.add(noticiando);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return noticiandos;
    }
}
