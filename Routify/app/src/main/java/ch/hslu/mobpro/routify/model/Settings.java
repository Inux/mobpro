package ch.hslu.mobpro.routify.model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

public class Settings {
    private LocalDateTime showFrom;
    private LocalDateTime showTo;
    private List<DayOfWeek> disabledDays;

    public Settings() {
        Calendar c = Calendar.getInstance();
        this.showFrom = LocalDateTime.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 0, 0);
        this.showTo = LocalDateTime.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 23, 59);
    }

    public Settings(LocalDateTime showFrom, LocalDateTime showTo, List<DayOfWeek> disabledDays) {
        this.showFrom = showFrom;
        this.showTo = showTo;
        this.disabledDays = disabledDays
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

    @Override
    public String toString() {
        return "ShowFrom: '" + showFrom + "', ShowTo: '" + showTo + "', DisabledDays: '" + disabledDays.toString() + "'";
    }
}
