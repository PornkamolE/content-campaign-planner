package com.pornkamol.campaign.service.auth;

import com.pornkamol.campaign.domain.user.Role;
import com.pornkamol.campaign.domain.user.User;
import com.pornkamol.campaign.dto.request.auth.LoginRequest;
import com.pornkamol.campaign.dto.request.auth.RegisterRequest;
import com.pornkamol.campaign.dto.response.auth.MeResponse;
import com.pornkamol.campaign.dto.response.auth.TokenResponse;
import com.pornkamol.campaign.repository.user.RoleRepository;
import com.pornkamol.campaign.repository.user.UserRepository;
import com.pornkamol.campaign.security.JwtService;
import com.pornkamol.campaign.service.file.FileStorageService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final FileStorageService fileStorageService;

    public AuthService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            FileStorageService fileStorageService
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.fileStorageService = fileStorageService;
    }

    public TokenResponse register(RegisterRequest rq, MultipartFile avatarFile) {
        if (userRepository.existsByEmail(rq.getEmail())) {
            throw new RuntimeException("email already exists");
        }

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("role USER not found"));

        String avatarUrl = fileStorageService.storeAvatar(avatarFile);

        User u = new User();
        u.setFullName(rq.getFullName());
        u.setEmail(rq.getEmail());
        u.setPassword(passwordEncoder.encode(rq.getPassword()));
        u.setEnabled(true);
        u.setAvatarUrl(avatarUrl);
        u.setLocation(rq.getLocation());
        u.setJobTitle(rq.getJobTitle());
        u.setOrganizationName(rq.getOrganizationName());
        u.setPlanName(rq.getPlanName());
        u.setTwoFactorEnabled(Boolean.TRUE.equals(rq.getTwoFactorEnabled()));
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

    public MeResponse me(String email) {
        var u = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("user not found"));

        String role = u.getRoles().stream()
                .map(Role::getName)
                .sorted()
                .findFirst()
                .orElse("USER");

        return new MeResponse(
                u.getId(),
                u.getFullName(),
                u.getEmail(),
                role,
                u.getAvatarUrl(),
                u.getLocation(),
                u.getJobTitle(),
                u.getOrganizationName(),
                u.getPlanName(),
                u.isTwoFactorEnabled()
        );
    }
}