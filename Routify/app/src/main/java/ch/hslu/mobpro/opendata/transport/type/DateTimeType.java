package ch.hslu.mobpro.opendata.transport.type;

/**
 * Created by haubschueh & inux (2019)
 */
public enum DateTimeType {
    Departure("departure"),
    Arrival("arrival");

    private final String text;

    /**
     * @param text
     */
    private DateTimeType(final String text) {
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
