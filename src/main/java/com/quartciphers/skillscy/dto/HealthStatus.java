package com.quartciphers.skillscy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HealthStatus {

    private String applicationURL;
    private String status;
    private String swaggerURL;
    private String productOwner;

    public HealthStatus(String uri) {
        this.applicationURL = uri;
        this.status = "UP";
        this.swaggerURL = uri.concat("/swagger-ui.html");
        this.productOwner = "Quart Ciphers";
    }

    @JsonProperty("ApplicationURL")
    public String getApplicationURL() {
        return applicationURL;
    }

    @JsonProperty("Status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("SwaggerURL")
    public String getSwaggerURL() {
        return swaggerURL;
    }

    @JsonProperty("ProductOwner")
    public String getProductOwner() {
        return productOwner;
    }
}
