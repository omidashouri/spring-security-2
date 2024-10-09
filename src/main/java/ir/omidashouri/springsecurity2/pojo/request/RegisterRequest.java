package ir.omidashouri.springsecurity2.pojo.request;

import ir.omidashouri.springsecurity2.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "firstname is required")
    private String firstname;
    @NotBlank(message = "lastname is required")
    private String lastname;
    @NotBlank(message = "email is required")
//    @Email(message = "email format is not valid")
    private String username;
    @NotBlank(message = "password is required")
//    @StrongPassword
    private String password;
    @NotNull
    private Role role;
}
