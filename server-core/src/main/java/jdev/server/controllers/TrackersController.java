package jdev.server.controllers;

/*
 * Created by srgva on 02.08.2017.
 */

import jdev.dto.PointDTO;
import jdev.dto.repo.PointsDbRepository;
import jdev.users.repo.RolesRepository;
import jdev.users.repo.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class TrackersController {
    private static final Logger log = LoggerFactory.getLogger(TrackersController.class);
    //@Autowired
    private PointsDbRepository pointsDbRepository;

/*    private RolesRepository rolesRepository;

    private UsersRepository usersRepository;
*/
    TrackersController(@Autowired PointsDbRepository pointsDbRepository){
        this.pointsDbRepository = pointsDbRepository;
/*        this.rolesRepository = rolesRepository;
        this.usersRepository = usersRepository;
*/
    }

    @Value("${fileToSave}")
    private String fileToSave;


    @RequestMapping(value = "/tracker", method = RequestMethod.POST,
         produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PointDTO> getPoint(@RequestBody PointDTO point) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept",MediaType.APPLICATION_JSON_UTF8_VALUE);


        PointDTO stored = pointsDbRepository.save(point);
        if(stored!=null) {
            if (stored.equals(point)) {
                log.info(" success get and save: " + point.toString()); // пишем в лог
                return new ResponseEntity<>(stored, headers, HttpStatus.CREATED);

            } else {
                log.info(" error: " + point.toString()); // пишем в лог
                return new ResponseEntity<>(stored, headers, HttpStatus.INTERNAL_SERVER_ERROR);

            }

        }
        return new ResponseEntity<>(stored, headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/route", method = RequestMethod.GET)
    public ResponseEntity<PointDTO[]> getTrack(@PathVariable String autoId, @PathVariable int maxPoints){
        PointDTO[] points = new PointDTO[maxPoints];
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", MediaType.APPLICATION_JSON_UTF8_VALUE);

        List<PointDTO> track = pointsDbRepository.findAllByAutoIdOrderByTimeDesc(autoId);
        if (track.size() == 0)
            return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);

        if(track.size() < maxPoints){
            points = (PointDTO[]) track.toArray();
        }else{
            for(int i = 0; i < maxPoints; i++){
              points[i] = track.get(i);
            }
        }
     return new ResponseEntity<>(points, headers, HttpStatus.OK);
    }
}

