package com.pornkamol.campaign.controller;

import com.pornkamol.campaign.dto.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/api/health")
    public ApiResponse<Map<String, Object>> health() {
        return ApiResponse.ok("ok", Map.of(
                "service", "campaign",
                "time", OffsetDateTime.now()
        ));
    }
}
