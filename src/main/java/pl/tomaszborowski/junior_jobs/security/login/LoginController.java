package pl.tomaszborowski.junior_jobs.security.login;

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
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;


    public LoginController(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDto loginRequestDto){
        System.out.println("kontroler login");

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUserName(),
                loginRequestDto.getPassword()));
        if(authentication.isAuthenticated()){
            System.out.println("autenticated");

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new ResponseEntity<>(jwtUtils.generateJwt(authentication), HttpStatus.ACCEPTED);
        }

        System.out.println("not autenticated");
        return new ResponseEntity<String>("Bad credentials", HttpStatus.UNAUTHORIZED);

    }
}
