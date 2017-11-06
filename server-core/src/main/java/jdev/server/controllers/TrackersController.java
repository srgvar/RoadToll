package jdev.server.controllers;

/*
 * Created by srgva on 02.08.2017.
 */

import jdev.dto.PointDTO;
import jdev.dto.RequestRoute;
import jdev.dto.repo.PointsDbRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;


@RestController
public class TrackersController {
    private static final ThreadLocal<Logger> LOG = ThreadLocal.withInitial(() -> LoggerFactory.getLogger(TrackersController.class));

    //@Autowired
    private final PointsDbRepository pointsDbRepository;


TrackersController(@Autowired PointsDbRepository pointsDbRepository){
        this.pointsDbRepository = pointsDbRepository;
    }

    @RequestMapping(value = "/tracker", method = RequestMethod.POST,
         produces = MediaType.APPLICATION_JSON_UTF8_VALUE)


    public ResponseEntity<PointDTO> savePoint(@RequestBody PointDTO point) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept",MediaType.APPLICATION_JSON_UTF8_VALUE);

        /* Проверка на наличие данного местоположения авто */
        if(pointsDbRepository.findFirstByAutoIdAndTimeStamp(point.getAutoId(), point.getTimeStamp())!=null){
            LOG.get().error("Контрольная точка уже существует - " + point);
            headers.add("message","Such a control point already exists");
            return new ResponseEntity<>(point, headers, HttpStatus.CONFLICT);
        }
        try {
            PointDTO stored = pointsDbRepository.save(point);


        if(stored != null) {
            if (stored.equals(point)) {
                LOG.get().info(" success get and save: " + point.toString()); // пишем в лог
                return new ResponseEntity<>(stored, headers, HttpStatus.CREATED);

            } else {
                LOG.get().error(" error: " + point.toString()); // пишем в лог
                return new ResponseEntity<>(stored, headers, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        } catch (Exception e){
          LOG.get().error("< ERROR > " + e.getMessage() + e.getClass());
        }
        return new ResponseEntity<>(null, headers, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @RequestMapping(value = "/route", method = RequestMethod.GET)
    public ResponseEntity<PointDTO[]> getTrack(@RequestBody RequestRoute requestRoute){

    String autoId=requestRoute.getAutoId();
    Integer maxPoints=requestRoute.getScope();
        PointDTO[] points = new PointDTO[maxPoints];
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        PageRequest pageRequest = new PageRequest(0, maxPoints);
        ArrayList<PointDTO> track =  (ArrayList<PointDTO>) pointsDbRepository.findAllByAutoIdOrderByTimeStampDesc(autoId);
        if (track.size() == 0)
            return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);

        if(track.size() < maxPoints){
            points = (PointDTO[]) track.toArray();
        }else{
            int i = 0;
            if (i < maxPoints) {
                do {
                    points[i] = track.get(i);
                    i++;
                } while (i < maxPoints);
            }
        }
     return new ResponseEntity<>(points, headers, HttpStatus.OK);
    }
}

