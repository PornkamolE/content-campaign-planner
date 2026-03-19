package com.pornkamol.campaign.dto.response.auth;

public record MeResponse(
        Long id,
        String username,
        String email,
        String role,
        String avatarUrl
) {
}