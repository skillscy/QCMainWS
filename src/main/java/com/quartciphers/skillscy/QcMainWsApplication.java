package com.quartciphers.skillscy;

import com.qc.skillscy.commons.misc.QcSwagger;
import com.qc.skillscy.commons.misc.SwaggerAppInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@EnableSwagger2
@EnableEurekaClient
public class QcMainWsApplication {

    @Value("${resttemplate.timeout}")
    private int restTemplateTimeout;

    public static void main(String[] args) {
        SpringApplication.run(QcMainWsApplication.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpComponentsClientHttpRequestFactory.setConnectTimeout(restTemplateTimeout); // setting 3s timeout
        return new RestTemplate(httpComponentsClientHttpRequestFactory);
    }

    @Bean
    public Docket swaggerConfiguration() {
        // Return a prepared docket instance
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.quartciphers.skillscy"))
                .build()
                .apiInfo(apiDetails());
    }

    private ApiInfo apiDetails() {
        final String SERVICE_NAME = SwaggerAppInfo.MainWS.getAppName();
        final String DESCRIPTION = SwaggerAppInfo.MainWS.getDescription();
        final String VERSION = "1.0";
        final String TERMS = QcSwagger.TERMS;
        final Contact CONTACT = new Contact(QcSwagger.COMPANY, QcSwagger.WEBSITE, QcSwagger.EMAIL);
        final String LICENSE = QcSwagger.LICENSE;
        final String LICENSE_URL = QcSwagger.LICENSE_URL;
        return new ApiInfo(SERVICE_NAME, DESCRIPTION, VERSION, TERMS, CONTACT, LICENSE, LICENSE_URL, Collections.emptyList());
    }

}
