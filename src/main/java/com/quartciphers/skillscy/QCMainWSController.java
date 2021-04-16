package com.quartciphers.skillscy;

import com.quartciphers.skillscy.dto.HealthStatus;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class QCMainWSController {

    @GetMapping(value = "/hello", produces = "application/json")
    @ApiOperation(value = "A hello method which check health", nickname = "Hello method", notes = "This method returns the running status of the application along with the application URL, Swagger URL and other company related information", produces = "application/json", response = HealthStatus.class)
    public ResponseEntity<HealthStatus> helloToHealth() {
        HealthStatus healthStatus = new HealthStatus(ServletUriComponentsBuilder.fromCurrentContextPath().toUriString()); // creates HealthStatus object with necessary parameters
        return ResponseEntity.ok(healthStatus); // returns the response to the consumer
    }

}
