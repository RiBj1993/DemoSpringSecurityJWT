package com.example.jwt.securite;
import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


	@Override
	protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request,
			jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain)
			throws jakarta.servlet.ServletException, IOException { String authorizationHeader = request.getHeader("Authorization");

	        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	            String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
	            if (jwtUtil.validateToken(token)) {
	                String username = jwtUtil.extractUsername(token);

	                // Create an authentication token (replace "ROLE_USER" with actual roles if needed)
	                UsernamePasswordAuthenticationToken authentication =
	                        new UsernamePasswordAuthenticationToken(username, null, null);

	                // Set the authentication in the security context
	                SecurityContextHolder.getContext().setAuthentication(authentication);
	            }
	        }

	        // Continue with the filter chain
	        filterChain.doFilter(request, response);
	    }
}