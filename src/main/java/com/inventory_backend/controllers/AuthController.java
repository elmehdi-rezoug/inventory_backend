package com.inventory_backend.controllers;

import com.inventory_backend.dto.AuthDto.AuthRequest;
import com.inventory_backend.dto.AuthDto.AuthResponse;
import com.inventory_backend.dto.AuthDto.RegisterRequest;
import com.inventory_backend.entities.Role;
import com.inventory_backend.entities.User;
import com.inventory_backend.repositories.UserRepo;
import com.inventory_backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        List<Role> roles = request.getRoles();
        if (roles == null || roles.isEmpty()) {
            roles = new ArrayList<>();
            roles.add(Role.ROLE_USER);
        }

        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .build();

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return ResponseEntity.ok(AuthResponse.builder()
                .token(jwtToken)
                .build());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);

        return ResponseEntity.ok(AuthResponse.builder()
                .token(jwtToken)
                .build());
    }
}