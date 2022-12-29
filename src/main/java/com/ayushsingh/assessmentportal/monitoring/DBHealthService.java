package com.ayushsingh.assessmentportal.monitoring;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Controller;

@Controller  //make it a component so that it can be used
public class DBHealthService implements HealthIndicator {


    /**
     *
     */
    private static final String DB_SERVICE = "Database Service";
    public boolean checkHealth(){
        //return true if health is good
        //can write custom logic for hardware or software
        return true;
    }
    @Override
    public Health health() {
        if(checkHealth()==true){
            return Health.up().withDetail(DB_SERVICE, "Database is running").build();
        }
        return Health.down().withDetail(DB_SERVICE, "Database Connection not established").build();
    }
    
}
