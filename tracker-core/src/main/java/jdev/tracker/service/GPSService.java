package jdev.tracker.service;
import de.micromata.opengis.kml.v_2_2_0.*;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

public class GPSService {
    public void getCoordinates(String fileName) {
        File f = new File(fileName);
        System.out.println(f);

        final Kml kml = Kml.unmarshal(f);
          Folder folder =  (Folder) kml.getFeature();
           List <Feature> features = folder.getFeature();
            Placemark placemark = new Placemark();
        // Просматриваем все объекты Placemarks
        for(Feature feature : features){
            placemark = (Placemark) feature;
               LineString lineString = (LineString) placemark.getGeometry();
                 List <Coordinate> coordinates = lineString.getCoordinates();
             if(!coordinates.isEmpty()){
                 for (Coordinate coordinate : coordinates) {
                     //получаем координаты
                     System.out.println("coordinate: lat = " + coordinate.getLatitude()+
                                         "\tlon = " + coordinate.getLongitude());
                 }
             }
        }

        /*for(Placemark placemark: placemarks ) {
            Point point = (Point) placemark.getGeometry();
            System.out.println("point = " + point);
            List<Coordinate> coordinates = point.getCoordinates();

            for (Coordinate coordinate : coordinates) {
                System.out.println(coordinate.getLatitude());
                System.out.println(coordinate.getLongitude());
                System.out.println(coordinate.getAltitude());
            }
        }*/



    }
}
