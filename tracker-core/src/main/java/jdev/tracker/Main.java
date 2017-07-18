package jdev.tracker;

import jdev.dto.PointDTO;

/**
 * Created by srgva on 17.07.2017.
 */
public class Main {
    static long delayTime = 1000L;
    public void setDelayTime(long delayTime){
        this.delayTime = delayTime;
    }
    public long getDelayTime(){
        return delayTime;
    }

    public static void main(String... args) {
        PointDTO dto = new PointDTO();
        dto.setAutoId("a123bcd");

        System.out.println("Tracker working");

        for(int i=0; i<5; i++){
          dto.setLat(Math.random()*100);
          dto.setLon(Math.random()*100);
          dto.setTime(System.currentTimeMillis());
            System.out.println(dto.toString());
            delay();
        }



    } // main()



    private static void delay(){
        try {
            Thread.sleep(delayTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
} // class

