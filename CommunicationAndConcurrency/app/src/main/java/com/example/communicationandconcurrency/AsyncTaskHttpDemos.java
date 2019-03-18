package com.example.communicationandconcurrency;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncTaskHttpDemos extends AsyncTask<String,Void,InputStream> {

    public interface AsyncResponse {
        void response(InputStream in, String url);
    }

    public AsyncResponse delegate = null;
    private String urlString;

    public AsyncTaskHttpDemos(AsyncResponse delegate){
        this.delegate = delegate;
    }

    @Override
    protected InputStream doInBackground(String... params) {
        return openHttpConnection(params[0]);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }

    @Override
    protected void onPostExecute(InputStream inputStream) {
        delegate.response(inputStream,urlString);
    }

    private InputStream openHttpConnection(String urlString) {
        try {
            this.urlString = urlString;
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setInstanceFollowRedirects(true);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return httpURLConnection.getInputStream();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
