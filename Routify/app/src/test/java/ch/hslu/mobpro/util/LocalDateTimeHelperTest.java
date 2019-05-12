package ch.hslu.mobpro.util;

import org.junit.Test;

import java.text.ParseException;
import java.time.LocalDateTime;

import ch.hslu.mobpro.routify.util.LocalDateTimeHelper;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class LocalDateTimeHelperTest {
    @Test
    public void testGetLocalDateTime() {
        LocalDateTime ldt = LocalDateTimeHelper.getLocalDateTime(1499070300L);
        System.out.println(ldt.toString());
        assertTrue(ldt.toString().equals("2017-07-03T10:25"));
    }
}
