package ir.omidashouri.springsecurity2.services.Impl;


import ir.omidashouri.springsecurity2.entities.UserEntity;
import ir.omidashouri.springsecurity2.enums.TokenType;
import ir.omidashouri.springsecurity2.pojo.request.AuthenticationRequest;
import ir.omidashouri.springsecurity2.pojo.request.RegisterRequest;
import ir.omidashouri.springsecurity2.pojo.response.AuthenticationResponse;
import ir.omidashouri.springsecurity2.repositories.UserRepository;
import ir.omidashouri.springsecurity2.services.AuthenticationService;
import ir.omidashouri.springsecurity2.services.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service @Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        var user = UserEntity.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        user = userRepository.save(user);
        var jwt = jwtService.generateToken(user.getUsername());
        var refreshToken = refreshTokenService.createRefreshToken(user.getUsername());

        var roles = user.getRole().getAuthorities()
                .stream()
                .map(SimpleGrantedAuthority::getAuthority)
                .toList();

        return AuthenticationResponse.builder()
                .accessToken(jwt)
                .username(user.getUsername())
                .id(user.getId())
                .refreshToken(refreshToken.getToken())
                .roles(roles)
                .tokenType(TokenType.BEARER.name())
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));

        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var roles = user.getRole().getAuthorities()
                .stream()
                .map(SimpleGrantedAuthority::getAuthority)
                .toList();
        var jwt = jwtService.generateToken(user.getUsername());
        var refreshToken = refreshTokenService.createRefreshToken(user.getUsername());
        return AuthenticationResponse.builder()
                .accessToken(jwt)
                .roles(roles)
                .username(user.getUsername())
                .id(user.getId())
                .refreshToken(refreshToken.getToken())
                .tokenType( TokenType.BEARER.name())
                .build();
    }
}
