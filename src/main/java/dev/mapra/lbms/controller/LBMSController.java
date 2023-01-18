package dev.mapra.lbms.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mapra.lbms.model.Admin;
import dev.mapra.lbms.model.Book;
import dev.mapra.lbms.model.Interfaces.BookInterface;
import dev.mapra.lbms.model.Interfaces.PublisherInterface;
import dev.mapra.lbms.model.Interfaces.WriterInterface;
import dev.mapra.lbms.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Main System API Controller
 * runs at subAddress = "**\api"
 * <p>
 *  - save publisher (POST)
 *  - save writer (POST)
 *  - save book (POST)
 *  - get book list (GET)
 *  - get new access key from refresh key (GET)
 * </>
 *
 * @author mohammadhoseinaref
 * @version 1.0
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LBMSController {
    private final AdminService adminService;

    @PostMapping("/publisher/save")
    public ResponseEntity<PublisherInterface> savePublisher(@RequestBody PublisherInterface publisherInterface, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = getUserName(request,response).orElseThrow(() -> new RuntimeException("cannot find username"));
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/publisher/save").toUriString());
        return ResponseEntity.created(uri).body(adminService.savePublisher(publisherInterface, userName));
    }

    @PostMapping("/writer/save")
    public ResponseEntity<WriterInterface> saveWriter(@RequestBody WriterInterface writerInterface, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = getUserName(request,response).orElseThrow(() -> new RuntimeException("cannot find username"));
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/writer/save").toUriString());
        return ResponseEntity.created(uri).body(adminService.saveWriter(writerInterface, userName));
    }

    @PostMapping("/book/save")
    public ResponseEntity<BookInterface> saveBook(@RequestBody BookInterface bookInterface, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = getUserName(request,response).orElseThrow(() -> new RuntimeException("cannot find username"));
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/book/save").toUriString());
        return ResponseEntity.created(uri).body(adminService.saveBook(bookInterface, userName));
    }

    @GetMapping("/book")
    public ResponseEntity<List<BookInterface>> getBooks(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = getUserName(request,response).orElseThrow(() -> new RuntimeException("cannot find username"));
        List<Book> books = adminService.getBooksList(userName);
        List<BookInterface> retBooks = new ArrayList<>(books.size());

        for (Book b:
                books) {
            retBooks.add(new BookInterface(b));
        }

        return ResponseEntity.ok().body(retBooks);
    }

    @GetMapping("/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("mapra".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);

                String userName = decodedJWT.getSubject();

                Admin admin = adminService.getAdmin(userName);

                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ADMIN"); // our only role is ADMIN
                authorities.add(authority);

                String accessToken = JWT.create()
                        .withSubject(userName)
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("role", authorities)
                        .sign(algorithm);

                HashMap<String, String> res = new HashMap<>();
                res.put("access_token", accessToken);
                res.put("refresh_token", refreshToken);

                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), res);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } catch (Exception e) {
                // handle the exception
                response.setHeader("error", e.getMessage());
                HashMap<String, String> errors = new HashMap<>();
                errors.put("error message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), errors);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

    private Optional<String> getUserName(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        Optional<String> returnValue;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("mapra".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);

                String userName = decodedJWT.getSubject();
                returnValue = Optional.ofNullable(userName);


            } catch (Exception e) {
                // handle the exception
                response.setHeader("error", e.getMessage());
                HashMap<String, String> errors = new HashMap<>();
                errors.put("error message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), errors);
                returnValue = Optional.empty();
            }
        } else {
            throw new RuntimeException("cannot retrieve user name");
        }
        return returnValue;
    }
}
