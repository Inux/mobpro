package ch.hslu.mobpro.routify.model;

public class Filters {
    private Integer maxDuration;
    private Boolean busAllowed;
    private Boolean trainAllowed;

    public Filters() {
        maxDuration = Integer.MAX_VALUE;
        busAllowed = true;
        trainAllowed = true;
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

    @Override
    public String toString() {
        return "MaxDuration: '" + maxDuration + "', BusAllowed: '" + busAllowed + "', TrainAllowed: '" + trainAllowed + "'";
    }
}
