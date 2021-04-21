package com.quartciphers.skillscy.service;

import com.quartciphers.skillscy.dto.MailContent;
import com.quartciphers.skillscy.dto.YouTubeCardResponse;
import com.quartciphers.skillscy.vo.WebServiceException;

import java.util.List;

public interface QCMainWSServiceV1 {

    List<YouTubeCardResponse> getYouTubeVideoInfo(final String channelID, final int count) throws Exception;

    void sendMessageToClient(final MailContent mailContent) throws Exception;

}
