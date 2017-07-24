package jdev.tracker.service;

import jdev.dto.PointDTO;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by srgva on 23.07.2017.
 *
 * Сервис хранения данных
 */
@Service
@EnableScheduling
public class DataSaveService {
    private static BlockingDeque<PointDTO> gpsQueue; // очередь сервиса GPS
    /** Очередь сервиса харнения */
    private static BlockingDeque<PointDTO> saveQueue =  new LinkedBlockingDeque<PointDTO>(300);
    // Логгер сервиса хранения
    private static final Logger log = LoggerFactory.getLogger(DataSaveService.class);

    @Autowired
    private DataSendService dataSendService;

    @PostConstruct
    private void init(){
        /** Передаем адрес очереди сервиса хранения в сервис передачи */
        dataSendService.setSaveQueue(saveQueue);
    }
    /** получаем и устанавливаем адрес сочреди сервиса GPS */
    void setGpsQueue(BlockingDeque<PointDTO> gpsQueue){
        this.gpsQueue = gpsQueue;
    }

    @Scheduled(cron = "${gpsSchedule}") // Используем расписание сервиса GPS
    void put() throws InterruptedException {
        PointDTO point = new PointDTO();
        point = gpsQueue.take(); // Получаем точку от сервиса GPS
        log.info(System.currentTimeMillis() + " DataSaveService " + point.toString()); //500, TimeUnit.MILLISECONDS));
        /** Сохраняем информацию о точке - в нашем случае
         * помещаем в очередь сервиса хранения */
        saveQueue.put(point);
    }



}
