package ir.omidashouri.springsecurity2.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class RefreshTokenRequest {
    private String refreshToken;

}
