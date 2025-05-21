package com.puetsnao.tcrseries.infrastructure.web.dto

import java.util.UUID

data class AuthenticationResponse(
    val accessToken: String,
    val refreshToken: String,
    val userId: UUID,
    val email: String,
    val firstName: String,
    val lastName: String
)