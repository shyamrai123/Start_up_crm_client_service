package com.example.Start_up_crm_client_service.filter;

import com.example.Start_up_crm_client_service.exception.JwtTokenException;
import com.example.Start_up_crm_client_service.exception.JwtTokenExpiredException;
import com.example.Start_up_crm_client_service.exception.JwtTokenParseException;
import com.example.Start_up_crm_client_service.security.JwtPrincipal;
import com.example.Start_up_crm_client_service.util.JwtTokenUtil;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI(); // ✅ IMPORTANT FIX

        // ✅ PUBLIC ENDPOINTS (BYPASS JWT)
        if (path.startsWith("/auth/")
                || path.contains("/api/client/login")
                || path.contains("/api/client/register")
                || path.contains("/api/hr/signup")
                || path.contains("/api/hr/login")) {

            filterChain.doFilter(request, response);
            return;
        }

        String token = extractTokenFromRequest(request);

        try {
            if (Objects.nonNull(token) && isValidJwtFormat(token)) {

                String email = jwtTokenUtil.extractUsername(token);
                Long userId = jwtTokenUtil.extractUserId(token);

                JwtPrincipal principal = new JwtPrincipal(userId, email);

                boolean isTokenValid = jwtTokenUtil.validateToken(token, email);
                boolean isNoAuthentication =
                        SecurityContextHolder.getContext().getAuthentication() == null;

                if (isTokenValid && isNoAuthentication) {

                    List<String> roles = jwtTokenUtil.extractRoles(token);

                    List<SimpleGrantedAuthority> authorities = roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .toList();

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    principal,
                                    null,
                                    authorities
                            );

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (JwtTokenExpiredException ex) {
            sendErrorResponse(response, "JWT Token Expired", HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (JwtTokenException | JwtTokenParseException ex) {
            sendErrorResponse(response, "Invalid JWT Token", HttpServletResponse.SC_BAD_REQUEST);
            return;
        } catch (Exception ex) {
            sendErrorResponse(response, "Authentication Failed", HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response,
                                   String message, int statusCode) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.getWriter().write("{\"message\": \"" + message + "\"}");
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    private boolean isValidJwtFormat(String token) {
        return token.chars().filter(ch -> ch == '.').count() == 2;
    }
}