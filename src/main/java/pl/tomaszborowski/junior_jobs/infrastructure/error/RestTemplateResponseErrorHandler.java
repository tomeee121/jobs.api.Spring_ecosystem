package pl.tomaszborowski.junior_jobs.infrastructure.error;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

public class RestTemplateResponseErrorHandler extends DefaultResponseErrorHandler {
    @Override
    public void handleError(ClientHttpResponse response, HttpStatus statusCode) throws IOException {
        HttpStatus status = response.getStatusCode();
        HttpStatus.Series series = status.series();
        switch (series) {
            case CLIENT_ERROR:
                if (response.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
                } else if (response.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }

            case SERVER_ERROR:
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            default:
                throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }
}
