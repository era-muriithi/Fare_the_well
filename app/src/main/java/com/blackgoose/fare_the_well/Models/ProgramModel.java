package com.blackgoose.fare_the_well.Models;

import java.io.Serializable;

public class ProgramModel implements Serializable {
    private String Starttime;


    private String Completiontime;
    private String action;

    public ProgramModel() {}

    public ProgramModel(String Starttime, String Completiontime, String action) {
        this.Starttime = Starttime;
        this.Completiontime = Completiontime;
        this.action = action;
    }
    public String getCompletiontime() {
        return Completiontime;
    }

    public void setCompletiontime(String completiontime) {
        Completiontime = completiontime;
    }

    public String getStarttime() {
        return Starttime;
    }

    public void setStarttime(String starttime) {
        Starttime = starttime;
    }


    public String getAction() {
        return action;
    }
}