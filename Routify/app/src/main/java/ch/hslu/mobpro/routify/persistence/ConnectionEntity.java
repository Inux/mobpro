package ch.hslu.mobpro.routify.persistence;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class ConnectionEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    //Connection
    @ColumnInfo(name = "from")
    private String from;
    @ColumnInfo(name = "to")
    private String to;
    //Filters
    @ColumnInfo(name = "max_duration")
    private Integer maxDuration;
    @ColumnInfo(name = "bus_allowed")
    private Boolean busAllowed;
    @ColumnInfo(name = "train_allowed")
    private Boolean trainAllowed;
    //Settings
    @ColumnInfo(name = "time_from")
    private String showFrom; // Todo: LocalDateTime
    @ColumnInfo(name = "time_to")
    private String showTo; // Todo: LocalDateTime
    @ColumnInfo(name = "monday")
    private Boolean monday;
    @ColumnInfo(name = "tuesday")
    private Boolean tuesday;
    @ColumnInfo(name = "wednesday")
    private Boolean wednesday;
    @ColumnInfo(name = "thursday")
    private Boolean thursday;
    @ColumnInfo(name = "friday")
    private Boolean friday;
    @ColumnInfo(name = "saturday")
    private Boolean saturday;
    @ColumnInfo(name = "sunday")
    private Boolean sunday;

    public ConnectionEntity(String from, String to, Integer maxDuration, Boolean busAllowed, Boolean trainAllowed, String showFrom, String showTo, Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday, Boolean saturday, Boolean sunday) {
        this.from = from;
        this.to = to;
        this.maxDuration = maxDuration;
        this.busAllowed = busAllowed;
        this.trainAllowed = trainAllowed;
        this.showFrom = showFrom;
        this.showTo = showTo;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
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

    public Integer getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(Integer maxDuration) {
        this.maxDuration = maxDuration;
    }

    public Boolean getBusAllowed() {
        return busAllowed;
    }

    public void setBusAllowed(Boolean busAllowed) {
        this.busAllowed = busAllowed;
    }

    public Boolean getTrainAllowed() {
        return trainAllowed;
    }

    public void setTrainAllowed(Boolean trainAllowed) {
        this.trainAllowed = trainAllowed;
    }

    public String getShowFrom() {
        return showFrom;
    }

    public void setShowFrom(String showFrom) {
        this.showFrom = showFrom;
    }

    public String getShowTo() {
        return showTo;
    }

    public void setShowTo(String showTo) {
        this.showTo = showTo;
    }

    public Boolean getMonday() {
        return monday;
    }

    public void setMonday(Boolean monday) {
        this.monday = monday;
    }

    public Boolean getTuesday() {
        return tuesday;
    }

    public void setTuesday(Boolean tuesday) {
        this.tuesday = tuesday;
    }

    public Boolean getWednesday() {
        return wednesday;
    }

    public void setWednesday(Boolean wednesday) {
        this.wednesday = wednesday;
    }

    public Boolean getThursday() {
        return thursday;
    }

    public void setThursday(Boolean thursday) {
        this.thursday = thursday;
    }

    public Boolean getFriday() {
        return friday;
    }

    public void setFriday(Boolean friday) {
        this.friday = friday;
    }

    public Boolean getSaturday() {
        return saturday;
    }

    public void setSaturday(Boolean saturday) {
        this.saturday = saturday;
    }

    public Boolean getSunday() {
        return sunday;
    }

    public void setSunday(Boolean sunday) {
        this.sunday = sunday;
    }
}
