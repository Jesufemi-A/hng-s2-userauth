package com.hng_s2_userauth.service;



import com.hng_s2_userauth.model.UserEntity;
import com.hng_s2_userauth.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {


    final JwtService jwtService;
    final UserRepository userRepository;

    @Override
    protected void doFilterInternal(@NotNull  HttpServletRequest request, @NotNull HttpServletResponse response,
                                 @NotNull FilterChain filterChain) throws ServletException, IOException {


        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }


        final String jwt = authHeader.substring(7);
        final String username = jwtService.extractUsername(jwt);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails;

        if (username != null && authentication == null) {
            Optional<UserEntity> user = userRepository.findByEmail(username);

            if (user.isEmpty()) {
                filterChain.doFilter(request, response);
                return;
            }
            userDetails = user.get();

            if (jwtService.isTokenValid(jwt, userDetails)) {
                SecurityContext contextHolder = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                contextHolder.setAuthentication(token);
                SecurityContextHolder.setContext(contextHolder);
            }


        }

        filterChain.doFilter(request,response);
    }
}
