package ch.hslu.mobpro.opendata;

import ch.hslu.mobpro.opendata.transport.TransportClient;
import ch.hslu.mobpro.opendata.transport.model.StationboardResult;
import ch.hslu.mobpro.opendata.transport.parameter.StationboardParameter;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by haubschueh & inux (2019)
 */
public class StationboardTest {
    private TransportClient client;
    private String station;
    private String id;

    @Before
    public void initObjects() {
        client = new TransportClient();

        station = "Aarau";
        id = "8502113";
    }

    @Test
    public void testSimpleStationboard() {
        StationboardResult result1 = client.getStationboardByStation(station);
        StationboardResult result2 = client.getStationboardById(id);

        assertEquals("Stationboards not the same!",
                result1.getStationboard().size(),
                result2.getStationboard().size());
    }

    @Test
    public void testEnhancedStationboard() {
        StationboardParameter param = new StationboardParameter();
        param.setStation(station);
        param.setLimit(1);

        StationboardResult result = client.getStationboard(param);
        
        assertTrue("Stationboards is not empty!",
                1 <= result.getStationboard().size());
    }
}
