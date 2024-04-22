package com.ps.culinarycompanion.security;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.ps.culinarycompanion.dao.UserRepository;
import com.ps.culinarycompanion.exception.NotFoundException;
import com.ps.culinarycompanion.user.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.time.LocalDate;

@Configuration
public class AuthenticationFilter implements Filter {

    private final UserRepository userRepository;

    public AuthenticationFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
            .orElseThrow(() -> new NotFoundException("User not found"));
    }

    /**
     * Retrieves the access token from the HTTP request header.
     *
     * @param  request  the HTTP servlet request object
     * @return          the access token extracted from the request header
     * @throws IllegalAccessException if the authorization header format is invalid
     */
    private String getTokenFromRequest(HttpServletRequest request) throws IllegalAccessException {
        String token = request.getHeader("Authorization");
        String[] parts = token.split(" ");
        if (parts.length != 2 || !parts[0].contains("Bearer")) {
            throw new IllegalAccessException("Authorization Bearer format invalid. <Bearer {token}>");
        }
        return parts[1];
    }

    /**
     * Filters the incoming request and authenticates the user using a Firebase ID token.
     *
     * @param  servletRequest  the servlet request object representing the incoming request
     * @param  servletResponse the servlet response object representing the outgoing response
     * @param  filterChain     the filter chain for processing the request
     * @throws IOException      if an I/O error occurs
     * @throws ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        try {

            String token = this.getTokenFromRequest((HttpServletRequest) servletRequest);

            FirebaseToken decodedToken = FirebaseAuth
                    .getInstance()
                    .verifyIdToken(token);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            final String userEmail = decodedToken.getEmail();

            if (userEmail != null && authentication == null) {
                UserDetails userDetails;
                try {
                    userDetails = this.userDetailsService().loadUserByUsername(userEmail);
                } catch (NotFoundException ex) {
                    User user = new User(decodedToken.getName(), userEmail, LocalDate.now());
                    userRepository.save(user);
                    userDetails = this.userDetailsService().loadUserByUsername(userEmail);
                }

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) servletRequest));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

            filterChain.doFilter(servletRequest, servletResponse);

        } catch (Exception ex) {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }
}