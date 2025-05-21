package com.puetsnao.tcrseries.domain.model

import java.time.LocalDateTime
import java.util.UUID

data class Token(
    val id: UUID = UUID.randomUUID(),
    val token: String,
    val tokenType: TokenType = TokenType.BEARER,
    val revoked: Boolean = false,
    val expired: Boolean = false,
    val userId: UUID,
    val createdAt: LocalDateTime = LocalDateTime.now()
)

enum class TokenType {
    BEARER
}