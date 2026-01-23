package com.pornkamol.campaign.dto.request.auth;

public record TokenResponse(
        String accessToken,
        String tokenType
) {
}
