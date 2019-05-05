package ch.hslu.mobpro.routify;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * The main activity of Routify.
 */


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

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
}
