package pl.tomaszborowski.junior_jobs.security.login.domain.Dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@Getter
public class LoginRequestDto {

    @NotNull(message = "{username.not.null}")
    @NotEmpty(message = "{username.not.empty}")
    @NotBlank(message = "{username.not.blank}")
    private final String userName;

    @NotNull(message = "{password.not.null}")
    @NotEmpty(message = "{password.not.empty}")
    @NotBlank(message = "{password.not.blank}")
    private final String password;
}
