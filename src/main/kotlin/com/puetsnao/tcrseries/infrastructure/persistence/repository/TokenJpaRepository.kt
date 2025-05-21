package com.puetsnao.tcrseries.infrastructure.persistence.repository

import com.puetsnao.tcrseries.infrastructure.persistence.entity.TokenEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface TokenJpaRepository : JpaRepository<TokenEntity, UUID> {
    fun findByToken(token: String): Optional<TokenEntity>
    
    @Query("SELECT t FROM TokenEntity t WHERE t.userId = :userId AND (t.expired = false OR t.revoked = false)")
    fun findAllValidTokensByUserId(userId: UUID): List<TokenEntity>
    
    @Modifying
    @Query("UPDATE TokenEntity t SET t.expired = true, t.revoked = true WHERE t.userId = :userId")
    fun revokeAllUserTokens(userId: UUID)
}