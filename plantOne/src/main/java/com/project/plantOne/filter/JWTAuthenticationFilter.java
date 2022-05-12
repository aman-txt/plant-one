package com.project.plantOne.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.plantOne.config.MyUserDetail;
import com.project.plantOne.jwt.JwtRequest;
import com.project.plantOne.jwt.JwtResponse;
import com.project.plantOne.jwt.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private JwtUtil jwtUtil;

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {

            JwtRequest jwtRequest = new ObjectMapper().readValue(req.getInputStream(), JwtRequest.class);

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword(),
                            new ArrayList<>()));
            return authentication;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {
        try{
            String token = jwtUtil.generateToken(auth);
            MyUserDetail myUserDetail = (MyUserDetail) auth.getPrincipal();
            JwtResponse jwtResponse = new JwtResponse(myUserDetail.getUser_id().toString(), myUserDetail.getUsername(),token);
            res.getWriter().write(jwtResponse.toString());
            res.getWriter().flush();
        }catch(Exception e){
            throw new RuntimeException(e);
        }

    }

    public void setAuthenticateService(JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }
}
