package com.project.plantOne.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.project.plantOne.config.MyUserDetail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import static com.project.plantOne.constants.Constants.*;

@Component
public class JwtUtil {

    private String secretKey;
    private long expirationTime;

    @Value("${jwt.secret}")
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    @Value("${jwt.expirationTime}")
    public void setExpirationTime(String expirationTime) {

        this.expirationTime = Long.parseLong(expirationTime) * JWT_EXPIRATION_TIME * JWT_EXPIRATION_TIME_FACTOR;

    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public String getUsernameFromToken(String token) {
        DecodedJWT decodedJWT = getDecodedJWT(token);
        return decodedJWT.getSubject();
    }

    public Claim getClaimFromToken(String token,String claimName) {
        DecodedJWT decodedJWT = getDecodedJWT(token);
        return decodedJWT.getClaim(claimName);
    }

    private Map<String, Claim> getAllClaimsFromToken(String token) {
        DecodedJWT decodedJWT = getDecodedJWT(token);
        return decodedJWT.getClaims();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
        DecodedJWT decodedJWT = getDecodedJWT(token);
        return decodedJWT.getExpiresAt();
    }

    public String generateToken(Authentication auth) {
        MyUserDetail myUserDetail = (MyUserDetail) auth.getPrincipal();
        String token = JWT.create()
                .withSubject(myUserDetail.getUsername())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + getExpirationTime()))
                .withClaim("roles", myUserDetail.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(Algorithm.HMAC256(getSecretKey().getBytes()));
        return token;
    }

    public DecodedJWT getDecodedJWT(String token){
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(getSecretKey().getBytes()))
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""));
        return decodedJWT;
    }
}
