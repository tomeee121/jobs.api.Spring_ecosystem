package pl.tomaszborowski.junior_jobs.security.login;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import pl.tomaszborowski.junior_jobs.security.JwtUtils;
import pl.tomaszborowski.junior_jobs.security.login.domain.Dto.LoginRequestDto;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LoginControllerTest implements SampleAuthentication{

    @Test
    public void whenLoginControllerInvoked_thenShouldReturnCorrectResponseWithGeneratedJwttokenandstatus() {
        //given
        final String expectedControllerResponseBody = "jwtToken";
        final User principal = new User("admin", "pass", Collections.emptyList());
        final Authentication authentication = sampleAuthentication(principal);

        final AuthenticationManager authenticationManager = Mockito.mock(AuthenticationManager.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        final JwtUtils jwtUtils = mock(JwtUtils.class);
        when(jwtUtils.generateJwt(authentication)).thenReturn("jwtToken");

        SecurityContextUpdater securityContextUpdater = mock(SecurityContextUpdater.class);
        doNothing().when(securityContextUpdater).setAuthentication(isA(Authentication.class));

        final LoginRequestDto loginRequestDto = new LoginRequestDto("admin", "pass");

        final LoginController loginController = new LoginController(authenticationManager, jwtUtils, securityContextUpdater);
        //when
        ResponseEntity<String> actualResponseEntity = loginController.login(loginRequestDto);
        String actualResponseBody = actualResponseEntity.getBody();


        //then
        assertThat(actualResponseBody).isEqualTo(expectedControllerResponseBody);
        assertThat(actualResponseEntity.getStatusCode().is2xxSuccessful());
        verify(securityContextUpdater, times(1)).setAuthentication(authentication);
    }

}