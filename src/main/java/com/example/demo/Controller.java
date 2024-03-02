package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Component
public class Controller {
    @PostMapping(value = "/average-velocity")
    public Double getAverageVelocity(@RequestBody List<SprintPoint> payload) {
        
       return  payload.stream().mapToDouble(SprintPoint::getCompletedPoints).average().orElse(Double.NaN);

       // return 1;
    }

    @PostMapping(value = "/team-effort-hour-capacity")
    public Double getAvailableEffortHours(@RequestBody List<Capacity> payload) {
       
    }
}
