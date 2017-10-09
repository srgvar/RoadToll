package jdev.tracker.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
@RunWith(MockitoJUnitRunner.class)
public class GpsServiceTest {
    private static final GpsService gps = new GpsService();

    @Test
    public void put() throws Exception {

        gps.setAutoId("gps123");
        gps.setCoordinates(gps.readCoordinates("\\resource\\tracks\\10158.kml"));
        // Список координат - не пуст
        assertNotNull(gps.getCoordinates());
        // Записей координат в списке 826
        assertEquals(826, gps.getCoordinates().size());
        for(int i = 0; i < 10; i++) {
            gps.put();
        }
        assertEquals(10, gps.getGpsQueue().size());
            gps.getGpsQueue().clear();
    }
}