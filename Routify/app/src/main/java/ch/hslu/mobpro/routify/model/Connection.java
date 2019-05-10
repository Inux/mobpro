package ch.hslu.mobpro.routify.model;

public class Connection {
    private int id;
    private String from;
    private String to;
    private Filters filters;
    private Settings settings;

    public Connection(String from, String to) {
        this(from, to, new Filters(), new Settings());
    }

    public Connection(String from, String to, Filters filters, Settings settings) {
        this.from = from;
        this.to = to;
        this.filters = filters;
        this.settings = settings;
    }

    public Connection(int id, String from, String to, Filters filters, Settings settings) {
        this(from, to, filters, settings);
        this.setId(id);
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

    public Filters getFilters() {
        return filters;
    }

    public void setFilters(Filters filters) {
        this.filters = filters;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    @Override
    public String toString() {
        return "From: '" + from + "', To: '" + to + "', Filters: [ " + filters.toString() + " ], Settings: [ " + settings.toString() + " ]";
    }
}
