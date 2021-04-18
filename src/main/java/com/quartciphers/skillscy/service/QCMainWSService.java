package com.quartciphers.skillscy.service;

import com.quartciphers.skillscy.dto.YouTubeCardResponse;
import com.quartciphers.skillscy.vo.WebServiceException;

import java.util.List;

public interface QCMainWSService {

    List<YouTubeCardResponse> getYouTubeVideoInfo(final String channelID, final int count) throws WebServiceException;

}
