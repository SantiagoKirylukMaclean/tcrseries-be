package com.puetsnao.tcrseries.infrastructure.web.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

@Schema(description = "Response payload for successful authentication")
data class AuthenticationResponse(
    @field:Schema(description = "JWT access token for API authorization", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    val accessToken: String,

    @field:Schema(description = "JWT refresh token to obtain new access tokens", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    val refreshToken: String,

    @field:Schema(description = "Unique identifier of the user", example = "123e4567-e89b-12d3-a456-426614174000")
    val userId: UUID,

    @field:Schema(description = "User's email address", example = "user@example.com")
    val email: String,

    @field:Schema(description = "User's first name", example = "John")
    val firstName: String,

    @field:Schema(description = "User's last name", example = "Doe")
    val lastName: String
)
