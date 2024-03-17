package com.example.demo;

import java.util.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.servlet.http.HttpServletRequest;

@SpringBootTest(classes = Controller.class)
public class ControllerTests {
 
    @Autowired
    Controller controller;

    @Mock
    HttpServletRequest mockRequest;
 

    @Test
    public void test_average_velocity() throws Exception {
       List<SprintPoint> list = new ArrayList();
       list.add(new SprintPoint(30.00));
       list.add(new SprintPoint(20.00));
        Double response = controller.getVelocity(list);
        Assertions.assertEquals(response, 25.00);
    }

    @Test
    public void test_average_velocity_exception_scenario() throws Exception {
    
        Exception thrown = Assertions.assertThrows(
            Exception.class,
           () -> controller.getVelocity(null),
           "Expected function to throw, but it didn't"
        );
        Assertions.assertTrue(thrown.getMessage().contains("Invalid"));
    }
    


    @Test
    public void test_setDeveloper() throws Exception {
      
       Capacity capacity = new Capacity();
       capacity.setSprintDuration(2);
       Developer developer1 = new Developer("a", 1, 1, new TimeAvailable(10, 12));
       Developer developer2 = new Developer("b", 1, 2, new TimeAvailable(8, 10));
       List<Developer> devs = new ArrayList();
       devs.add(developer1);
       devs.add(developer2);
       capacity.setDevelopers(devs);
       List<DeveloperEffortHour> list = new ArrayList<DeveloperEffortHour>();
       controller.setDevelopers(capacity, list);
        Assertions.assertEquals(list.get(0).getEffortHourRange(), "16-20");
    }

    @Test
    public void test_setDeveloper_exception() throws Exception {
      
       Capacity capacity = new Capacity();
       capacity.setSprintDuration(2);
       List<DeveloperEffortHour> list = new ArrayList<DeveloperEffortHour>();
       
       Exception thrown = Assertions.assertThrows(
            Exception.class,
           () -> controller.setDevelopers(capacity, list),
           "Expected function to throw, but it didn't"
        );
        Assertions.assertTrue(thrown.getMessage().contains("Invalid developer exception"));
    }
}
