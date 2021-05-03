package com.quartciphers.skillscy.controller;

import com.qc.skillscy.commons.dto.StatusIndicator;
import com.qc.skillscy.commons.loggers.CommonLogger;
import com.qc.skillscy.commons.misc.Validator;
import com.quartciphers.skillscy.dto.MailContent;
import com.quartciphers.skillscy.dto.YouTubeCardResponse;
import com.quartciphers.skillscy.dto.YouTubeVideoResponse;
import com.quartciphers.skillscy.service.v1.QCMainWSServiceV1;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class MainWSControllerV1 {

    @Autowired
    private QCMainWSServiceV1 service;

    @GetMapping(value = "/youtube", produces = "application/json")
    @ApiOperation(value = "This API will return the requested number of latest videos from the requested YouTube channel ID", produces = "application/json", response = YouTubeVideoResponse.class)
    public ResponseEntity<YouTubeVideoResponse> getLatestYouTubeVideoList(@RequestHeader("channel_id") String channelID, @RequestHeader(value = "video_count", required = false, defaultValue = "3") int count) throws Exception {

        CommonLogger.info(this.getClass(), "---------- API 'getLatestYouTubeVideoList' STARTED ----------");

        // Connecting to service layer
        CommonLogger.info(this.getClass(), "Connecting to service.getYouTubeVideoInfo...");
        List<YouTubeCardResponse> videosList = service.getYouTubeVideoInfo(channelID, count);

        // Success Response
        CommonLogger.info(this.getClass(), "Wrapping up the response");
        YouTubeVideoResponse apiResponse = new YouTubeVideoResponse(videosList);
        apiResponse.completed();
        CommonLogger.info(this.getClass(), "---------- API 'getLatestYouTubeVideoList' COMPLETED ----------");
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping(value = "/contact-mail", produces = "application/json")
    @ApiOperation(value = "This API will send a mail to the client E-Mail ID", produces = "application/json", response = StatusIndicator.class)
    public ResponseEntity<StatusIndicator> sendMailToClient(@RequestBody MailContent mailContent) throws Exception {

        CommonLogger.info(this.getClass(), "---------- API 'sendMailToClient' STARTED ----------");

        // Validation
        CommonLogger.info(this.getClass(), "Validating body parameters");
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
        CommonLogger.info(this.getClass(), "Connecting to service.sendMessageToClient...");
        service.sendMessageToClient(mailContent);

        // Success Response
        CommonLogger.info(this.getClass(), "Wrapping up the response");
        StatusIndicator apiResponse = new StatusIndicator();
        apiResponse.completed();
        CommonLogger.info(this.getClass(), "---------- API 'sendMailToClient' COMPLETED ----------");
        return ResponseEntity.ok(apiResponse);
    }

}
