package com.example.demo;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Component
public class Controller {

    @PostMapping(value = "/average-velocity")
    public Double getAverageVelocity(@RequestBody List<SprintPoint> payload) throws Exception {
        
       return  getVelocity(payload);

    }

    @PostMapping(value = "/team-effort-hour-capacity")
    public EffortHours getAvailableEffortHours(@RequestBody Capacity payload) throws Exception {
        Double minAvg = payload.getDevelopers().stream().mapToDouble( d -> d.getRange().getMin() - d.getPto() - d.getCeremonies()).average().getAsDouble();
        Double maxAvg = payload.getDevelopers().stream().mapToDouble( d -> d.getRange().getMax() - d.getPto() - d.getCeremonies()).average().getAsDouble();
        EffortHours effortHours = new EffortHours();
        List<DeveloperEffortHour> deh = effortHours.getDevelopers();
        effortHours.setEffortHourTeam(minAvg * payload.sprintDuration + "-" + maxAvg * payload.sprintDuration);
        setDevelopers(payload, deh);
        
        return effortHours;
    }

    public void setDevelopers(Capacity payload, List<DeveloperEffortHour> deh) throws Exception {
        if (payload.getDevelopers() == null) {
            throw new Exception("Invalid developer exception");
        }
        for (Developer dev : payload.getDevelopers()) {
            int min = dev.getRange().getMin() - dev.getPto() - dev.getCeremonies();
            int max = dev.getRange().getMax() - dev.getPto() - dev.getCeremonies();
            String range = min * payload.sprintDuration + "-" + max * payload.sprintDuration;
            DeveloperEffortHour devEH = new DeveloperEffortHour(dev.getName(), range);
            deh.add(devEH);
        }
    }


    public Double getVelocity(List<SprintPoint> payload) throws Exception {
        if(payload == null) {
            throw new Exception("Invalid paramter exception");
        }
        return payload.stream().mapToDouble(SprintPoint::getCompletedPoints).average().orElse(Double.NaN);
    }
}
