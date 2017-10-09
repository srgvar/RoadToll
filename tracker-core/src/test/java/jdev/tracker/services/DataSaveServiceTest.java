package jdev.tracker.services;

import jdev.dto.PointDTO;
import jdev.dto.repo.PointsDbRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class DataSaveServiceTest {

    @InjectMocks
    private static final GpsService gpsMock = new GpsService();

    @Mock
    private PointsDbRepository pointsDbRepositoryMock;

    @InjectMocks
    private final DataSaveService dataSaveServiceMock = new DataSaveService(pointsDbRepositoryMock);

    @Test
    public void saveFromGpsQueueToDatabase() throws Exception {
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

        // Заглушка для операции записи в БД
        when(pointsDbRepositoryMock.save(any(PointDTO.class))).thenAnswer((Answer<PointDTO>) invocation -> {
            Object args[] =  invocation.getArguments();
            PointDTO point;// = new PointDTO();
                    point = (PointDTO) args[0];
            return point;
        });

        //for(PointDTO point : gpsMock.getGpsQueue()){
            dataSaveServiceMock.saveToDb();
        //}

        // Очердь сервиса GPS пуста
            assertEquals(0, gpsMock.getGpsQueue().size());
    }
}