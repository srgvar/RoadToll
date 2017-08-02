package jdev.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by srgva on 29.07.2017.
 */
public class Response {
    public static final long L_SUCCESS = 777;
    public static final long L_FAILURE = -1777;
    public static final String S_SUCCESS = "SUCC";
    public static final String S_FAILURE = "FAIL";

    long id;
    String result;

    public Response(long id, String result){
        this.id = id;
        this.result = result;
    }

    public long getId(){return id;}
    public String getResult(){return result;}
    public void setId(long id){this.id = id;}
    public void setResult(String result){this.result = result;}

    public String toJson() {
        String string = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            string =  mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return string;
    }
}
