package com.puetsnao.tcrseries.infrastructure.persistence.repository

import com.puetsnao.tcrseries.infrastructure.persistence.entity.UserRoleEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRoleJpaRepository : JpaRepository<UserRoleEntity, UUID> {
    @Query("SELECT ur.roleId FROM UserRoleEntity ur WHERE ur.userId = :userId")
    fun findRoleIdsByUserId(userId: UUID): List<UUID>
    
    fun existsByUserIdAndRoleId(userId: UUID, roleId: UUID): Boolean
}