package pl.tomaszborowski.junior_jobs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pl.tomaszborowski.junior_jobs.infrastructure.RemoteOfferClient;
import pl.tomaszborowski.junior_jobs.infrastructure.error.RestTemplateResponseErrorHandler;

import java.time.Duration;

@Configuration
public class ConfigHttpOfferBeansForTests extends Config{

    public RemoteOfferClient getOfferHttpClient(int connectionTimeout, int readTimeout, String uri){
        RestTemplate restTemplate = restTemplate(connectionTimeout, readTimeout, restTemplateResponseErrorHandler());
        RemoteOfferClient remoteOfferClient = offerHttpClient(restTemplate, uri);
        return remoteOfferClient;
    }

    public RestTemplate restTemplate(int connectTimeout,
                                     int readTimeout,
                                     RestTemplateResponseErrorHandler httpErrorHandler){
        return new RestTemplateBuilder().errorHandler(httpErrorHandler).setConnectTimeout(Duration.ofMillis(connectTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout)).build();
    }

    public RestTemplateResponseErrorHandler restTemplateResponseErrorHandler(){
        return new RestTemplateResponseErrorHandler();
    }

}