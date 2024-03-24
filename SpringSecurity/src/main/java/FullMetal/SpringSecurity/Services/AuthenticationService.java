package FullMetal.SpringSecurity.Services;

import FullMetal.SpringSecurity.DTO.Request.AuthenticationRequest;
import FullMetal.SpringSecurity.DTO.Response.AuthenticationResponse;
import FullMetal.SpringSecurity.DTO.Request.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
