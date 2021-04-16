package com.quartciphers.skillscy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
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
public class QcMainWsApplication {

	public static void main(String[] args) {
		SpringApplication.run(QcMainWsApplication.class, args);
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
		final String SERVICE_NAME = "QCMainWS";
		final String DESCRIPTION = "This webservice exposes several APIs which can be utilized by the Quart Ciphers company.";
		final String VERSION = "1.0";
		final String TERMS = "All Rights Reserved (c) 2021 - Quart Ciphers";
		final Contact CONTACT = new Contact("Quart Ciphers", "quartciphers.in", "ping@quartciphers.in");
		final String LICENSE = "The Apache Software License, Version 2.0";
		final String LICENSE_URL = "http://www.apache.org/licenses/LICENSE-2.0.txt";
		return new ApiInfo(SERVICE_NAME, DESCRIPTION, VERSION, TERMS, CONTACT, LICENSE, LICENSE_URL, Collections.emptyList());
	}

}
