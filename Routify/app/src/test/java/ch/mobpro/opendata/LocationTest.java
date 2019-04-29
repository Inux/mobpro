package ch.mobpro.opendata;

import ch.hslu.mobpro.opendata.transport.TransportClient;
import ch.hslu.mobpro.opendata.transport.model.LocationResult;
import ch.hslu.mobpro.opendata.transport.type.LocationType;
import ch.hslu.mobpro.opendata.transport.type.TransportationType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by haubschueh & inux (2019)
 */
public class LocationTest {
    private TransportClient client;

    @Before
    public void initObjects() {
        client = new TransportClient();
    }

    @Test
    public void testLocationByQuery() {
        String city = "Basel SBB";

        LocationResult result = client.getLocations(city, LocationType.All);
        assertEquals("Locations not matching!", city, result.getStations().get(0).getName());
    }

    @Test
    public void testLocationByCoordinates() {
        String city = "Basel SBB (Haltestelle)";

        double x = 47.5476;
        double y = 7.5897;

        LocationResult result = client.getLocations(x, y, TransportationType.ICE_TGV_RJ);
        assertEquals("Locations not matching!", city, result.getStations().get(0).getName());
    }
}