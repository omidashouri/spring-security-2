package ir.omidashouri.springsecurity2.controllers;

import ir.omidashouri.springsecurity2.entities.UserEntity;
import ir.omidashouri.springsecurity2.pojo.request.AuthenticationRequest;
import ir.omidashouri.springsecurity2.pojo.response.AuthenticationResponse;
import ir.omidashouri.springsecurity2.services.Impl.JwtServiceImpl;
import ir.omidashouri.springsecurity2.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/public")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtServiceImpl jwtService;
    @Qualifier("userDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;

    @PostMapping("/token")
    public String getToken(@RequestBody UserEntity user) {
        Authentication authentication =
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user);
        } else {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("/authenticate")
    public AuthenticationResponse createToken(@RequestBody AuthenticationRequest request) {
        log.info("createToken(-)");
        // Authenticate the user
        UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
        // Generate the token
        String jwtToken = jwtService.generateToken(userDetails);

        return AuthenticationResponse
                .builder()
                .username(userDetails.getUsername())
                .roles(userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .accessToken(jwtToken)
                .build();
    }
}
