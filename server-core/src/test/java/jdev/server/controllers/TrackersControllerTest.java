package jdev.server.controllers;

import jdev.dto.PointDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TrackersControllerTest {

    @Test
    public void test() throws Exception {

        TrackersController tc = new TrackersController();

        PointDTO point = new PointDTO();
        point.setLat(56.49771);
        point.setLon(84.97437);
        point.setAutoId("server9090");
        long currentTime = System.currentTimeMillis();
        point.setTime(currentTime);
        point.setSpeed(111);
        point.setBearing(99);
        ResponseEntity r = tc.getPoint(point);
        assertEquals(HttpStatus.CREATED, r.getStatusCode());
        assertEquals(point, r.getBody());
    }


}