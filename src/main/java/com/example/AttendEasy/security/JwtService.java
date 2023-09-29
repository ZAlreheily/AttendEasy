package com.example.AttendEasy.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {
    public static String secret= "QIjGTLuMZXzNrVJwWm6b98XTOxKvTPbmxO4VsdwQGAA=";

    public String extractMobile(String token) {
        return extractClaim( token, Claims::getSubject );
    }

    public static String extractMobileWithOutBearer(String token) {
        token = token.substring( 7 );
        return extractClaim( token, Claims::getSubject );
    }

    public Date extractExpiration(String token) {
        return extractClaim( token, Claims::getExpiration );
    }

    public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims( token );
        return claimsResolver.apply( claims );
    }

    public static String extractClaim(String token, String claimName) {
        token = token.substring( 7 );
        return Jwts.parser()
                .setSigningKey( secret )
                .parseClaimsJws( token )
                .getBody()
                .get( claimName, String.class );
    }

    public static Claims extractAllClaims(String token) {
        try {
            Jws<Claims> claimsJws = Jwts
                    .parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token);

            return claimsJws.getBody();
        } catch (ExpiredJwtException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Token has expired", e);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing JWT token", e);
        }
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration( token ).before( new Date() );
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String mobile = extractMobile(token);
        return (mobile.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String generateToken(String mobileNumber) {
        Map<String, Object> claimsAdded = new HashMap<>();

        return createToken( claimsAdded, mobileNumber);

    }

    private String createToken(Map<String, Object> claimsAdded, String mobileNumber) {
        return Jwts.builder()
                .setClaims(claimsAdded)
                .setSubject(mobileNumber)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private static Key getSignKey() {
        byte[] keyByte= Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyByte);
    }
}
