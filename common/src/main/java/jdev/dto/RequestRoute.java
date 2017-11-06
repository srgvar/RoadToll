package jdev.dto;


import com.fasterxml.jackson.annotation.ObjectIdGenerators;

public class RequestRoute {
    private String autoId;
    private int scope;

    public RequestRoute(){}

    public RequestRoute(String autoId, Integer scope){
        this.autoId = autoId;
        this.scope = scope;
    }


    public String getAutoId() {
        return autoId;
    }

    public void setAutoId(String autoId) {
        this.autoId = autoId;
    }


    public Integer getScope() {
        return scope;
    }

    public void setScope(Integer scope) {
        this.scope = scope;
    }


}
