package jdev.tracker.services;

import jdev.dto.PointDTO;
import jdev.dto.repo.PointsDbRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import static jdev.tracker.services.GpsService.*;

/**
 * Created by srgva on 23.07.2017.
 *
 * Сервис хранения данных
 */
@Service
//@EnableScheduling
//@Transactional
//@SpringBootTest
public class DataSaveService {

    // Логгер сервиса хранения
    private static final Logger log = LoggerFactory.getLogger(DataSaveService.class);

    // Интерфейс репозитория
    private PointsDbRepository pointsDbRepository;

    // Конструктор с инициализацией репозитория
    public DataSaveService(@Autowired PointsDbRepository dbRepository){
       this.pointsDbRepository = dbRepository;
    }

    /* Используем расписание сервиса GPS */
    @Scheduled(cron = "${saveSchedule}")
    public void saveToDb()  {
        PointDTO  savedPoint; // = new PointDTO();

        for(PointDTO point : getGpsQueue()) {
            try {

            /* Сохраняем информацию о точке в БД */
                if (!(point == null)) {
                    savedPoint = pointsDbRepository.save(point);
                    //
                    if (point.equals(savedPoint)) {
                        log.info(" save to database point: " + gpsQueue.take());
                    } else {
                        log.error(" ERROR saving point: " + point);
                    }
                }
                } catch(InterruptedException e){
                    log.error(e.getMessage());
                    e.printStackTrace();
                } //try

            }//if

    }

    public PointsDbRepository getPointsDbRepository() {
        return pointsDbRepository;
    }

}
