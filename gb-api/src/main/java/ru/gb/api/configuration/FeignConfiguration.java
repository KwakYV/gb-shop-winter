package ru.gb.api.configuration;

import feign.*;
import feign.codec.ErrorDecoder;
import feign.optionals.OptionalDecoder;
import feign.slf4j.Slf4jLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.gb.api.base.BaseGateway;
import ru.gb.api.category.api.CategoryGateway;
import ru.gb.api.manufacturer.api.ManufacturerGateway;
import ru.gb.api.product.api.ProductGateway;

import static feign.FeignException.errorStatus;


@Configuration
@EnableFeignClients
@EnableConfigurationProperties(GbApiProperties.class)
@RequiredArgsConstructor
public class FeignConfiguration {

    private final GbApiProperties gbApiProperties;
    private final ObjectFactory<HttpMessageConverters> messageConverters;
    private String gateWayName;

    @Bean
    public ManufacturerGateway manufacturerGateway() {
        return (ManufacturerGateway) getGateWay("Manufacturer");
    }

    @Bean
    public CategoryGateway categoryGateway() {
        return (CategoryGateway) getGateWay("Category");
    }

    @Bean
    public ProductGateway productGateway() {
        return (ProductGateway) getGateWay("Product");
    }

    private BaseGateway getGateWay(String gateWayName) {
        this.gateWayName = gateWayName;
        Feign.Builder builder = Feign.builder()
                .encoder(new SpringEncoder(this.messageConverters))
                .decoder(new OptionalDecoder(new ResponseEntityDecoder(new SpringDecoder(this.messageConverters))))
                .options(new Request.Options(
                        gbApiProperties.getConnection().getConnectTimeoutMillis(),
                        gbApiProperties.getConnection().getReadTimeoutMillis()
                ))
                .logLevel(Logger.Level.FULL)
                .retryer(new Retryer.Default(
                        gbApiProperties.getConnection().getPeriod(),
                        gbApiProperties.getConnection().getMaxPeriod(),
                        gbApiProperties.getConnection().getMaxAttempts()
                ))
                .errorDecoder(errorDecoder())
                .contract(new SpringMvcContract());
        if ("Manufacturer".equals(gateWayName)) {
            return builder
                    .logger(new Slf4jLogger(ManufacturerGateway.class))
                    .target(ManufacturerGateway.class, gbApiProperties.getEndpoint().getManufacturerUrl());
        }

        if ("Category".equals(gateWayName)) {
            return builder
                    .logger(new Slf4jLogger(CategoryGateway.class))
                    .target(CategoryGateway.class, gbApiProperties.getEndpoint().getCategoryUrl());
        }

        if ("Product".equals(gateWayName)) {
            return builder
                    .logger(new Slf4jLogger(ProductGateway.class))
                    .target(ProductGateway.class, gbApiProperties.getEndpoint().getProductUrl());
        }
        return null;
    }

    private ErrorDecoder errorDecoder() {
        return (methodKey, response) -> {
            FeignException feignException = errorStatus(methodKey, response);
            if (feignException.status() == 500 || feignException.status() == 503) {
                return new RetryableException(
                        response.status(),
                        feignException.getMessage(),
                        response.request().httpMethod(),
                        feignException,
                        null,
                        response.request());
            }
            return feignException;
        };
    }
}
