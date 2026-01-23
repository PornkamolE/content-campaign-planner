package com.pornkamol.campaign.controller.auth;

import com.pornkamol.campaign.dto.request.auth.LoginRequest;
import com.pornkamol.campaign.dto.request.auth.RegisterRequest;
import com.pornkamol.campaign.dto.request.auth.TokenResponse;
import com.pornkamol.campaign.dto.response.ApiResponse;
import com.pornkamol.campaign.service.auth.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController (AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ApiResponse<TokenResponse> register(@RequestBody RegisterRequest rq) {
        return ApiResponse.ok("registered", authService.register(rq));
    }

    @PostMapping("/login")
    public ApiResponse<TokenResponse> login(@RequestBody LoginRequest rq) {
        return ApiResponse.ok("ok", authService.login(rq));
    }
}
