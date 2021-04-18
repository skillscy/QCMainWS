package com.quartciphers.skillscy.service;

import com.quartciphers.skillscy.dto.YouTubeAPI.ItemInfo;
import com.quartciphers.skillscy.dto.YouTubeAPI.YouTubeAPIResponse;
import com.quartciphers.skillscy.dto.YouTubeCardResponse;
import com.quartciphers.skillscy.vo.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
public class QCMainWSServiceImpl implements QCMainWSService {

    @Value("${youtube.api.url}")
    private String youTubeApiURL;

    @Value("${youtube.api.key}")
    private String youTubeApiKey;

    /* GET '/youtube' */
    @Override
    public List<YouTubeCardResponse> getYouTubeVideoInfo(String channelID, int count) throws WebServiceException {

        // Connecting to YouTube API
        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(youTubeApiURL)
                .queryParam(ApplicationConstants.QPARAM_API_KEY, youTubeApiKey)
                .queryParam(ApplicationConstants.QPARAM_CHANNEL_ID, channelID)
                .queryParam(ApplicationConstants.QPARAM_MAX_RESULTS, count);

        ResponseEntity<YouTubeAPIResponse> youTubeResponse = new RestTemplate().getForEntity(uri.toUriString(), YouTubeAPIResponse.class);

        // Validating and formatting YouTube response
        List<YouTubeCardResponse> youTubeCardResponse = new ArrayList<>();
        if (youTubeResponse.getBody() != null) {
            if (youTubeResponse.getBody().getVideoList().isEmpty())
                throw new WebServiceException(ApplicationCodes.INVALID_OR_NO_VIDEO_CHANNEL, HTTPCodes.PRECONDITION_FAILED, WebExceptionType.VALIDATION);

            for (ItemInfo itemInfo: youTubeResponse.getBody().getVideoList()) {
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
}
