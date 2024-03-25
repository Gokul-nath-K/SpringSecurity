package FullMetal.SpringSecurity.Services.Implementations;

import FullMetal.SpringSecurity.DTO.Request.AuthenticationRequest;
import FullMetal.SpringSecurity.DTO.Response.AuthenticationResponse;
import FullMetal.SpringSecurity.DTO.Request.RegisterRequest;
import FullMetal.SpringSecurity.Models.Enumerate.Role;
import FullMetal.SpringSecurity.Models.Token;
import FullMetal.SpringSecurity.Models.User;
import FullMetal.SpringSecurity.Repository.TokenRepository;
import FullMetal.SpringSecurity.Repository.UserRepository;
import FullMetal.SpringSecurity.Services.AuthenticationService;
import FullMetal.SpringSecurity.Utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final TokenRepository tokenRepository;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {

        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole().toUpperCase()))
                .build();

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );


        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", user.getRole().toString());

        var accessToken = jwtService.generateToken(extraClaims, user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        return AuthenticationResponse.builder().accessToken(accessToken).build();
    }

    private void saveUserToken(User user, String accessToken) {
        var token = Token.builder().user(user).token(accessToken).expired(false).revoked(false).build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllByUser_IdAndExpiredFalseAndRevokedFalse(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
    token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
