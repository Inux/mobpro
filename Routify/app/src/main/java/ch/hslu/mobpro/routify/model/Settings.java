package ch.hslu.mobpro.routify.model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Settings {
    private LocalDateTime showFrom;
    private LocalDateTime showTo;
    private List<DayOfWeek> disabledDays = new ArrayList<>();

    public Settings() {
        Calendar c = Calendar.getInstance();
        this.showFrom = LocalDateTime.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 0, 0);
        this.showTo = LocalDateTime.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 23, 59);
    }

    public Settings(LocalDateTime showFrom, LocalDateTime showTo, List<DayOfWeek> disabledDays) {
        this.showFrom = showFrom;
        this.showTo = showTo;
        this.disabledDays = disabledDays;
    }

    public Settings(LocalDateTime showFrom, LocalDateTime showTo, boolean monday, boolean tuesday,
                    boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday) {
        this.showFrom = showFrom;
        this.showTo = showTo;

        this.disabledDays = new ArrayList<>();
        if (monday == false) {
            this.disabledDays.add(DayOfWeek.MONDAY);
        }
        if (tuesday == false) {
            this.disabledDays.add(DayOfWeek.THURSDAY);
        }
        if (wednesday == false) {
            this.disabledDays.add(DayOfWeek.WEDNESDAY);
        }
        if (thursday == false) {
            this.disabledDays.add(DayOfWeek.THURSDAY);
        }
        if (friday == false) {
            this.disabledDays.add(DayOfWeek.FRIDAY);
        }
        if (saturday == false) {
            this.disabledDays.add(DayOfWeek.SATURDAY);
        }
        if (sunday == false) {
            this.disabledDays.add(DayOfWeek.SUNDAY);
        }
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

    List<DayOfWeek> getDisabledDays() { return this.disabledDays; }

    public boolean isDayEnabled(DayOfWeek day) {
        return !disabledDays.contains(day);
    }

    public void setDayEnabled(DayOfWeek day) {
        if(disabledDays.contains(day)) {
            disabledDays.remove(day);
        }
    }

    public void setDayDisabled(DayOfWeek day) {
        if(!disabledDays.contains(day)) {
            disabledDays.add(day);
        }
    }

    @Override
    public String toString() {
        return "ShowFrom: '" + showFrom + "', ShowTo: '" + showTo + "', DisabledDays: '" + disabledDays.toString() + "'";
    }
}
