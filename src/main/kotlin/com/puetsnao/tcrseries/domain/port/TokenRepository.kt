package com.puetsnao.tcrseries.domain.port

import com.puetsnao.tcrseries.domain.model.Token
import java.util.Optional
import java.util.UUID

interface TokenRepository {
    fun save(token: Token): Token
    fun findByToken(token: String): Optional<Token>
    fun findAllValidTokensByUserId(userId: UUID): List<Token>
    fun revokeAllUserTokens(userId: UUID)
}