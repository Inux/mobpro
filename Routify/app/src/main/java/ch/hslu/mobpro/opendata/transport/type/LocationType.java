package ch.hslu.mobpro.opendata.transport.type;

/**
 * Created by haubschueh & inux (2019)
 */
public enum LocationType {
    /**
     * Looks up for all types of locations (default)
     */
    All("all"),

    /**
     * Looks up for stations (train station, bus station)
     */
    Station("station"),

    /**
     * Looks up for points of interest (Clock tower, China garden)
     */
    POI("poi"),

    /**
     * Looks up for an address (Zurich Bahnhofstrasse 33)
     */
    ADDRESS("address");

    private final String text;

    /**
     * @param text
     */
    private LocationType(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
