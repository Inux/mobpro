package ch.hslu.mobpro.routify;

import android.appwidget.AppWidgetProvider;
import android.appwidget.AppWidgetManager;
import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

import ch.hslu.mobpro.routify.api.ConnectionRegistry;
import ch.hslu.mobpro.routify.model.ActualConnection;

import static android.content.Context.MODE_PRIVATE;

public class RoutifyWidgetProvider extends AppWidgetProvider {
    final private String WIDGET_INITIALIZED = "WIDGET_INITIALIZED";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.routify_widget);

        Log.i("RoutifyWidgetProvider", "updating list item departure");

        ActualConnection actualConnection = ConnectionRegistry.getInstance().getNextActualConnection();

        if(actualConnection != null) {
            views.setTextViewText(R.id.list_item_from, actualConnection.getFrom());
            views.setTextViewText(R.id.list_item_to, actualConnection.getTo());
            views.setTextViewText(R.id.list_item_departure, actualConnection.getDateTime().toLocalTime().toString());
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);

        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(WIDGET_INITIALIZED, true);
        editor.apply();
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);

        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(WIDGET_INITIALIZED, false);
        editor.apply();
    }
}
