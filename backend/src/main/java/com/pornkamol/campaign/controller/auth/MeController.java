package com.pornkamol.campaign.controller.auth;

import com.pornkamol.campaign.dto.response.ApiResponse;
import com.pornkamol.campaign.dto.response.auth.MeResponse;
import com.pornkamol.campaign.service.auth.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class MeController {

    private final AuthService authService;

    public MeController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/me")
    public ApiResponse<MeResponse> me(Authentication authentication) {
        String email = authentication.getName();
        return ApiResponse.ok("ok", authService.me(email));
    }
}