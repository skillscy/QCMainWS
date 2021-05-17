package com.quartciphers.skillscy.service.v1;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import com.qc.skillscy.commons.codes.ApplicationCodes;
import com.qc.skillscy.commons.codes.HTTPCodes;
import com.qc.skillscy.commons.dto.LookupDocument;
import com.qc.skillscy.commons.exceptions.WebExceptionType;
import com.qc.skillscy.commons.exceptions.WebServiceException;
import com.qc.skillscy.commons.loggers.CommonLogger;
import com.qc.skillscy.commons.misc.*;
import com.quartciphers.skillscy.dto.MailContent;
import com.quartciphers.skillscy.dto.SendInBlueAPI.ContactInfo;
import com.quartciphers.skillscy.dto.SendInBlueAPI.ContentBody;
import com.quartciphers.skillscy.dto.SendInBlueAPI.SendInBlueAPIResponse;
import com.quartciphers.skillscy.dto.YouTubeAPI.ItemInfo;
import com.quartciphers.skillscy.dto.YouTubeAPI.YouTubeAPIResponse;
import com.quartciphers.skillscy.dto.YouTubeCardResponse;
import com.quartciphers.skillscy.dto.repository.FeedbackDB;
import com.quartciphers.skillscy.vo.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.MessageFormat;
import java.util.*;

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

    @Value("${firestore.database.url}")
    private String databaseURL;

    @Value("${firestore.database.credentials}")
    private String databaseSecret;

    @Autowired
    private RestTemplate restTemplate;

    /* GET '/youtube' */
    @Override
    @Cacheable(value = "YouTubeResponse", key = "'YouTubeResponse'+#channelID")
    public List<YouTubeCardResponse> getYouTubeVideoInfo(String channelID, int count) throws Exception {

        // Connecting to YouTube API
        CommonLogger.info(this.getClass(), "Building the URI for calling youTube API...");
        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(youTubeApiURL)
                .queryParam(ApplicationConstants.QPARAM_API_KEY, youTubeApiKey)
                .queryParam(ApplicationConstants.QPARAM_CHANNEL_ID, channelID)
                .queryParam(ApplicationConstants.QPARAM_MAX_RESULTS, count);

        CommonLogger.info(this.getClass(), "Calling YouTube API...");
        ResponseEntity<YouTubeAPIResponse> youTubeResponse = restTemplate.getForEntity(uri.toUriString(), YouTubeAPIResponse.class);
        CommonLogger.info(this.getClass(), "YouTube API response received successfully");

        // Validating and formatting YouTube response
        List<YouTubeCardResponse> youTubeCardResponse = new ArrayList<>();
        if (Validator.isNotNull(youTubeResponse.getBody())) {
            CommonLogger.info(this.getClass(), "Body found in API response");

            if (Validator.isNotNull(youTubeResponse.getBody().getVideoList())) {
                CommonLogger.warning(this.getClass(), "Video list object found in API response");

                if (youTubeResponse.getBody().getVideoList().isEmpty()) {
                    CommonLogger.warning(this.getClass(), "Empty video list found in API response");

                    throw new WebServiceException(ApplicationCodes.INVALID_OR_NO_VIDEO_CHANNEL, HTTPCodes.PRECONDITION_FAILED, WebExceptionType.VALIDATION);
                }
            } else {
                CommonLogger.warning(this.getClass(), "Video list object not found in API response");

                throw new WebServiceException(ApplicationCodes.INVALID_OR_NO_VIDEO_CHANNEL, HTTPCodes.PRECONDITION_FAILED, WebExceptionType.VALIDATION);
            }

            CommonLogger.info(this.getClass(), "Iterating over video list from API response...");
            for (ItemInfo itemInfo : youTubeResponse.getBody().getVideoList()) {
                YouTubeCardResponse cardResponse = itemInfo.getSnippetInfo();
                cardResponse.setVideoPlayURL(itemInfo.getVideoID());
                youTubeCardResponse.add(cardResponse);
            }
            CommonLogger.info(this.getClass(), "Iteration completed! All videos received in the dto object");
        } else {
            CommonLogger.warning(this.getClass(), "Body not found in API response");
            throw new WebServiceException(ApplicationCodes.NO_BODY_FOUND, HTTPCodes.NOT_FOUND, WebExceptionType.SERVICE_CALL);
        }

        // Returning the formatted response
        CommonLogger.info(this.getClass(), "Responding dto object to the controller");
        return youTubeCardResponse;
    }

    /* POST '/content-mail' */
    @Override
    public void sendMessageToClient(MailContent mailContent) throws Exception {

        // Formatting Content
        CommonLogger.info(this.getClass(), "Preparing mail content body");
        ContentBody contentBody = new ContentBody();

        CommonLogger.info(this.getClass(), "Setting sender info...");
        ContactInfo sender = new ContactInfo();
        sender.setName(QcSwagger.COMPANY);
        sender.setEMailID("skillscy.team@gmail.com");

        CommonLogger.info(this.getClass(), "Setting receiver info...");
        List<ContactInfo> receiverList = new ArrayList<>();
        ContactInfo receiver = new ContactInfo();
        receiver.setName(mailContent.getClientInfo().getName());
        receiver.setEMailID(mailContent.getClientInfo().getMailAddress());
        receiverList.add(receiver);

        CommonLogger.info(this.getClass(), "Setting content body...");
        contentBody.setSender(sender);
        contentBody.setReceivers(receiverList);
        contentBody.setSubject(mailContent.getClientInfo().getMailSubject());
        contentBody.setContentInHTML(MessageFormat.format(ApplicationConstants.MAIL_BODY, mailContent.getName(), mailContent.getPhoneNumber(), mailContent.geteMailID(), mailContent.getMessage()));

        // Connecting to Send in blue API
        CommonLogger.info(this.getClass(), "Setting headers for the Sendinblue API call...");
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(ApplicationConstants.HEADER_API_KEY, sendInBlueApiKey);

        HttpEntity<ContentBody> httpEntity = new HttpEntity<>(contentBody, headers);

        CommonLogger.info(this.getClass(), "Calling Sendinblue API...");
        ResponseEntity<SendInBlueAPIResponse> sendInBlueResponse = restTemplate.postForEntity(sendInBlueApiURL, httpEntity, SendInBlueAPIResponse.class);
        CommonLogger.info(this.getClass(), "Sendinblue API response received successfully");

        if (Validator.isNotNull(sendInBlueResponse.getBody())) {
            CommonLogger.info(this.getClass(), "Received Message ID = [".concat(sendInBlueResponse.getBody().getMessageID()).concat("]"));
        } else {
            CommonLogger.warning(this.getClass(), "Body not found in API response");
            throw new WebServiceException(ApplicationCodes.NO_BODY_FOUND, HTTPCodes.NOT_FOUND, WebExceptionType.SERVICE_CALL);
        }
    }

    @Override
    public void writeDB(String senderID, String receivedID, String feedback) throws Exception {

        Firestore db = FirebaseServer.start().initialize(databaseURL, databaseSecret);

        String currentID = FirebaseDB.connect().getLatestIDFromLookupDocument(db, "comments");
        String newID = QcUtils.generateNextCommentsID(currentID);

        FeedbackDB data = new FeedbackDB();
        data.setSenderID(senderID);
        data.setReceiverID(receivedID);
        data.setFeedback(feedback);
        data.setTimeOfCreation(Timestamp.now());

        ApiFuture<WriteResult> result = db.collection("comments").document(newID).set(data.dbValue());
        CommonLogger.info(QCMainWSServiceV1Impl.class, "Update time : " + result.get().getUpdateTime());

        FirebaseDB.connect().updateLatestIDToLookupDocument(db, "comments", newID);
    }

}
