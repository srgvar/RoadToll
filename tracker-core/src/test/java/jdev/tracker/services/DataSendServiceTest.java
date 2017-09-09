package jdev.tracker.services;

import jdev.dto.PointDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DataSendServiceTest {
    @InjectMocks
    private static final GpsService gpsMock = new GpsService();
    @InjectMocks
    private static final DataSaveService dataSaveServiceMock = new DataSaveService();
    @Mock
    private RestTemplate restTemplateMock = new RestTemplate();

    @InjectMocks
    private DataSendService dataSendServiceMock = new DataSendService(restTemplateMock);

    @Test
    public void dataSend() throws Exception {
        gpsMock.setAutoId("send123");
        gpsMock.setCoordinates(gpsMock.readCoordinates(".\\\\resource\\\\tracks\\\\10158.kml"));
        assertNotNull(gpsMock.getCoordinates());
        assertEquals(826, gpsMock.getCoordinates().size());
        for(int i = 0; i < 10; i++) {
            gpsMock.put();
        }
        // 10 точек с координатами перемещены в очередь сервиса GPS
        assertEquals(10, gpsMock.getGpsQueue().size());
        assertEquals(816, gpsMock.getCoordinates().size());

        for(PointDTO point : gpsMock.getGpsQueue()) {
            dataSaveServiceMock.put();
        }
        //  Данные из очереди сервиса GPS перемещены в очередь сервиса хранения
        assertEquals(10, DataSaveService.getSaveQueue().size());
        // Очердь сервиса GPS пуста
        assertEquals(0, gpsMock.getGpsQueue().size());
        // Адрес сервера
        String url = "http://localhost:9090";

        dataSendServiceMock.setServerURL(url);

        /*
        Использование заглушки RestTemplate для сервиса передачи dataSendServiceMock

        Ответ от сервера - ошибка
        */
        when(restTemplateMock.postForEntity(eq("http://localhost:9090/tracker"), any(HttpEntity.class), eq(PointDTO.class))).thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
            dataSendServiceMock.dataSend();
        // данные остались в очереди
        assertEquals(10, DataSaveService.getSaveQueue().size());

        // Ответ от сервера - ОК
        when(restTemplateMock.postForEntity(eq("http://localhost:9090/tracker"), any(HttpEntity.class), eq(PointDTO.class))).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));
            dataSendServiceMock.dataSend();
        // Очередь сервиса хранения после передачи - пуста
        assertEquals(0, DataSaveService.getSaveQueue().size());

    }
}