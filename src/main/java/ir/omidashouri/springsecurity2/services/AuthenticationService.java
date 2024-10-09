package ir.omidashouri.springsecurity2.services;


import ir.omidashouri.springsecurity2.pojo.request.AuthenticationRequest;
import ir.omidashouri.springsecurity2.pojo.request.RegisterRequest;
import ir.omidashouri.springsecurity2.pojo.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
