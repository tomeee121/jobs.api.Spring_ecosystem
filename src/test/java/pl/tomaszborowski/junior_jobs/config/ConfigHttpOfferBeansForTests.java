package pl.tomaszborowski.junior_jobs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pl.tomaszborowski.junior_jobs.infrastructure.RemoteOfferClient;
import pl.tomaszborowski.junior_jobs.infrastructure.error.RestTemplateResponseErrorHandler;
import pl.tomaszborowski.junior_jobs.infrastructure.offer.client.OfferHttpClient;

import java.time.Duration;

public class ConfigHttpOfferBeansForTests extends Config{

    public RemoteOfferClient getOfferHttpClient(int connectionTimeout, int readTimeout,String uri) {
        final RestTemplate restTemplate = restTemplate(connectionTimeout, readTimeout, restTemplateResponseErrorHandler());
        return new OfferHttpClient(restTemplate, uri);
    }

}