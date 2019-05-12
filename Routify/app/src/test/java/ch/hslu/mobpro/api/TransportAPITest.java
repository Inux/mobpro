package ch.hslu.mobpro.api;

import org.junit.Test;

import java.util.List;
import java.time.LocalDateTime;

import ch.hslu.mobpro.routify.api.TransportAPI;
import ch.hslu.mobpro.routify.model.ActualConnection;
import ch.hslu.mobpro.routify.model.Connection;
import ch.hslu.mobpro.routify.model.Filters;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by haubschueh & inux (2019)
 */
public class TransportAPITest {

    @Test
    public void testGetConnections() {
        List<Connection> connections = TransportAPI.getConnections("Luzern", "Zürich");
        assertTrue(connections.size() > 0);
        for(Connection conn : connections) {
            System.out.println(conn.toString());
        }
    }


    @Test
    public void testGetActualConnection() {
        Filters filters = new Filters();
        ActualConnection actualConnection = TransportAPI.getActualConnection("Luzern", "Zürich HB", filters);
        assertTrue(actualConnection != null);
        assertTrue(actualConnection.getFrom().equals("Luzern"));
        assertTrue(actualConnection.getTo().equals("Zürich HB"));

        LocalDateTime now = LocalDateTime.now();

        System.out.println("Now: " + LocalDateTime.now());
        System.out.println("Departure: " + actualConnection.getDateTime());
        System.out.println("Compare: " + now.compareTo(actualConnection.getDateTime()));
        assertTrue(now.compareTo(actualConnection.getDateTime()) < 0);
    }
}
