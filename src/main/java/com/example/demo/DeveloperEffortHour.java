package com.example.demo;

public class DeveloperEffortHour {
    String name;
    String effortHourRange;

    
    public DeveloperEffortHour(String name, String effortHourRange) {
        this.name = name;
        this.effortHourRange = effortHourRange;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEffortHourRange() {
        return effortHourRange;
    }
    public void setEffortHourRange(String effortHourRange) {
        this.effortHourRange = effortHourRange;
    }

    
}
