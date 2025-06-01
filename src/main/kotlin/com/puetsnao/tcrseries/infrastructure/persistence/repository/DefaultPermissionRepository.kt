package com.puetsnao.tcrseries.infrastructure.persistence.repository

import com.puetsnao.tcrseries.domain.model.Accesses
import com.puetsnao.tcrseries.domain.model.Permission
import com.puetsnao.tcrseries.domain.port.PermissionRepository
import com.puetsnao.tcrseries.infrastructure.persistence.entity.PermissionEntity
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

/**
 * Default implementation of the PermissionRepository interface.
 */
@Repository
class DefaultPermissionRepository(
    private val permissionJpaRepository: PermissionJpaRepository
) : PermissionRepository {

    override fun findByUserId(userId: UUID): List<Permission> {
        return permissionJpaRepository.findByUserId(userId)
            .map { it.toDomain() }
    }

    override fun hasPermission(userId: UUID, accesses: Accesses): Boolean {
        return permissionJpaRepository.existsByUserIdAndAccesses(userId, accesses)
    }

    @Transactional
    override fun grantPermission(userId: UUID, accesses: Accesses): Permission {
        // Check if permission already exists
        if (hasPermission(userId, accesses)) {
            return permissionJpaRepository.findByUserIdAndAccesses(userId, accesses)!!.toDomain()
        }
        
        // Create new permission
        val permissionEntity = PermissionEntity(
            userId = userId,
            accesses = accesses
        )
        
        return permissionJpaRepository.save(permissionEntity).toDomain()
    }

    @Transactional
    override fun revokePermission(userId: UUID, accesses: Accesses) {
        permissionJpaRepository.deleteByUserIdAndAccesses(userId, accesses)
    }
    
    /**
     * Convert a permission entity to a domain permission.
     */
    private fun PermissionEntity.toDomain(): Permission {
        return Permission(
            id = this.id,
            userId = this.userId,
            accesses = this.accesses
        )
    }
}