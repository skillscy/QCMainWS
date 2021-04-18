package com.quartciphers.skillscy;

import com.quartciphers.skillscy.dto.HealthStatus;
import com.quartciphers.skillscy.dto.WebServiceCommonResponse;
import com.quartciphers.skillscy.dto.YouTubeCardResponse;
import com.quartciphers.skillscy.service.QCMainWSService;
import com.quartciphers.skillscy.vo.WebServiceException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
public class QCMainWSController {

    @Autowired
    private QCMainWSService service;

    @GetMapping(value = "/api/hello", produces = "application/json")
    @ApiOperation(value = "A hello method which check health", nickname = "Hello method", notes = "This method returns the running status of the application along with the application URL, Swagger URL and other company related information", produces = "application/json", response = HealthStatus.class)
    public ResponseEntity<HealthStatus> helloToHealth() {
        HealthStatus healthStatus = new HealthStatus(ServletUriComponentsBuilder.fromCurrentContextPath().toUriString()); // creates HealthStatus object with necessary parameters
        return ResponseEntity.ok(healthStatus); // returns the response to the consumer
    }

    @GetMapping(value = "/api/v1/youtube", produces = "application/json")
    @ApiOperation(value = "This API will return the requested number of latest videos from the requested YouTube channel ID", produces = "application/json", response = WebServiceCommonResponse.class)
    public ResponseEntity<WebServiceCommonResponse> getLatestYouTubeVideoList(@RequestHeader("channel_id") String channelID, @RequestHeader(value = "count", required = false, defaultValue = "3") int count) {
        try {
            List<YouTubeCardResponse> a = service.getYouTubeVideoInfo(channelID, count);
            return new WebServiceCommonResponse(a).response();
        } catch (WebServiceException wex) {
            return wex.response();
        }
    }

}
