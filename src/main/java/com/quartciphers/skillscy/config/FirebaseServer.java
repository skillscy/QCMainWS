package com.quartciphers.skillscy.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.qc.skillscy.commons.loggers.CommonLogger;
import com.quartciphers.skillscy.vo.ApplicationCodesLocal;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FirebaseServer {

    public void initalize(String url, String credentials) throws Exception {
        FirebaseOptions options = null;
        CommonLogger.info(FirebaseServer.class, "Parsing database secret to InputStream...");
        InputStream credentialsStream = new ByteArrayInputStream(credentials.getBytes(StandardCharsets.UTF_8));
        GoogleCredentials googleCredentials = null;

        CommonLogger.info(FirebaseServer.class, "Parsing GoogleCredentials...");
        try {
            googleCredentials = GoogleCredentials.fromStream(credentialsStream);
        } catch (IOException e) {
            throw new Exception(ApplicationCodesLocal.FIREBASE_GOOGLE_CREDENTIALS_STREAM_ERROR.getAppCodeDescription());
            //throw new WebServiceException(ApplicationCodes.FIREBASE_GOOGLE_CREDENTIALS_STREAM_ERROR, HTTPCodes.INTERNAL_ERROR, WebExceptionType.DATABASE_ERROR);
        }

        CommonLogger.info(FirebaseServer.class, "Building FirebaseOptions...");
        options = FirebaseOptions.builder()
                .setCredentials(googleCredentials)
                .setDatabaseUrl(url)
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            CommonLogger.info(FirebaseServer.class, "Initializing Firestore App...");
            FirebaseApp.initializeApp(options);
        } else {
            CommonLogger.info(FirebaseServer.class, "Using already initialized app...");
        }

    }

}
