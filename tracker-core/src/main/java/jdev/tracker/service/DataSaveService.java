package jdev.tracker.service;

import jdev.dto.PointDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by srgva on 23.07.2017.
 *
 * Сервис хранения данных
 */
@Service
@EnableScheduling
public class DataSaveService {

    /** Очередь сервиса харнения */
    protected static BlockingDeque<PointDTO> saveQueue =  new LinkedBlockingDeque<PointDTO>(300);
    // Логгер сервиса хранения
    private static final Logger log = LoggerFactory.getLogger(DataSaveService.class);
    // Сервис GPS
    private static GpsService gps;

    @Scheduled(cron = "${gpsSchedule}") // Используем расписание сервиса GPS
    void put() throws InterruptedException {
        PointDTO point = new PointDTO();
        point = gps.gpsQueue.take(); // Получаем точку от сервиса GPS
        log.info(System.currentTimeMillis() + " DataSaveService " + point.toString()); //500, TimeUnit.MILLISECONDS));
        /** Сохраняем информацию о точке - в нашем случае
         * помещаем в очередь сервиса хранения */
        saveQueue.put(point);
    }
}
