package ch.hslu.mobpro.routify.model;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.time.LocalDateTime;

import ch.hslu.mobpro.routify.api.TransportAPI;

public class Connection extends AsyncTask<String, Void, ActualConnection> {
    private int id;
    private String from;
    private String to;
    private Filters filters;
    private Settings settings;
    private ActualConnection actualConnection = null;
    private TextView duration = null;

    public Connection(String from, String to) {
        this(from, to, new Filters(), new Settings());
    }

    public Connection(String from, String to, Filters filters, Settings settings) {
        this.from = from;
        this.to = to;
        this.filters = filters;
        this.settings = settings;
        this.duration = null;
    }

    /*
     Attention: May returns null if no Actual Connection is found for the particular Connection
     */
    public ActualConnection getActualConnection() {
        return actualConnection;
    }

    /*
     set Duration textView
     */
    public void setDuration(TextView textView) {
        this.duration = textView;
    }

    public Connection(int id, String from, String to, Filters filters, Settings settings) {
        this(from, to, filters, settings);
        this.setId(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Filters getFilters() {
        return filters;
    }

    public void setFilters(Filters filters) {
        this.filters = filters;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    @Override
    public String toString() {
        return "From: '" + from + "', To: '" + to + "', Filters: [ " + filters.toString() + " ], Settings: [ " + settings.toString() + " ]";
    }

    private boolean isTodayValid() {
       return !this.settings.getDisabledDays().contains(LocalDateTime.now().getDayOfWeek());
    }

    private boolean isTimeSlotValid() {
        if(this.settings.getShowTo().toLocalTime().toNanoOfDay() > LocalDateTime.now().toLocalTime().toNanoOfDay() &&
                LocalDateTime.now().toLocalTime().toNanoOfDay() > this.settings.getShowFrom().toLocalTime().toNanoOfDay()) {
            return true;
        }
        return false;
    }

    @Override
    protected ActualConnection doInBackground(String... strings) {
        if(isTodayValid() && isTimeSlotValid()) {
            Log.i("Connection", "searching for ActualConnection");
            this.actualConnection = TransportAPI.getActualConnection(this.from, this.to, this.filters);
        }
        else {
            this.actualConnection = null; // no valid connection possible found!
        }

        if(this.actualConnection != null) {
            Log.i("Connection", "new ActualConnection");
        } else {
            Log.i("Connection", "no ActualConnection!");
        }

        if(this.duration != null && this.actualConnection != null) {
            this.duration.post(() -> {
                Log.i("Connection", "setText of Duration label");
                LocalDateTime ldt = this.actualConnection.getDateTime();
                String time = ldt.getHour()+":"+ldt.getMinute();

                this.duration.setText(time);
            });
        }

        return this.actualConnection;
    }
}
