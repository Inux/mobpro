package ch.hslu.mobpro.routify.api;

import java.util.ArrayList;
import java.util.List;

import ch.hslu.mobpro.opendata.transport.TransportClient;
import ch.hslu.mobpro.opendata.transport.model.Station;
import ch.hslu.mobpro.opendata.transport.parameter.ConnectionParameter;
import ch.hslu.mobpro.opendata.transport.type.LocationType;
import ch.hslu.mobpro.routify.model.ActualConnection;
import ch.hslu.mobpro.routify.model.Connection;
import ch.hslu.mobpro.routify.model.Filters;

/**
 * TransportAPI is a small wrapper for the opendata API
 * to convert the retrieved data to internal models
 *
 * Created by haubschueh & inux (2019)
 */
public class TransportAPI {
    private static TransportClient client = new TransportClient();

    /**
     * Returns all possible connections between 'from' and 'to'
     * Note that all possible ways are returned
     *
     * @param from (station to start from)
     * @param to (station to go)
     * @return list of connections
     */
    public static List<Connection> getConnections(String from, String to) {
        List<Connection> connections = new ArrayList<>();

        List<Station> fromStations = client.getLocations(from, LocationType.All).getStations();
        List<Station> toStations = client.getLocations(to, LocationType.All).getStations();

        if(fromStations.size() > 0 && toStations.size() > 0) {
            for(Station fromStation : fromStations) {
                for(Station toStation : toStations) {
                    connections.add(new Connection(fromStation.getName(), toStation.getName()));
                }
            }
        }

        return connections;
    }

    public static ActualConnection getActualConnection(String from, String to, Filters filters) {
        boolean busAllowed = filters.getBusAllowed();
        boolean trainAllowed = filters.getTrainAllowed();
        client.getConnections(new ConnectionParameter().s);

        Integer maxDuration = filters.getMaxDuration();


    }
}
