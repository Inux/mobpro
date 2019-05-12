package ch.hslu.mobpro.opendata.transport.type;

/**
 * Created by haubschueh & inux (2019)
 */
public enum TransportationType {
    TRAIN("train"),
    TRAM( "tram"),
    SHIP( "ship"),
    BUS("bus"),
    CABLEWAY("cableway");

    private final String text;

    /**
     * @param text
     */
    private TransportationType(final String text) {
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
