package com.puetsnao.tcrseries.infrastructure.persistence.repository

import com.puetsnao.tcrseries.domain.model.Token
import com.puetsnao.tcrseries.domain.port.TokenRepository
import com.puetsnao.tcrseries.infrastructure.persistence.mapper.toEntity
import com.puetsnao.tcrseries.infrastructure.persistence.mapper.toModel
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.Optional
import java.util.UUID

@Component
class DefaultTokenRepository(
    private val tokenJpaRepository: TokenJpaRepository
) : TokenRepository {
    
    override fun save(token: Token): Token {
        val tokenEntity = token.toEntity()
        return tokenJpaRepository.save(tokenEntity).toModel()
    }
    
    override fun findByToken(token: String): Optional<Token> {
        return tokenJpaRepository.findByToken(token).map { it.toModel() }
    }
    
    override fun findAllValidTokensByUserId(userId: UUID): List<Token> {
        return tokenJpaRepository.findAllValidTokensByUserId(userId).map { it.toModel() }
    }
    
    @Transactional
    override fun revokeAllUserTokens(userId: UUID) {
        tokenJpaRepository.revokeAllUserTokens(userId)
    }
}