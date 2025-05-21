package com.puetsnao.tcrseries.infrastructure.web.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

@Schema(description = "Request payload for user authentication")
data class AuthenticationRequest(
    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Email should be valid")
    @field:Schema(description = "User's email address", example = "user@example.com", required = true)
    val email: String,

    @field:NotBlank(message = "Password is required")
    @field:Schema(description = "User's password", example = "password123", required = true)
    val password: String
)
