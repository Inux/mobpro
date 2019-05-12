package ch.hslu.mobpro.routify.model;

import android.util.Log;

import java.time.LocalDateTime;

import android.os.AsyncTask;
import ch.hslu.mobpro.routify.api.TransportAPI;

public class ActualConnection extends AsyncTask<Connection, Void, ActualConnection> {
    final private String from;
    final private String to;
    final LocalDateTime dateTime;

    public ActualConnection(String from, String to, LocalDateTime dateTime) {
        this.from = from;
        this.to = to;
        this.dateTime = dateTime;
    }

    public ActualConnection(String from, String to) {
        this.from = from;
        this.to = to;
        this.dateTime = null;
    }

    public String getFrom() {
        return this.from;
    }

    public String getTo() {
        return this.to;
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    @Override
    protected ActualConnection doInBackground(Connection... connections) {
        ActualConnection actualConn = TransportAPI.getActualConnection(this.from, this.to, connections[0].getFilters());


        if(actualConn != null) {
            Log.i("Connection", "new ActualConnection");
        } else {
            Log.i("Connection", "no ActualConnection!");
        }

        if(connections[0].getDuration() != null && actualConn != null) {
            connections[0].getDuration().post(() -> {
                Log.i("Connection", "setText of Duration label");
                LocalDateTime ldt = actualConn.getDateTime();

                connections[0].getDuration().setText(ldt.toLocalTime().toString());
            });
        }

        connections[0].setActualConnection(actualConn);
        return actualConn;
    }
}
