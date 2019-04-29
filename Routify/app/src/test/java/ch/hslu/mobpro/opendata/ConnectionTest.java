package ch.hslu.mobpro.opendata;

import ch.hslu.mobpro.opendata.transport.TransportClient;
import ch.hslu.mobpro.opendata.transport.model.ConnectionResult;
import ch.hslu.mobpro.opendata.transport.model.LocationResult;
import ch.hslu.mobpro.opendata.transport.model.Station;
import ch.hslu.mobpro.opendata.transport.model.Stationboard;
import ch.hslu.mobpro.opendata.transport.model.StationboardResult;
import ch.hslu.mobpro.opendata.transport.parameter.ConnectionParameter;
import ch.hslu.mobpro.opendata.transport.type.LocationType;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by haubschueh & inux (2019)
 */
public class ConnectionTest {
    private TransportClient client;
    private String city1;
    private String city2;
    private Date connectionDate;

    @Before
    public void initObjects() {
        client = new TransportClient();

        city1 = "Basel SBB";
        city2 = "Zurich";

        connectionDate = new Date(2019 - 1900, 1, 1, 15, 0, 0);
    }

    @Test
    public void testSimpleConnection() {
        ConnectionResult result = client.getConnections(city1, city2);
        assertEquals("Locations not matching!", city1,
                result.getConnections().get(0).getFrom().getStation().getName());
    }

    @Test
    public void testDateConnection() {
        ConnectionParameter params = new ConnectionParameter(city1, city2);
        params.setDateTime(connectionDate);

        ConnectionResult result = client.getConnections(params);
        assertEquals("Timestamp not matching!",
                Long.valueOf(1549030020),
                result.getConnections().get(0).getFrom().getDepartureTimestamp());
    }

    @Test
    public void testConnectionSearch() {
        StationboardResult result = client.getStationboardByStation("Zurich");
        for(Stationboard board : result.getStationboard()) {
            System.out.println(board.getName());
        }
        LocationResult lresults = client.getLocations("Zurich", LocationType.Station);
        for(Station station : lresults.getStations()) {
            System.out.println(station.getName());
        }
    }
}
