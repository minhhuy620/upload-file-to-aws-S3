package blog.be.configuration.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class JWTTokenFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Autowired
//    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtil.validateJwtToken(jwt)) {
                String username = jwtUtil.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
//            response.setHeader("Access-Control-Allow-Origin", allowedOrigin);
//            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//            response.setHeader("Access-Control-Allow-Credentials","true");
//            response.setHeader("Access-Control-Max-Age", "3600");
//            response.setHeader("Access-Control-Allow-Headers", "authorization, content-type, xsrf-token");
//            response.addHeader("Access-Control-Expose-Headers", "xsrf-token");
            if ("OPTIONS".equals(request.getMethod())) {
                response.setStatus(HttpServletResponse.SC_OK);
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
    }
    private String parseJwt(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return token;
    }
//    private void authorizeRequest(HttpServletRequest request) {
//        logger.debug("Processing authentication for '{}'", request.getRequestURL());
//        String requestHeader = request.getHeader("Authorization");
//        if (requestHeader == null || !requestHeader.startsWith("Bearer ")) {
//            logger.warn("Authorization failed. No JWT token found");
//            return;
//        }
//        String username;
//        String authToken =  requestHeader.substring(7);
//
//        try {
//            username = jwtUtil.getUsernameFromToken(authToken);
//        } catch (IllegalArgumentException e) {
//            logger.error("Error during getting username from token", e);
//            return;
//        } catch (ExpiredJwtException e) {
//            logger.warn("The token has expired", e);
//            return;
//        }
//        if (username == null || SecurityContextHolder.getContext().getAuthentication() != null) return;
//        logger.debug("Security context was null, so authorizing user '{}'...", username);
//        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
//        if (!jwtUtil.validateToken(authToken, userDetails)) {
//            logger.error("Not a valid token!!!");
//            return;
//        }
//        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//        logger.info("Authorized user '{}', setting security context...", username);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//    }
}