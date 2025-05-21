package com.puetsnao.tcrseries.infrastructure.persistence.entity

import com.puetsnao.tcrseries.domain.model.TokenType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "tokens")
class TokenEntity(
    @Id
    val id: UUID = UUID.randomUUID(),
    
    @Column(nullable = false, unique = true)
    val token: String,
    
    @Enumerated(EnumType.STRING)
    @Column(name = "token_type", nullable = false)
    val tokenType: TokenType = TokenType.BEARER,
    
    @Column(nullable = false)
    val revoked: Boolean = false,
    
    @Column(nullable = false)
    val expired: Boolean = false,
    
    @Column(name = "user_id", nullable = false)
    val userId: UUID,
    
    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)