package com.puetsnao.tcrseries.infrastructure.persistence.repository

import com.puetsnao.tcrseries.domain.model.User
import com.puetsnao.tcrseries.domain.port.UserRepository
import com.puetsnao.tcrseries.infrastructure.persistence.mapper.toEntity
import com.puetsnao.tcrseries.infrastructure.persistence.mapper.toModel
import org.springframework.stereotype.Component
import java.util.Optional
import java.util.UUID

@Component
class DefaultUserRepository(
    private val userJpaRepository: UserJpaRepository
) : UserRepository {
    
    override fun save(user: User): User {
        val userEntity = user.toEntity()
        return userJpaRepository.save(userEntity).toModel()
    }
    
    override fun findById(id: UUID): Optional<User> {
        return userJpaRepository.findById(id).map { it.toModel() }
    }
    
    override fun findByEmail(email: String): Optional<User> {
        return userJpaRepository.findByEmail(email).map { it.toModel() }
    }
    
    override fun existsByEmail(email: String): Boolean {
        return userJpaRepository.existsByEmail(email)
    }
}