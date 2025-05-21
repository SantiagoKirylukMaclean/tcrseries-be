package com.puetsnao.tcrseries.infrastructure.web.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema(description = "Request payload for refreshing access token")
data class RefreshTokenRequest(
    @field:NotBlank(message = "Refresh token is required")
    @field:Schema(description = "JWT refresh token to obtain new access token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...", required = true)
    val refreshToken: String
)
