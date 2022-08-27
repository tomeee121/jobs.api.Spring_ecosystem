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

@Configuration
public class Config {

    @Bean
    public RestTemplateResponseErrorHandler restTemplateResponseErrorHandler(){
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    public RestTemplate restTemplate(@Value("${offer.http.client.config.connectTimeout}") long connectTimeout,
                              @Value("${offer.http.client.config.readTimeout}") long readTimeout,
                              RestTemplateResponseErrorHandler httpErrorHandler){
        return new RestTemplateBuilder().errorHandler(httpErrorHandler).setConnectTimeout(Duration.ofMillis(connectTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout)).build();
    }

    @Bean
    public RemoteOfferClient offerHttpClient(RestTemplate restTemplate, @Value("${offer.http.client.config.uri:https://example.com}") String uri){
        return new OfferHttpClient(restTemplate, uri);
    }
}
