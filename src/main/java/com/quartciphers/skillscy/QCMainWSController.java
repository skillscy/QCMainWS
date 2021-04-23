package com.quartciphers.skillscy;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.quartciphers.skillscy.dto.*;
import com.quartciphers.skillscy.service.QCMainWSServiceV1;
import com.quartciphers.skillscy.vo.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class QCMainWSController {

    @Value("${company.name}")
    private String companyName;

    @GetMapping(value = "/hello", produces = "application/json")
    @ApiOperation(value = "A hello method which check health", nickname = "Hello method", notes = "This method returns the running status of the application along with the application URL, Swagger URL and other company related information", produces = "application/json", response = HealthStatus.class)
    public ResponseEntity<HealthStatus> helloToHealth() {
        HealthStatus healthStatus = new HealthStatus(ServletUriComponentsBuilder.fromCurrentContextPath().toUriString(), companyName); // creates HealthStatus object with necessary parameters
        return ResponseEntity.ok(healthStatus); // returns the response to the consumer
    }

}

@RestController
@RequestMapping("/api/v1")
class QCMainWSControllerVersion1 {

    @Autowired
    private QCMainWSServiceV1 service;

    @GetMapping(value = "/youtube", produces = "application/json")
    @ApiOperation(value = "This API will return the requested number of latest videos from the requested YouTube channel ID", produces = "application/json", response = YouTubeCardResponse.class)
    @HystrixCommand(
            fallbackMethod = "getFallbackLatestYouTubeVideoList",
            threadPoolKey = "sendMailToClientPool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "20"),
                    @HystrixProperty(name = "maxQueueSize", value = "10")
            }
    )
    public ResponseEntity<WebServiceCommonResponse> getLatestYouTubeVideoList(@RequestHeader("channel_id") String channelID, @RequestHeader(value = "video_count", required = false, defaultValue = "3") int count) {
        try {
            List<YouTubeCardResponse> videosList = service.getYouTubeVideoInfo(channelID, count);
            return new WebServiceCommonResponse(new YouTubeVideoResponse(videosList)).response();
        } catch (WebServiceException wex) {
            return wex.response();
        } catch (Exception ex) {
            return new WebServiceException(ApplicationCodes.INTERNAL_SERVER_ERROR, HTTPCodes.INTERNAL_ERROR).response();
        }
    }

    @PostMapping(value = "/contact-mail", produces = "application/json")
    @ApiOperation(value = "This API will send a mail to the client E-Mail ID", produces = "application/json", response = WebServiceCommonResponse.class)
    @HystrixCommand(
            fallbackMethod = "getFallbackMailToClient",
            threadPoolKey = "sendMailToClientPool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "20"),
                    @HystrixProperty(name = "maxQueueSize", value = "10")
            }
    )
    public ResponseEntity<WebServiceCommonResponse> sendMailToClient(@RequestBody MailContent mailContent) {
        try {
            // Validation
            Validator.notNull(mailContent);
            Validator.notNull(mailContent.getClientInfo());
            Validator.notNull(mailContent.getClientInfo().getName());
            Validator.notNull(mailContent.getClientInfo().getMailAddress());
            Validator.notNull(mailContent.getClientInfo().getMailSubject());
            Validator.notNull(mailContent.getName());
            Validator.notNull(mailContent.geteMailID());
            Validator.notNull(mailContent.getPhoneNumber());
            Validator.notNull(mailContent.getMessage());

            // Connecting to service layer
            service.sendMessageToClient(mailContent);
            return new WebServiceCommonResponse().response();
        } catch (WebServiceException wex) {
            return wex.response();
        } catch (Exception ex) {
            return new WebServiceException(ApplicationCodes.INTERNAL_SERVER_ERROR, HTTPCodes.INTERNAL_ERROR).response();
        }
    }

    public ResponseEntity<WebServiceCommonResponse> getFallbackLatestYouTubeVideoList(@RequestHeader("channel_id") String channelID, @RequestHeader(value = "video_count", required = false, defaultValue = "3") int count) {
        return new WebServiceException(ApplicationCodes.FALLBACK_DETECTED, HTTPCodes.CIRCUIT_BROKEN, WebExceptionType.CIRCUIT_BROKEN).response();
    }

    public ResponseEntity<WebServiceCommonResponse> getFallbackMailToClient(@RequestBody MailContent mailContent) {
        return new WebServiceException(ApplicationCodes.FALLBACK_DETECTED, HTTPCodes.CIRCUIT_BROKEN, WebExceptionType.CIRCUIT_BROKEN).response();
    }

}
