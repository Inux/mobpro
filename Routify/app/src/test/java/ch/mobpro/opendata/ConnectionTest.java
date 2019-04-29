package ch.mobpro.opendata;

import ch.hslu.mobpro.opendata.transport.TransportClient;
import ch.hslu.mobpro.opendata.transport.model.ConnectionResult;
import ch.hslu.mobpro.opendata.transport.parameter.ConnectionParameter;
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
}
