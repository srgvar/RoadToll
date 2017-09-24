package jdev.tracker.services;

import jdev.dto.PointDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class DataSaveServiceTest {
    @InjectMocks
    private static final GpsService gpsMock = new GpsService();
    @InjectMocks
    private static final DataSaveService dataSaveServiceMock = new DataSaveService();


    // TODO ***** Изменить тест для работы с СУБД *****

    @Test
    public void put() throws Exception {
        gpsMock.setAutoId("save123");
        gpsMock.setCoordinates(gpsMock.readCoordinates(".\\\\resource\\\\tracks\\\\10158.kml"));
        assertNotNull(gpsMock.getCoordinates());
        assertEquals(826, gpsMock.getCoordinates().size());
        for(int i = 0; i < 10; i++) {
            gpsMock.put();
        }
        // 10 точек с координатами перемещены в очередь сервиса GPS
        assertEquals(10, gpsMock.getGpsQueue().size());
        assertEquals(816, gpsMock.getCoordinates().size());

        for(PointDTO point : gpsMock.getGpsQueue()){
            dataSaveServiceMock.put();
        }
        //  Данные из очереди сервиса GPS перемещены в очередь сервиса хранения
            assertEquals(10, DataSaveService.getSaveQueue().size());
        // Очердь сервиса GPS пуста
            assertEquals(0, gpsMock.getGpsQueue().size());
            DataSaveService.getSaveQueue().clear();

    }


}