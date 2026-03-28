package com.pornkamol.campaign.controller.auth;

import com.pornkamol.campaign.dto.request.auth.LoginRequest;
import com.pornkamol.campaign.dto.request.auth.RegisterRequest;
import com.pornkamol.campaign.dto.response.ApiResponse;
import com.pornkamol.campaign.dto.response.auth.TokenResponse;
import com.pornkamol.campaign.service.auth.AuthService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/register", consumes = "multipart/form-data")
    public ApiResponse<TokenResponse> register(
            @ModelAttribute RegisterRequest request,
            @RequestPart(value = "avatarFile", required = false) MultipartFile avatarFile
    ) {
        return ApiResponse.ok(authService.register(request, avatarFile));
    }

    @PostMapping("/login")
    public ApiResponse<TokenResponse> login(@RequestBody LoginRequest rq) {
        return ApiResponse.ok("ok", authService.login(rq));
    }
}