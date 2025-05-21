package com.puetsnao.tcrseries.infrastructure.persistence.mapper

import com.puetsnao.tcrseries.domain.model.Token
import com.puetsnao.tcrseries.infrastructure.persistence.entity.TokenEntity

fun Token.toEntity(): TokenEntity {
    return TokenEntity(
        id = this.id,
        token = this.token,
        tokenType = this.tokenType,
        revoked = this.revoked,
        expired = this.expired,
        userId = this.userId,
        createdAt = this.createdAt
    )
}

fun TokenEntity.toModel(): Token {
    return Token(
        id = this.id,
        token = this.token,
        tokenType = this.tokenType,
        revoked = this.revoked,
        expired = this.expired,
        userId = this.userId,
        createdAt = this.createdAt
    )
}