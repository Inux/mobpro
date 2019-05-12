package ch.hslu.mobpro.routify.model;

import java.time.LocalDateTime;

public class ActualConnection {
    final private String from;
    final private String to;
    final LocalDateTime dateTime;

    public ActualConnection(String from, String to, LocalDateTime dateTime) {
        this.from = from;
        this.to = to;
        this.dateTime = dateTime;
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
}
