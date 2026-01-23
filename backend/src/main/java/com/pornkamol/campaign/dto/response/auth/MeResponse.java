package com.pornkamol.campaign.dto.response.auth;

import java.util.List;

public record MeResponse(
        String email,
        List<String> roles
) {
}
