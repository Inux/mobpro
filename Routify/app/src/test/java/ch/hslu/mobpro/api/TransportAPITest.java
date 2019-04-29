package ch.hslu.mobpro.api;

import ch.hslu.mobpro.routify.api.TransportAPI;
import ch.hslu.mobpro.routify.model.Connection;

import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

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
}
