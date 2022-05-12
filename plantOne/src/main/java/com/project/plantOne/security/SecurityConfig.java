package com.project.plantOne.security;

import com.project.plantOne.config.UserDetailsServiceImpl;
import com.project.plantOne.filter.JWTAuthenticationFilter;
import com.project.plantOne.filter.JWTAuthorizationFilter;
import com.project.plantOne.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import static com.project.plantOne.constants.Constants.*;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter(authenticationManagerBean());
        jwtAuthenticationFilter.setAuthenticateService(jwtUtil);
        jwtAuthenticationFilter.setFilterProcessesUrl(AUTHENTICATE_USER);

        JWTAuthorizationFilter jwtAuthorizationFilter = new JWTAuthorizationFilter(authenticationManagerBean());
        jwtAuthorizationFilter.setAuthenticateService(jwtUtil);

        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,ADD_USER,RESET_PASSWORD,CHANGE_PASSWORD).permitAll()
                .antMatchers(HttpMethod.GET,AUTHENTICATE_USER,VERIFY_TOKEN,HOME_URL).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(jwtAuthenticationFilter)
                .addFilter(jwtAuthorizationFilter)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private static final String[] AUTH_WHITELIST = {
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger-resources/configuration/ui"
    };

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(AUTH_WHITELIST);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setMaxUploadSize(10000000);
        commonsMultipartResolver.setDefaultEncoding("UTF-8");
        return commonsMultipartResolver;
    }

}
