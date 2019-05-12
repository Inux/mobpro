package ch.hslu.mobpro.routify;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import ch.hslu.mobpro.routify.persistence.DatabaseHelper;

/**
 * The main activity of Routify.
 */

public class MainActivity extends AppCompatActivity {
    final private String WIDGET_INITIALIZED = "WIDGET_INITIALIZED";
    final private boolean PRODUCTION = false;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //DatabaseHelper
        this.databaseHelper = new DatabaseHelper(this);

        //WidgetManager
        final SharedPreferences pref = getPreferences(MODE_PRIVATE);
        final boolean widgetInitialized = pref.getBoolean(WIDGET_INITIALIZED, true);
        if(!widgetInitialized || !PRODUCTION) {
            AppWidgetManager appWidgetManager = this.getSystemService(AppWidgetManager.class);
            ComponentName provider = new ComponentName(this, RoutifyWidgetProvider.class);
            if (appWidgetManager.isRequestPinAppWidgetSupported()) {
                appWidgetManager.requestPinAppWidget(provider, null, null);
            }

            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean(WIDGET_INITIALIZED, true);
            editor.apply();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ListView listView = (ListView)findViewById(R.id.connection_listview);
        listView.setAdapter(null);
        databaseHelper.getAllConnections(listView);
    }

    public void onAddConnectionClick(View view) {
        startActivity(new Intent(this, ConnectionActivity.class));
    }
}
