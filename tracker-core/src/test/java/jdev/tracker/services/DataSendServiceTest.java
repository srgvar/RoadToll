package jdev.tracker.services;

import jdev.dto.PointDTO;
import jdev.dto.repo.PointsDbRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DataSendServiceTest {

    @Mock
    private RestTemplate restTemplateMock = new RestTemplate();

    @Mock
    private PointsDbRepository pointsDbRepositoryMock;

    @InjectMocks
    private static final GpsService gpsMock = new GpsService();
    @InjectMocks
    private final DataSaveService dataSaveServiceMock = new DataSaveService(pointsDbRepositoryMock);

    @InjectMocks
    private DataSendService dataSendServiceMock = new DataSendService(pointsDbRepositoryMock);

    @Test
    public void dataSend() throws Exception {
         BlockingDeque<PointDTO> all = new LinkedBlockingDeque<>();

        dataSendServiceMock.setRestTemplate(restTemplateMock);

        gpsMock.setAutoId("send123");
        gpsMock.setCoordinates(gpsMock.readCoordinates(".\\\\resource\\\\tracks\\\\10158.kml"));
        assertNotNull(gpsMock.getCoordinates());
        assertEquals(826, gpsMock.getCoordinates().size());
        for(int i = 0; i < 10; i++) {
            //all.add(gpsMock.getGpsQueue().peek());
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
            all.add(point);
            return point;
        });

        //for(PointDTO point : gpsMock.getGpsQueue()) {
            dataSaveServiceMock.saveToDb();
        //}
        //  Данные из очереди сервиса GPS перемещены в очередь сервиса хранения
        //assertEquals(10, DataSaveService.getSaveQueue().size());
        // Очердь сервиса GPS пуста
        assertEquals(0, gpsMock.getGpsQueue().size());
        // Адрес сервера
        String url = "http://localhost:9090";

        dataSendServiceMock.setServerURL(url);

        when(pointsDbRepositoryMock.findAll()).thenReturn((BlockingDeque<PointDTO>)all);


        doAnswer((Answer<Void>) invocation -> {
            Object[] args = invocation.getArguments();
            if (args[0] != null) {

                PointDTO point = (PointDTO) args[0];
                all.remove(point);
            }
            return null;
        }).when(pointsDbRepositoryMock).delete(any(PointDTO.class));



        /*
        Использование заглушки RestTemplate для сервиса передачи dataSendServiceMock

        Ответ от сервера - ошибка
        */


       //when(restTemplateMock.postForEntity(eq("http://localhost:9090/tracker"), any(HttpEntity.class), eq(PointDTO.class))).thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        //    dataSendServiceMock.sendToServer();
        // данные остались в очереди
        //assertEquals(10, DataSaveService.getSaveQueue().size());

        // Ответ от сервера - ОК
        // when(restTemplateMock.postForEntity(eq("http://localhost:9090/tracker"), any(HttpEntity.class), eq(PointDTO.class))).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));

        when(restTemplateMock.postForEntity(eq("http://localhost:9090/tracker"), any(HttpEntity.class), eq(PointDTO.class))).thenAnswer((Answer<ResponseEntity>) invocation -> {
            Object[] args = invocation.getArguments();
            HttpEntity httpEntity = (HttpEntity)args[1];
            PointDTO point = (PointDTO)httpEntity.getBody();
            return new ResponseEntity <>(point, null, HttpStatus.CREATED);
        });

            dataSendServiceMock.sendToServer();
        // Очередь сервиса хранения после передачи - пуста
        // assertEquals(0, DataSaveService.getSaveQueue().size());

    }
}