package com.example.demo;

public class Developer {
    String name;
    int pto;
    int ceremonies;
    TimeAvailable range;

    
    public Developer(String name, int pto, int ceremonies, TimeAvailable range) {
        this.name = name;
        this.pto = pto;
        this.ceremonies = ceremonies;
        this.range = range;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getPto() {
        return pto;
    }
    public void setPto(int pto) {
        this.pto = pto;
    }
    public int getCeremonies() {
        return ceremonies;
    }
    public void setCeremonies(int ceremonies) {
        this.ceremonies = ceremonies;
    }
    public TimeAvailable getRange() {
        return range;
    }
    public void setRange(TimeAvailable range) {
        this.range = range;
    }

    


    
}
