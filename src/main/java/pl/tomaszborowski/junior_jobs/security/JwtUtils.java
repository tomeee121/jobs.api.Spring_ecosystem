package pl.tomaszborowski.junior_jobs.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import pl.tomaszborowski.junior_jobs.security.login.domain.Dao.User;

import java.util.Date;

@Component
@Slf4j
public class JwtUtils {

    private final String jwtSecret;
    private final int expirationTimeSeconds;

    public JwtUtils(@Value("${offers.jwt.secret}") String jwtSecret,@Value("${offers.expiration.time.seconds}") int expirationTimeSeconds) {
        this.jwtSecret = jwtSecret;
        this.expirationTimeSeconds = expirationTimeSeconds;
    }

    public String generateJwt(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwt = Jwts.builder().setSubject(userDetails.getUsername()).setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime() + expirationTimeSeconds))
                .signWith(SignatureAlgorithm.HS256, jwtSecret).compact();
        return jwt;
    }

    public boolean verifyToken(String token){
        try{
            Date expiration = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getExpiration();
            if (expiration.after(new Date())) {
                return true;
            }
        }
        catch (ExpiredJwtException exception){
            log.error("JWT token is expired: {}", exception.getMessage());
        }
        catch(UnsupportedJwtException exception){
            log.error("JWT token is unsupported: {}", exception.getMessage());
        }
        catch(MalformedJwtException exception){
            log.error("Invalid JWT token: {}", exception.getMessage());
        }
        catch(SignatureException exception){
            log.error("Invalid JWT signature: {}", exception.getMessage());
        }
        catch(IllegalArgumentException exception){
            log.error("JWT claims string is empty: {}", exception.getMessage());
        }
        return true;
    }

    public String getUsernameFromToken(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }
}
