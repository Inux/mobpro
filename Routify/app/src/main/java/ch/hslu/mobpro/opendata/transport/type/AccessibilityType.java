package ch.hslu.mobpro.opendata.transport.type;

/**
 * Created by haubschueh & inux (2019)
 */
public enum AccessibilityType {

    INDEPENDENT_BOARDING("independent_boarding"),
    ASSISTED_BOARDING("assisted_boarding"),
    ADVANCED_NOTICE("advanced_notice");

    private final String text;

    /**
     * @param text
     */
    private AccessibilityType(final String text) {
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
