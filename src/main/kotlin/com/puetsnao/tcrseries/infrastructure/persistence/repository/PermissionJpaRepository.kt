package com.puetsnao.tcrseries.infrastructure.persistence.repository

import com.puetsnao.tcrseries.domain.model.Accesses
import com.puetsnao.tcrseries.infrastructure.persistence.entity.PermissionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

/**
 * JPA repository for accessing permission entities in the database.
 */
@Repository
interface PermissionJpaRepository : JpaRepository<PermissionEntity, UUID> {
    /**
     * Find all permissions for a user.
     *
     * @param userId The ID of the user
     * @return List of permission entities for the user
     */
    fun findByUserId(userId: UUID): List<PermissionEntity>
    
    /**
     * Find a permission by user ID and feature.
     *
     * @param userId The ID of the user
     * @param accesses The feature
     * @return The permission entity if found
     */
    fun findByUserIdAndAccesses(userId: UUID, accesses: Accesses): PermissionEntity?
    
    /**
     * Delete a permission by user ID and feature.
     *
     * @param userId The ID of the user
     * @param accesses The feature
     */
    fun deleteByUserIdAndAccesses(userId: UUID, accesses: Accesses)
    
    /**
     * Check if a permission exists for a user and feature.
     *
     * @param userId The ID of the user
     * @param accesses The feature
     * @return True if the permission exists, false otherwise
     */
    fun existsByUserIdAndAccesses(userId: UUID, accesses: Accesses): Boolean
}