package com.springCloud.orderService.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;

@Configuration
@Data
@ConfigurationProperties(prefix = "microservice")
@RefreshScope
public class CloudConfig {

    @Value("${microservice.payment-service.endpoints.endpoint.uri}")
    private String PAYMENT_ENDPOINT_URL;

    @Value("${microservice.order-service.endpoints.endpoint.uri}")
    private String ORDER_ENDPOINT_URL;

}
