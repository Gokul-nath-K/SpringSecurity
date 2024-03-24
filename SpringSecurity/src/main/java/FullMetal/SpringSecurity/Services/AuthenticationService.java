package FullMetal.SpringSecurity.Services;

import FullMetal.SpringSecurity.DTO.AuthenticationRequest;
import FullMetal.SpringSecurity.DTO.AuthenticationResponse;
import FullMetal.SpringSecurity.DTO.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
