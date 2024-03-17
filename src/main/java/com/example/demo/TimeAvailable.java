package com.example.demo;

public class TimeAvailable {
    Integer min = 0;
    Integer max = 0;
    
    public TimeAvailable(Integer min, Integer max) {
        this.min = min;
        this.max = max;
    }
    public Integer getMin() {
        return min;
    }
    public Integer getMax() {
        return max;
    }
    public void setMin(Integer min) {
        this.min = min;
    }
    public void setMax(Integer max) {
        this.max = max;
    }

    
}
