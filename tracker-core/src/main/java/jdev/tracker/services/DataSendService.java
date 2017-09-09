package jdev.tracker.services;

import jdev.dto.PointDTO;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class DataSendService {

    /** Логгер сервиса передачи */
    private final Logger log = LoggerFactory.getLogger(DataSendService.class);

    @Value("${serverURL}")
    private String serverURL;

    private RestTemplate restTemplate;// = new RestTemplate();
    public DataSendService(){}
    public DataSendService(@Autowired RestTemplate restTemplate){this.restTemplate = restTemplate;}

    @Scheduled (cron = "${sendSchedule}") // параметры из файла-конфигурации (roadtoll.properties)
    public void dataSend()  {
        String url = serverURL + "/tracker";
        // RestTemplate для обращения к RESTful-сервису сервера
         //RestTemplate restTemplate = new RestTemplate();

        // Передача данных на сервер
        // пока есть данные в очереди сервиса хранения
        for(PointDTO point: DataSaveService.getSaveQueue()){
            // Передаем данные точки на сервер
            // для каждой точки из очереди сервиса хранения
            // формируем запрос к серверу
            // Отправляем данные на сервер и ожидаем результат
            try {
                HttpEntity <PointDTO> sendEntity = new HttpEntity<>(point, getHeaders());
                if(restTemplate == null)
                    restTemplate = new RestTemplate();
                ResponseEntity<?> response = restTemplate.postForEntity(url,  sendEntity, PointDTO.class);

                if (response.getStatusCode() == HttpStatus.CREATED) {
                    log.info(" send to server success: " + DataSaveService.getSaveQueue().poll());
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
    /* Формирование заголовка HTTP-запроса для авторизации на сервере
       в случае необходимости (логин=tracker : пароль=tracker) */
    private static HttpHeaders getHeaders(){
        String plainCredentials="tracker:tracker";
        String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        return headers;
    }

    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }

    public String getServerURL() {
        return serverURL;
    }
}
