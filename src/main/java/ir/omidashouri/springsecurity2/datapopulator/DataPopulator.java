package ir.omidashouri.springsecurity2.datapopulator;

import ir.omidashouri.springsecurity2.enums.Role;
import ir.omidashouri.springsecurity2.pojo.request.RegisterRequest;
import ir.omidashouri.springsecurity2.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataPopulator implements CommandLineRunner {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        RegisterRequest user = RegisterRequest
                .builder()
                .firstname("user")
                .lastname("user")
                .username("user")
                .password(passwordEncoder.encode("user"))
                .role(Role.USER)
                .build();
        authenticationService.register(user);

        RegisterRequest admin = RegisterRequest
                .builder()
                .firstname("admin")
                .lastname("admin")
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .role(Role.ADMIN)
                .build();
        authenticationService.register(admin);
    }
}
