package com.example.spring.springSecurity.config;

import com.example.spring.springSecurity.Service.JwtService;
import com.example.spring.springSecurity.Service.MyUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//as these need to be filter class we can extend a filter class
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //  Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJraWxsIiwiaWF0IjoxNzIzMTgzNzExLCJleHAiOjE3MjMxODM4MTl9.5nf7dRzKRiuGurN2B9dHh_M5xiu73ZzWPr6rbhOTTHs
        /// in the output we will get like there as Bearer token
        String authHeader = request.getHeader("Authorization");
        String token =null;
        String username = null;
        if(authHeader!= null && authHeader.startsWith("Bearer ")){
            token= authHeader.substring(7);
            //username is encoded in the token tooo,so
            username=jwtService.extractUserName(token);
        }
        if(username != null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = context.getBean(MyUserDetails.class).loadUserByUsername(username);

            if(jwtService.vaildateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);


            }
        }
        filterChain.doFilter(request,response);
    }
}
