package com.quartciphers.skillscy.controller;

import com.qc.skillscy.commons.dto.HealthStatus;
import com.qc.skillscy.commons.loggers.CommonLogger;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class MainWSController {

    @GetMapping(value = "/hello", produces = "application/json")
    @ApiOperation(value = "A hello method which check health", nickname = "Hello method", notes = "This method returns the running status of the application.", produces = "application/json", response = HealthStatus.class)
    public ResponseEntity<HealthStatus> helloToHealth() {
        CommonLogger.info(this.getClass(), "---------- MainWS API 'helloToHealth' STARTED ----------");
        HealthStatus healthStatus = new HealthStatus(ServletUriComponentsBuilder.fromCurrentContextPath().toUriString()); // creates HealthStatus object with necessary parameters
        CommonLogger.info(this.getClass(), "---------- MainWS API 'helloToHealth' COMPLETED ----------");
        return ResponseEntity.ok(healthStatus); // returns the response to the consumer
    }

}
