package com.blackgoose.fare_the_well.Models;

import java.io.Serializable;

public class ProgramModel implements Serializable {
    private String startTime;
    private String action;

    public ProgramModel() {}

    public ProgramModel(String startTime, String action) {
        this.startTime = startTime;
        this.action = action;
    }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
}
