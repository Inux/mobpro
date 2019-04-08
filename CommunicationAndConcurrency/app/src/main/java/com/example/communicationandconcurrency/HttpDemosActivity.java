package com.example.communicationandconcurrency;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpDemosActivity extends AppCompatActivity {
    private final String JPG_URL = "http://wherever.ch/hslu/homer.jpg";
    private final String TXT_URL = "http://wherever.ch/hslu/loremIpsum.txt";
    private final String JSON_URL = "http://www.nactem.ac.uk/software/acromine/dictionary.py?sf=HTTP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_demos);
    }

    public void loadData(View view) {
        AsyncTask asyncTask = new ImageDownloadTask();
        String[] params = {JPG_URL};
        asyncTask.execute(params);

    }

    public void loadDocument(View view) {
        startAsyncTask(TXT_URL);
    }

    public void loadJSON(View view) {
        startAsyncTask(JSON_URL);
    }

    private void showImage(Bitmap image) {
        ImageView imgView = (ImageView) findViewById(R.id.imgView_inputStream);
        imgView.setImageBitmap(image);
    }

    private void showText(InputStream in) {
        String text = readText(in);
        TextView textView = (TextView) findViewById(R.id.text_inputStream);
        textView.setText(text);
    }

    public String readText(InputStream in) {
        StringBuilder text= new StringBuilder();
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        try {
            while((line = reader.readLine()) != null) {
                text.append(line);
                text.append("\n");
            }
            return text.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public void showJSON(InputStream in) {
        try {
            JSONArray jsonArray = getJsonArray(in);
            JSONObject firstObject = jsonArray.getJSONObject(0);
            JSONObject firstDef = firstObject.getJSONArray("lfs").getJSONObject(0);
            TextView textView= (TextView) findViewById(R.id.text_json);
            textView.setText("HTTP: " +firstDef.getString("lf") + " (since " + firstDef.getInt("since") + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JSONArray getJsonArray(InputStream in) throws IOException{
        String json = readText(in);
        try {
            return new JSONArray(json);
        } catch (JSONException ex){
            throw new IOException("JSON parsing failed",ex);
        }
    }

    private void startAsyncTask(String url) {
        AsyncTask asyncTask = new TextDownloadTask();
        String[] params = {url};
        asyncTask.execute(params);
    }

    private InputStream openHttpConnection(String urlString) {
        try {
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

    public class ImageDownloadTask extends AsyncTask<String,Void,Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {
            InputStream inputStream = openHttpConnection(urls[0]);
            return BitmapFactory.decodeStream(inputStream);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            showImage(bitmap);
        }
    }

    public class TextDownloadTask extends AsyncTask<String,Void,InputStream> {
        private String urlString;

        @Override
        protected InputStream doInBackground(String... urls) {
            urlString = urls[0];
            return openHttpConnection(urlString);

        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            if (TXT_URL == urlString){
                showText(inputStream);
            } else if (JSON_URL == urlString){
                showJSON(inputStream);
            }
        }
    }

}
