package ch.hslu.mobpro.routify.model;

import android.util.Log;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import ch.hslu.mobpro.routify.api.TransportAPI;

public class Connection {
    private int id;
    private String from;
    private String to;
    private Filters filters;
    private Settings settings;
    private ActualConnection actualConnection = null;
    private Runnable runnable = null;
    private Thread thread = null;

    public Connection(String from, String to) {
        this(from, to, new Filters(), new Settings());
    }

    public Connection(String from, String to, Filters filters, Settings settings) {
        this.from = from;
        this.to = to;
        this.filters = filters;
        this.settings = settings;
        this.runnable = () -> updateActualConnection();
        this.thread = new Thread(this.runnable);
    }

    /*
     Attention: May returns null if no Actual Connection is found for the particular Connection
     */
    public ActualConnection getActualConnection() {
        return actualConnection;
    }

    /*
     Start updating the Actual Connection in the Background
     */
    public void update() {
        if(this.thread.isAlive()) {
           return;
        }
        this.thread.run();
    }

    /*
     Check if a update is running in the Background

     */
    public boolean isUpdateRunning() {
        return this.thread.isAlive();
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

    /*
    Private Functions!
     */

    /*
     Runnable running in the background thread!
     */
    private void updateActualConnection() {
        if(isTodayValid() && isTimeSlotValid()) {
            TransportAPI.getConnections(this.from, this.to);
        }
        else {
            this.actualConnection = null; // no valid connection possible found!
        }
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
}
