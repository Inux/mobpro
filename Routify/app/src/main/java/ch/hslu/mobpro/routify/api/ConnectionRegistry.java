package ch.hslu.mobpro.routify.api;

import android.os.AsyncTask;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ch.hslu.mobpro.routify.model.ActualConnection;
import ch.hslu.mobpro.routify.model.Connection;

public class ConnectionRegistry {
    List<Connection> connections = new ArrayList<>();

    private static final Object lock = new Object();
    private static final ConnectionRegistry instance = new ConnectionRegistry();

    //private constructor to avoid client applications to use constructor
    private ConnectionRegistry(){}

    public static ConnectionRegistry getInstance(){
        synchronized (lock) {
            return instance;
        }
    }

    public void register(Connection conn) {
        synchronized (lock) {
            this.connections.add(conn);
        }
    }

    public void update() {
        synchronized (lock) {
            for(Connection c : this.connections) {
                c.update();
            }
        }
    }

    public void unregister(Connection conn) {
        synchronized (lock) {
            this.connections.remove(conn);
        }
    }

    public ActualConnection getNextActualConnection() {
        this.update();

        synchronized (lock) {
            List<Connection> validConnections = this.connections.stream()
                    .filter(c -> c.getActualConnection() != null)
                    .collect(Collectors.toList());

            ActualConnection actualConnection = null;
            for(Connection c : validConnections) {
                if(actualConnection == null ||
                        c.getActualConnection().getDateTime().compareTo(actualConnection.getDateTime()) < 0) {
                    actualConnection = c.getActualConnection();
                }
            }

            return actualConnection;
        }
    }
}