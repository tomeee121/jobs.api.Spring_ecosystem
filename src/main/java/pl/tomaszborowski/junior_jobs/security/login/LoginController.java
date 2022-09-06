package pl.tomaszborowski.junior_jobs.security.login;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.tomaszborowski.junior_jobs.security.login.Dto.LoginRequestDto;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class LoginController {

    @PostMapping
    public ResponseEntity<> login(@Valid @RequestBody LoginRequestDto loginRequestDto){

    }
}
