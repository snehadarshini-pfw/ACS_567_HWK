package com.example.demo;

public class SprintPoint {
    Double completedPoints;

    
    public SprintPoint() {
        
    }
    public SprintPoint(Double completedPoints) {
        this.completedPoints = completedPoints;
    }

    public Double getCompletedPoints(){
        return completedPoints;
    }
    
    public void setCompletedPoints(Double completedPoints) {
        this.completedPoints = completedPoints;
    }
}
