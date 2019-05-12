package ch.hslu.mobpro.routify;

import android.content.Intent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import ch.hslu.mobpro.routify.persistence.DatabaseHelper;

/**
 * The main activity of Routify.
 */


public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //DatabaseHelper
        this.databaseHelper = new DatabaseHelper(getApplicationContext());
        //WidgetManager
        AppWidgetManager appWidgetManager = this.getSystemService(AppWidgetManager.class);
        ComponentName provider = new ComponentName(this, RoutifyWidgetProvider.class);
        if(appWidgetManager.isRequestPinAppWidgetSupported()) {
            appWidgetManager.requestPinAppWidget(provider, null, null);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView textView = (TextView)findViewById(R.id.databasetest);
        databaseHelper.getAllConnections(textView);
    }

    public void onAddConnectionClick(View view) {
        startActivity(new Intent(this, ConnectionActivity.class));
    }
}