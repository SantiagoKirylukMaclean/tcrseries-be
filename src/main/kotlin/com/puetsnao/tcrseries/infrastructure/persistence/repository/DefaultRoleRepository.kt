package com.puetsnao.tcrseries.infrastructure.persistence.repository

import com.puetsnao.tcrseries.domain.model.Role
import com.puetsnao.tcrseries.domain.model.RoleType
import com.puetsnao.tcrseries.domain.port.RoleRepository
import com.puetsnao.tcrseries.infrastructure.persistence.entity.UserRoleEntity
import com.puetsnao.tcrseries.infrastructure.persistence.mapper.toEntity
import com.puetsnao.tcrseries.infrastructure.persistence.mapper.toModel
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.Optional
import java.util.UUID

@Component
class DefaultRoleRepository(
    private val roleJpaRepository: RoleJpaRepository,
    private val userRoleJpaRepository: UserRoleJpaRepository
) : RoleRepository {
    
    override fun save(role: Role): Role {
        val roleEntity = role.toEntity()
        return roleJpaRepository.save(roleEntity).toModel()
    }
    
    override fun findById(id: UUID): Optional<Role> {
        return roleJpaRepository.findById(id).map { it.toModel() }
    }
    
    override fun findByName(name: RoleType): Optional<Role> {
        return roleJpaRepository.findByName(name).map { it.toModel() }
    }
    
    override fun findAllByUserId(userId: UUID): List<Role> {
        val roleIds = userRoleJpaRepository.findRoleIdsByUserId(userId)
        return roleJpaRepository.findAllById(roleIds).map { it.toModel() }
    }
    
    @Transactional
    override fun assignRoleToUser(userId: UUID, roleId: UUID) {
        if (!userRoleJpaRepository.existsByUserIdAndRoleId(userId, roleId)) {
            val userRole = UserRoleEntity(userId = userId, roleId = roleId)
            userRoleJpaRepository.save(userRole)
        }
    }
}