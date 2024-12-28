package com.example.spring.springSecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.net.http.HttpRequest;

@Configuration
@EnableWebSecurity
public class springConfig {
// below method we didn't confiure anything & also removed the default configaration
    //so there won't be any security
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
//        return security.build();
//    }
@Autowired
private UserDetailsService userDetailsService;

@Autowired
private JwtFilter jwtFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
//        security.csrf(c->c.disable());
//        //authenticate any and all request
//        security.authorizeHttpRequests(request ->request.anyRequest().authenticated());
//        //give basic http configuration for postman
//        security.httpBasic(Customizer.withDefaults());
//        //for web page access
//        security.formLogin(Customizer.withDefaults());
//        //to use the api with session
//        security.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//        return security.build();
        //or we can write as
      return   security
                .csrf(customizer->customizer.disable())
                        .authorizeHttpRequests(request ->request
                                //for login & to register we don't need to authenticate so we are skipping these two
                               // .requestMatchers("register","login").permitAll()
                                .anyRequest()
                                .authenticated())
                        .httpBasic(Customizer.withDefaults())
                        .sessionManagement(session->session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
              //for server side validation
              .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
/// if we want the spring to take credential form her instead application.propertieses
//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails userDetails= User.withDefaultPasswordEncoder().username("Naga").password("Naga").roles("Developer").build();
//        UserDetails userDetails1= User.withDefaultPasswordEncoder().username("Anna").password("Anna").roles("Manager").build();
//        //as UserDetailsService is interface so we can't return it , so we are using InMemoryUserDetailsManager which implements userDetailsService
//        return new InMemoryUserDetailsManager(userDetails,userDetails1);
////Not useful
//    }
    @Bean
    public AuthenticationProvider provider(){
        DaoAuthenticationProvider authenticationProvider= new DaoAuthenticationProvider();
       //authenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        // here by taking BCryptPasswordEncoder with 12 means(2 power 12 value), it will encode our
        //value in to some encrypted value
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        authenticationProvider.setUserDetailsService(userDetailsService);

return authenticationProvider;
    }

    @Bean
    public AuthenticationManager manager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
