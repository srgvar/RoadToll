package jdev.tracker.services;

import jdev.dto.PointDTO;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

//import org.apache.tomcat.jni.SSLContext;
//import org.apache.http.conn.ssl.*;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//import javax.net.ssl.SSLContext;


/**
 * Created by srgva on 23.07.2017.
 */
@Service
@EnableScheduling
class DataSendService {

    /** Логгер сервиса передачи */
    private static final Logger log = LoggerFactory.getLogger(DataSendService.class);

    // сервис хранения
    private static DataSaveService dataSaveService;

    @Value("${serverURL}")
    private String serverURL;

    @Scheduled (cron = "${sendSchedule}") // параметры из файла-конфигурации (roadtoll.properties)
    private void dataSend()  {
        String url = serverURL + "/tracker";


        /* ******** Аутентификация ********* */


        /* ------- Аутентификация -------- */

        RestTemplate restTemplate = new RestTemplate();


        // Передаем данные на сервер
        // пока есть данные в очеерди сервиса хранения
        for(PointDTO point: DataSaveService.saveQueue){
            // Передаем данные точки на сервер
            // для каждой точки из очереди сервиса хранения
            // формируем запрос к серверу
            // Отправляем данные на сервер и ожидаем результат
            try {
                HttpEntity <PointDTO> sendEntity = new HttpEntity<>(point, getHeaders());

                ResponseEntity<?> response = restTemplate.postForEntity(url,  sendEntity, PointDTO.class);

                if (response.getStatusCode() == HttpStatus.CREATED) {
                    log.info(" send to server success: " + DataSaveService.saveQueue.poll());
                } else {
                    log.error(" send to server FAILURE, error code: " +  response +
                            " for point:" + point);
                    break;
                }
            }catch(Exception e){
                log.error(" send to server ERROR: " + e.getMessage());
            }
        }
    }
    private static HttpHeaders getHeaders(){
        String plainCredentials="tracker:tracker";
        String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        return headers;
    }

}
