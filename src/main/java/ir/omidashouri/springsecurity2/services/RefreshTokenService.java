package ir.omidashouri.springsecurity2.services;

import ir.omidashouri.springsecurity2.entities.RefreshTokenEntity;
import ir.omidashouri.springsecurity2.pojo.request.RefreshTokenRequest;
import ir.omidashouri.springsecurity2.pojo.response.RefreshTokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;

import java.util.Optional;

public interface RefreshTokenService {

    RefreshTokenEntity createRefreshToken(String userName);
    RefreshTokenEntity verifyExpiration(RefreshTokenEntity token);
    Optional<RefreshTokenEntity> findByToken(String token);
    RefreshTokenResponse generateNewToken(RefreshTokenRequest request);
    ResponseCookie generateRefreshTokenCookie(String token);
    String getRefreshTokenFromCookies(HttpServletRequest request);
    void deleteByToken(String token);
    ResponseCookie getCleanRefreshTokenCookie();
}
