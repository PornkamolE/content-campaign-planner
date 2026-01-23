package com.pornkamol.campaign.controller.auth;

import com.pornkamol.campaign.dto.response.ApiResponse;
import com.pornkamol.campaign.dto.response.auth.MeResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MeController {

    @GetMapping("/me")
    public ApiResponse<MeResponse> me(Authentication authentication) {
        String email = authentication.getName();

        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return ApiResponse.ok("ok", new MeResponse(email, roles));
    }
}
