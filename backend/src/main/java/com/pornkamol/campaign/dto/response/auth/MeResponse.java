package com.pornkamol.campaign.dto.response.auth;

public record MeResponse(
        Long id,
        String fullName,
        String email,
        String role,
        String avatarUrl,
        String location,
        String jobTitle,
        String organizationName,
        String planName,
        Boolean twoFactorEnabled
) {
}