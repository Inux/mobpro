package ch.hslu.mobpro.routify.model;

import java.time.LocalDateTime;
import java.util.Calendar;

public class Settings {
    private LocalDateTime showFrom;
    private LocalDateTime showTo;
    private Boolean monday = true;
    private Boolean tuesday = true;
    private Boolean wednesday = true;
    private Boolean thursday = true;
    private Boolean friday = true;
    private Boolean saturday = true;
    private Boolean sunday = true;

    public Settings() {
        Calendar c = Calendar.getInstance();
        this.showFrom = LocalDateTime.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 0, 0);
        this.showTo = LocalDateTime.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 23, 59);
    }

    public Settings(LocalDateTime showFrom, LocalDateTime showTo, Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday, Boolean saturday, Boolean sunday) {
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

    public LocalDateTime getShowFrom() {
        return showFrom;
    }

    public void setShowFrom(LocalDateTime showFrom) {
        this.showFrom = showFrom;
    }

    public LocalDateTime getShowTo() {
        return showTo;
    }

    public void setShowTo(LocalDateTime showTo) {
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

    @Override
    public String toString() {
        return "ShowFrom: '" + showFrom + "', ShowTo: '" + showTo + "', Monday: '" + monday + "', Tuesday: '" + tuesday + "', Wednesday: '" + wednesday + "', Thursday: '" + thursday + "', Friday: '" + friday + "', Saturday: '" + saturday + "', Sunday: '" + sunday + "'";
    }
}
