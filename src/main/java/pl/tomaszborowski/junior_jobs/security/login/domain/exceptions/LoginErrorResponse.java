package pl.tomaszborowski.junior_jobs.security.login.domain.exceptions;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class LoginErrorResponse {

    private final String message;
    private final HttpStatus status;
}
