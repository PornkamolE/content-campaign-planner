package com.pornkamol.campaign.dto.response.auth;

public record TokenResponse(
        String accessToken,
        String tokenType
) {
}
