package pl.tomaszborowski.junior_jobs.infrastructure.offer.client;

import pl.tomaszborowski.junior_jobs.infrastructure.error.RestTemplateResponseErrorHandler;

public interface SampleRestTemplateException {
    default RestTemplateResponseErrorHandler getSampleRestTemplateException(){
        return new RestTemplateResponseErrorHandler();
    }
}
