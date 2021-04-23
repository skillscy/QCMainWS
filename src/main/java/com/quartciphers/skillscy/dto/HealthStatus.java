package com.quartciphers.skillscy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class HealthStatus {

    private String applicationURL;
    private String status;
    private Map<String, String> url;
    private String productOwner;

    public HealthStatus(String uri, String companyName) {
        this.applicationURL = uri;
        this.status = "UP";
        this.productOwner = companyName;

        // List of URLs
        this.url = new HashMap<>();
        this.url.put("SwaggerURL", uri.concat("/swagger-ui.html"));
        this.url.put("HystrixDashboardURL", uri.concat("/hystrix"));
        this.url.put("SingleHystrixAppURL", uri.concat("/actuator/hystrix.stream"));
    }

    @JsonProperty("ApplicationURL")
    public String getApplicationURL() {
        return applicationURL;
    }

    @JsonProperty("Status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("URL")
    public Map<String, String> getURL() {
        return url;
    }

    @JsonProperty("ProductOwner")
    public String getProductOwner() {
        return productOwner;
    }
}
