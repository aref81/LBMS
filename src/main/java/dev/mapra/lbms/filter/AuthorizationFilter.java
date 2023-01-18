package dev.mapra.lbms.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Handles authorization filter
 * <p>
 * every request passes thorough this filter :
 *  - if it is for login or refresh -> passes
 *  - otherwise -> it is checked for valid access token
 *  </>
 * @author mohammadhoseinaref
 * @version 1.0
 */
public class AuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
         if(request.getServletPath().equals("/login") || request.getServletPath().equals("/api/refresh")) {
             filterChain.doFilter(request,response);
         } else {
             String authorizationHeader = request.getHeader(AUTHORIZATION);

             if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                 try {
                     String token = authorizationHeader.substring("Bearer ".length());
                     Algorithm algorithm = Algorithm.HMAC256("mapra".getBytes());
                     JWTVerifier verifier = JWT.require(algorithm).build();
                     DecodedJWT decodedJWT = verifier.verify(token);

                     String userName = decodedJWT.getSubject();
                     String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

                     Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                     authorities.add(new SimpleGrantedAuthority("ADMIN")); // our only role is admin

                     UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, null, authorities);
                     SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                     filterChain.doFilter(request,response) ;
                 } catch (Exception e) {
                     // handle the exception
                     response.setHeader("error", e.getMessage());
                     HashMap<String,String> errors = new HashMap<>();
                     errors.put("error message",e.getMessage());
                     response.setContentType(APPLICATION_JSON_VALUE);
                     new ObjectMapper().writeValue(response.getOutputStream(), errors);
                 }
             }
         }
    }
}
