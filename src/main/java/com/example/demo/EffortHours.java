package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class EffortHours {
    String effortHourTeam;
    List<DeveloperEffortHour> developers = new ArrayList<>();
    public String getEffortHourTeam() {
        return effortHourTeam;
    }
    public void setEffortHourTeam(String effortHourTeam) {
        this.effortHourTeam = effortHourTeam;
    }
    public List<DeveloperEffortHour> getDevelopers() {
        return developers;
    }
    public void setDevelopers(List<DeveloperEffortHour> developers) {
        this.developers = developers;
    }

    
}
