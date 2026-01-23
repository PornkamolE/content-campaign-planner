package com.pornkamol.campaign.service.auth;

import com.pornkamol.campaign.domain.user.Role;
import com.pornkamol.campaign.domain.user.User;
import com.pornkamol.campaign.dto.request.auth.LoginRequest;
import com.pornkamol.campaign.dto.request.auth.RegisterRequest;
import com.pornkamol.campaign.dto.request.auth.TokenResponse;
import com.pornkamol.campaign.repository.user.RoleRepository;
import com.pornkamol.campaign.repository.user.UserRepository;
import com.pornkamol.campaign.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public TokenResponse register(RegisterRequest rq) {
        if (userRepository.existsByEmail(rq.email())) {
            throw new RuntimeException("email alreadey exists");
        }

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("role USER not found"));

        User u = new User();
        u.setEmail(rq.email());
        u.setPassword(passwordEncoder.encode(rq.password()));
        u.setEnabled(true);
        u.setRoles(Set.of(userRole));

        userRepository.save(u);

        String token = jwtService.generateAccessToken(u.getEmail());
        return new TokenResponse(token, "Bearer");
    }

    public TokenResponse login(LoginRequest rq) {
        var u = userRepository.findByEmail(rq.email())
                .orElseThrow(() -> new RuntimeException("invalid credentials"));

        if (!passwordEncoder.matches(rq.password(), u.getPassword())) {
            throw new RuntimeException("invalid credentials");
        }

        if (!u.isEnabled()) {
            throw new RuntimeException("user disabled");
        }

        String token = jwtService.generateAccessToken(u.getEmail());
        return new TokenResponse(token, "Bearer");
    }
}
