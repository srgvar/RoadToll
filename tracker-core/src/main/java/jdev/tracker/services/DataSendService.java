package jdev.tracker.services;

import jdev.dto.PointDTO;
import jdev.dto.repo.PointsDbRepository;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;


/**
 * Created by srgva on 23.07.2017.
 */
@Service
//@EnableScheduling
//@Transactional
public class DataSendService {

    /**
     * Логгер сервиса передачи
     */
    private static final ThreadLocal<Logger> LOG = ThreadLocal.withInitial(() -> LoggerFactory.getLogger(DataSendService.class));

    @Value("${serverURL}")
    private  String serverURL;
    // RestTemplate для обращения к RESTful-сервису сервера
    //@Autowired
    private RestTemplate restTemplate;// = new RestTemplate();
    //@Autowired
    private  PointsDbRepository pointsDbRepository;
    //public DataSendService(){}

    public DataSendService(@Autowired PointsDbRepository pointsDbRepository){
       this.pointsDbRepository = pointsDbRepository;
    }

    /*public DataSendService(RestTemplate restTemplate, PointsDbRepository pointsDbRepository, ){
        this.restTemplate = restTemplate;
        this.pointsDbRepository = pointsDbRepository;
    }*/

    @Scheduled (cron = "${sendSchedule}") // параметры из файла-конфигурации (roadtoll.properties)
    public void sendToServer()  {
        String url = serverURL + "/tracker";

        // Передача данных на сервер
        // пока есть данные в очереди сервиса хранения
        for(PointDTO point : pointsDbRepository.findAll() ){
            // Передаем данные точки на сервер
            // для каждой точки из очереди сервиса хранения
            // формируем запрос к серверу
            // Отправляем данные на сервер и ожидаем результат
            try {
                HttpEntity<PointDTO> sendEntity = new HttpEntity<>(point, getHeaders());
                if (restTemplate == null) {
                    restTemplate = new RestTemplate();
                }
                ResponseEntity<PointDTO> response = restTemplate.postForEntity(url, sendEntity, PointDTO.class);

                if (response.getStatusCode() == HttpStatus.CREATED) {
                    pointsDbRepository.delete(point);
                    LOG.get().info(" send to server success: " + response.getBody());
                } else {
                    if(response.getStatusCode() == HttpStatus.CONFLICT){
                        LOG.get().error(" point on server now exists, local delete : " + response);
                        pointsDbRepository.delete(point);
                    }else {
                        LOG.get().error(" send to server FAILURE, error code: " + response);
                    }
                }
            }catch(Exception e){
                LOG.get().error(" send to server ERROR: " + e.toString());
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

    public  void setServerURL(String serverUrl) {
        this.serverURL = serverUrl;
    }

    public String getServerURL() {
        return serverURL;
    }

    public void setRestTemplate(RestTemplate restTemplate1) {
        this.restTemplate = restTemplate1;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }
}
