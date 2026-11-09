package com.gonzalezjuan.gonzalezjuanlab1proyecto3.service;

import com.gonzalezjuan.gonzalezjuanlab1proyecto3.dto.*;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;

    @Value("${app.jwt.expiration-minutes}")
    private long expirationMinutes;

    public LoginResponseDTO login(LoginRequestDTO request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.username(),
                                request.password()
                        )
                );

        Instant now = Instant.now();

        Instant expiration = now.plus(
                expirationMinutes,
                ChronoUnit.MINUTES
        );

        var roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("productos-categorias-api")
                .subject(authentication.getName())
                .issuedAt(now)
                .expiresAt(expiration)
                .claim("roles", roles)
                .build();

        JwsHeader header = JwsHeader
                .with(MacAlgorithm.HS256)
                .build();

        String token = jwtEncoder.encode(
                JwtEncoderParameters.from(header, claims)
        ).getTokenValue();

        return new LoginResponseDTO(
                token,
                "Bearer",
                expirationMinutes * 60
        );
    }
}