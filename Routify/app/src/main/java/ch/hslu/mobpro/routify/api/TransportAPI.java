package ch.hslu.mobpro.routify.api;

import android.icu.text.SimpleDateFormat;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import ch.hslu.mobpro.opendata.transport.TransportClient;
import ch.hslu.mobpro.opendata.transport.model.Station;
import ch.hslu.mobpro.opendata.transport.parameter.ConnectionParameter;
import ch.hslu.mobpro.opendata.transport.model.ConnectionResult;
import ch.hslu.mobpro.opendata.transport.type.LocationType;
import ch.hslu.mobpro.opendata.transport.type.TransportationType;
import ch.hslu.mobpro.routify.model.ActualConnection;
import ch.hslu.mobpro.routify.model.Connection;
import ch.hslu.mobpro.routify.model.Filters;
import ch.hslu.mobpro.routify.util.LocalDateTimeHelper;

import android.util.Log;

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

        ConnectionParameter connParams = new ConnectionParameter(from, to);
        connParams.setLimit(1);

        List<TransportationType> transportationTypes = new ArrayList<>();
        if(busAllowed) {
            transportationTypes.add(TransportationType.BUS);
            transportationTypes.add(TransportationType.CABLEWAY);
            transportationTypes.add(TransportationType.TRAM);
            transportationTypes.add(TransportationType.SHIP);
        }
        if(trainAllowed) {
            transportationTypes.add(TransportationType.TRAIN);
        }

        connParams.setTransportations(transportationTypes);
        ConnectionResult connResult = client.getConnections(connParams);

        if(connResult.getConnections().size() > 0) {
            ch.hslu.mobpro.opendata.transport.model.Connection conn = connResult.getConnections().get(0);

            Log.i("TransportAPI", "actualConnection -> from: '" + conn.getFrom().getStation().getName() +
                    "', to: '" + conn.getTo().getStation().getName() +
                    "', departure: '" + conn.getFrom().getDeparture() +
                    "', timestamp: '" + conn.getFrom().getDepartureTimestamp() + "'");

            System.out.println(conn.getFrom().getDepartureTimestamp());

            return new ActualConnection(
                    conn.getFrom().getStation().getName(),
                    conn.getTo().getStation().getName(),
                    LocalDateTimeHelper.getLocalDateTime(conn.getFrom().getDepartureTimestamp()));
        }

        return null;
    }
}
