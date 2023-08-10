package am.github.springbootblogapi.security;

import am.github.springbootblogapi.config.AppConstants;
import am.github.springbootblogapi.exceptions.ApiException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    private Key getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String generateToken(Authentication authentication) {
        String username = authentication.getName(); // in our case: username or email
        Date issuedAt = new Date();
        Date expirationDate = new Date(issuedAt.getTime() + AppConstants.JWT_EXPIRATION_MILLISECONDS);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(issuedAt)
                .setExpiration(expirationDate)
                .signWith(getKey())
                .compact();
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parse(token);

            return true;
        } catch (MalformedJwtException exception) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Token invalid!");
        } catch (ExpiredJwtException exception) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Token expired!");
        } catch (UnsupportedJwtException exception) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Unsupported token!");
        } catch (IllegalArgumentException exception) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "JWT claims string is empty!");
        } catch (Exception exception) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid token!");
        }
    }
}
