package com.quartciphers.skillscy.service.v1;

import com.quartciphers.skillscy.dto.MailContent;
import com.quartciphers.skillscy.dto.YouTubeCardResponse;

import java.util.List;

public interface QCMainWSServiceV1 {

    List<YouTubeCardResponse> getYouTubeVideoInfo(final String channelID, final int count) throws Exception;

    void sendMessageToClient(final MailContent mailContent) throws Exception;

    void writeDB(String value) throws Exception;

}
