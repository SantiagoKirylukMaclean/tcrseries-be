package com.puetsnao.tcrseries.domain.port

import com.puetsnao.tcrseries.domain.model.Accesses
import com.puetsnao.tcrseries.domain.model.Permission
import java.util.UUID

/**
 * Repository interface for managing permissions.
 */
interface PermissionRepository {
    /**
     * Find all permissions for a user.
     *
     * @param userId The ID of the user
     * @return List of permissions for the user
     */
    fun findByUserId(userId: UUID): List<Permission>
    
    /**
     * Check if a user has a specific permission.
     *
     * @param userId The ID of the user
     * @param accesses The feature to check permission for
     * @return True if the user has permission, false otherwise
     */
    fun hasPermission(userId: UUID, accesses: Accesses): Boolean
    
    /**
     * Grant a permission to a user.
     *
     * @param userId The ID of the user
     * @param accesses The feature to grant permission for
     * @return The created permission
     */
    fun grantPermission(userId: UUID, accesses: Accesses): Permission
    
    /**
     * Revoke a permission from a user.
     *
     * @param userId The ID of the user
     * @param accesses The feature to revoke permission for
     */
    fun revokePermission(userId: UUID, accesses: Accesses)
}