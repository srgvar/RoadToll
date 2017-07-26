package jdev.tracker;

import jdev.tracker.service.DataSaveService;
import jdev.tracker.service.DataSendService;
import jdev.tracker.service.GpsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * Created by srgva on 21.07.2017.
 */
@Configuration
@EnableScheduling
@PropertySource("classpath:/roadtoll.properties")
class TrackerCoreContext {

    // Сервис храниеия
    @Bean
    private static DataSaveService dataSaveService(){return new DataSaveService();    }

    // Сервис передачи
    @Bean
    private static DataSendService dataSendService(){return new DataSendService();}

    // Сервис GPS
    @Bean
    private static GpsService gps(){
        return new GpsService();
    }

    // Шедулер
    @Bean
    public TaskScheduler poolScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("poolScheduler");
        scheduler.setPoolSize(20);
        return scheduler;
    }

}
