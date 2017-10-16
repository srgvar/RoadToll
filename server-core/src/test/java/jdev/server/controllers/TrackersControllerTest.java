package jdev.server.controllers;

import jdev.dto.PointDTO;
import jdev.dto.repo.PointsDbRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrackersControllerTest {
    @Mock
    private PointsDbRepository pointsDbRepositoryMock;
    /*@Mock
    RolesRepository rolesRepositoryMock;
    @Mock
    UsersRepository usersRepositoryMock;*/

    private List<PointDTO> testList = new ArrayList<>();


    @Test
    public void testGetPoint() throws Exception {

        TrackersController tc = new TrackersController(pointsDbRepositoryMock);

        PointDTO point = new PointDTO();
        point.setLat(56.49771);
        point.setLon(84.97437);
        point.setAutoId("server9090");
        long currentTime = System.currentTimeMillis();
        point.setTime(currentTime);
        point.setSpeed(111);
        point.setBearing(99);

        when(pointsDbRepositoryMock.save(any(PointDTO.class))).thenAnswer((Answer<PointDTO>) invocation -> {
            Object args[] =  invocation.getArguments();
            PointDTO point1;// = new PointDTO();
            point1 = (PointDTO) args[0];
            return point1;
        });

        ResponseEntity r = tc.savePoint(point);
        assertEquals(HttpStatus.CREATED, r.getStatusCode());
        assertEquals(point, r.getBody());
    }

    @Test
    public void testGetTrack()throws Exception{
        TrackersController tc = new TrackersController(pointsDbRepositoryMock);
        String autoIdTest = "test001";
        PointDTO[] track;
        int maxPointsTest = 7;

        String testJsonPoints[] = {
        "{\"id\":794,\"lat\":43.914639,\"lon\":131.989942,\"bearing\":19.976533720678162,\"speed\":51.36256922793203,\"autoId\":\"test001\",\"time\":10000}",
        "{\"id\":795,\"lat\":43.915809,\"lon\":131.990533,\"bearing\":19.995108749416882,\"speed\":138.48235297485786,\"autoId\":\"test001\",\"time\":20000}",
        "{\"id\":796,\"lat\":43.917146,\"lon\":131.991209,\"bearing\":20.01223413157322,\"speed\":158.26588741934378,\"autoId\":\"test001\",\"time\":30000}",
        "{\"id\":797,\"lat\":43.918126,\"lon\":131.991704,\"bearing\":19.993442157460063,\"speed\":115.99251357160408,\"autoId\":\"test001\",\"time\":40000}",
        "{\"id\":798,\"lat\":43.919583,\"lon\":131.99244,\"bearing\":19.994640706051626,\"speed\":172.45149828572957,\"autoId\":\"test001\",\"time\":50000}",
        "{\"id\":799,\"lat\":43.921204,\"lon\":131.993259,\"bearing\":19.997638498442228,\"speed\":191.8663365308212,\"autoId\":\"test001\",\"time\":60000}",
        "{\"id\":800,\"lat\":43.922419,\"lon\":131.993872,\"bearing\":19.97113978023512,\"speed\":143.7867291465711,\"autoId\":\"test001\",\"time\":70000}",
        "{\"id\":801,\"lat\":43.923707,\"lon\":131.994738,\"bearing\":25.839781400375955,\"speed\":159.17463936951324,\"autoId\":\"test001\",\"time\":80000}",
        "{\"id\":802,\"lat\":43.924583,\"lon\":131.995887,\"bearing\":43.371461012974095,\"speed\":134.03849987603533,\"autoId\":\"test001\",\"time\":90000}",
        "{\"id\":803,\"lat\":43.925104,\"lon\":131.997153,\"bearing\":60.257034156899145,\"speed\":116.80810022014441,\"autoId\":\"test001\",\"time\":100000}"};

        for(String jsonPoint: testJsonPoints)
            testList.add(new PointDTO(jsonPoint));

        when(pointsDbRepositoryMock.findAllByAutoIdOrderByTimeDesc(autoIdTest)).thenReturn(testList);

        track = (tc.getTrack("test001",maxPointsTest)).getBody();

        for(int i = 0; i < maxPointsTest; i++) {
            System.out.println(testList.get(i));
            assertEquals(testList.get(i), track[i]);
        }
    }
}