package com.gonzalezjuan.gonzalezjuanlab1proyecto3.security;

import com.nimbusds.jose.jwk.source.ImmutableSecret;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import org.springframework.security.web.SecurityFilterChain;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(provider);
    }

    @Bean
    public SecretKey jwtSecretKey(
            @Value("${app.jwt.secret}") String secret
    ) {

        byte[] keyBytes =
                secret.getBytes(StandardCharsets.UTF_8);

        if (keyBytes.length < 32) {
            throw new IllegalArgumentException(
                    "La clave JWT debe tener al menos 32 bytes"
            );
        }

        return new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    @Bean
    public JwtEncoder jwtEncoder(SecretKey secretKey) {

        return new NimbusJwtEncoder(
                new ImmutableSecret<>(secretKey)
        );
    }

    @Bean
    public JwtDecoder jwtDecoder(SecretKey secretKey) {

        return NimbusJwtDecoder
                .withSecretKey(secretKey)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        JwtAuthenticationConverter converter =
                new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt -> {

            List<String> roles =
                    jwt.getClaimAsStringList("roles");

            if (roles == null) {
                return List.of();
            }

            return roles.stream()
                    .map(rol -> (GrantedAuthority)
                            new SimpleGrantedAuthority(rol))
                    .toList();
        });

        return converter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationConverter jwtConverter
    ) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .cors(cors -> {})

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // Login publico
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/auth/login"
                        )
                        .permitAll()

                        // Nuevo cambios para Front-Juang
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/auth/registro"
                        )
                        .permitAll()

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/usuarios"
                        )
                        .hasAuthority("SUPER-ADMIN-ROLE")


                        // Consultas para usuarios autenticados
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/productos",
                                "/api/productos/**",
                                "/api/categorias",
                                "/api/categorias/**"
                        )
                        .authenticated()

                        // Crear: solo SUPER-ADMIN-ROLE
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/productos",
                                "/api/categorias"
                        )
                        .hasAuthority("SUPER-ADMIN-ROLE")

                        // Actualizar: solo SUPER-ADMIN-ROLE
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/productos/**",
                                "/api/categorias/**"
                        )
                        .hasAuthority("SUPER-ADMIN-ROLE")

                        // Eliminar: solo SUPER-ADMIN-ROLE
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/productos/**",
                                "/api/categorias/**"
                        )
                        .hasAuthority("SUPER-ADMIN-ROLE")

                        // Denegar cualquier otra ruta
                        .anyRequest()
                        .denyAll()
                )

                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt ->
                                jwt.jwtAuthenticationConverter(
                                        jwtConverter
                                )
                        )
                );

        return http.build();
    }
}
