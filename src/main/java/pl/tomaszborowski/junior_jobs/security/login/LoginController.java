package pl.tomaszborowski.junior_jobs.security.login;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.tomaszborowski.junior_jobs.security.login.domain.Dto.LoginRequestDto;
import pl.tomaszborowski.junior_jobs.security.JwtUtils;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final SecurityContextUpdater securityContextUpdater;


    @PostMapping
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDto loginRequestDto){

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUserName(),
                loginRequestDto.getPassword()));
        securityContextUpdater.setAuthentication(authentication);
        System.out.println("autenticated  ");
        return new ResponseEntity<>(jwtUtils.generateJwt(authentication), HttpStatus.ACCEPTED);

    }
}
