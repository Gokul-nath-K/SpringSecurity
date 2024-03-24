package FullMetal.SpringSecurity.Controllers;

import FullMetal.SpringSecurity.DTO.Request.AuthenticationRequest;
import FullMetal.SpringSecurity.DTO.Response.AuthenticationResponse;
import FullMetal.SpringSecurity.DTO.Request.RegisterRequest;
import FullMetal.SpringSecurity.Services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {

        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {


        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
