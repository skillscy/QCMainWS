package com.quartciphers.skillscy.service;

import com.quartciphers.skillscy.dto.MailContent;
import com.quartciphers.skillscy.dto.SendInBlueAPI.ContactInfo;
import com.quartciphers.skillscy.dto.SendInBlueAPI.ContentBody;
import com.quartciphers.skillscy.dto.SendInBlueAPI.SendInBlueAPIResponse;
import com.quartciphers.skillscy.dto.YouTubeAPI.ItemInfo;
import com.quartciphers.skillscy.dto.YouTubeAPI.YouTubeAPIResponse;
import com.quartciphers.skillscy.dto.YouTubeCardResponse;
import com.quartciphers.skillscy.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class QCMainWSServiceV1Impl implements QCMainWSServiceV1 {

    @Value("${youtube.api.url}")
    private String youTubeApiURL;

    @Value("${youtube.api.key}")
    private String youTubeApiKey;

    @Value("${sendinblue.smtp.url}")
    private String sendInBlueApiURL;

    @Value("${sendinblue.api.key}")
    private String sendInBlueApiKey;

    @Value("${company.name}")
    private String companyName;

    @Value("${mail.body}")
    private String mailBody;

    @Autowired
    private RestTemplate restTemplate;

    /* GET '/youtube' */
    @Override
    public List<YouTubeCardResponse> getYouTubeVideoInfo(String channelID, int count) throws Exception {

        // Connecting to YouTube API
        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(youTubeApiURL)
                .queryParam(ApplicationConstants.QPARAM_API_KEY, youTubeApiKey)
                .queryParam(ApplicationConstants.QPARAM_CHANNEL_ID, channelID)
                .queryParam(ApplicationConstants.QPARAM_MAX_RESULTS, count);

        ResponseEntity<YouTubeAPIResponse> youTubeResponse = restTemplate.getForEntity(uri.toUriString(), YouTubeAPIResponse.class);

        // Validating and formatting YouTube response
        List<YouTubeCardResponse> youTubeCardResponse = new ArrayList<>();
        if (youTubeResponse.getBody() != null) {
            if (youTubeResponse.getBody().getVideoList().isEmpty())
                throw new WebServiceException(ApplicationCodes.INVALID_OR_NO_VIDEO_CHANNEL, HTTPCodes.PRECONDITION_FAILED, WebExceptionType.VALIDATION);

            for (ItemInfo itemInfo : youTubeResponse.getBody().getVideoList()) {
                YouTubeCardResponse cardResponse = itemInfo.getSnippetInfo();
                cardResponse.setVideoURL(itemInfo.getVideoID());
                youTubeCardResponse.add(cardResponse);
            }
        } else {
            throw new WebServiceException(ApplicationCodes.NO_BODY_FOUND, HTTPCodes.NOT_FOUND, WebExceptionType.SERVICE_CALL);
        }

        // Returning the formatted response
        return youTubeCardResponse;
    }

    /* POST '/content-mail' */
    @Override
    public void sendMessageToClient(MailContent mailContent) throws Exception {

        // Formatting Content
        ContentBody contentBody = new ContentBody();

        ContactInfo sender = new ContactInfo();
        sender.setName(companyName);
        sender.setEMailID("skillscy.team@gmail.com");

        List<ContactInfo> receiverList = new ArrayList<>();
        ContactInfo receiver = new ContactInfo();
        receiver.setName(mailContent.getClientInfo().getName());
        receiver.setEMailID(mailContent.getClientInfo().getMailAddress());
        receiverList.add(receiver);

        contentBody.setSender(sender);
        contentBody.setReceivers(receiverList);
        contentBody.setSubject(mailContent.getClientInfo().getMailSubject());
        contentBody.setContentInHTML(MessageFormat.format(mailBody, mailContent.getName(), mailContent.getPhoneNumber(), mailContent.geteMailID(), mailContent.getMessage()));

        // Connecting to Send in blue API
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(ApplicationConstants.HEADER_API_KEY, sendInBlueApiKey);

        HttpEntity<ContentBody> httpEntity = new HttpEntity<>(contentBody, headers);

        ResponseEntity<SendInBlueAPIResponse> sendInBlueResponse = restTemplate.postForEntity(sendInBlueApiURL, httpEntity, SendInBlueAPIResponse.class);
        // TODO: Log the message ID from SendInBlue response body
    }
}
