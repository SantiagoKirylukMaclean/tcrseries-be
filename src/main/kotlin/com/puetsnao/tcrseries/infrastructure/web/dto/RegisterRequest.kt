package com.puetsnao.tcrseries.infrastructure.web.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema(description = "Request payload for user registration")
data class RegisterRequest(
    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Email should be valid")
    @field:Schema(description = "User's email address", example = "user@example.com", required = true)
    val email: String,

    @field:NotBlank(message = "Password is required")
    @field:Size(min = 8, message = "Password must be at least 8 characters")
    @field:Schema(description = "User's password (min 8 characters)", example = "password123", required = true)
    val password: String,

    @field:NotBlank(message = "First name is required")
    @field:Schema(description = "User's first name", example = "John", required = true)
    val firstName: String,

    @field:NotBlank(message = "Last name is required")
    @field:Schema(description = "User's last name", example = "Doe", required = true)
    val lastName: String
)
