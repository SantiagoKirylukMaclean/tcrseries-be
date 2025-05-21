package com.puetsnao.tcrseries.domain.port

import com.puetsnao.tcrseries.domain.model.Role
import com.puetsnao.tcrseries.domain.model.RoleType
import java.util.Optional
import java.util.UUID

interface RoleRepository {
    fun save(role: Role): Role
    fun findById(id: UUID): Optional<Role>
    fun findByName(name: RoleType): Optional<Role>
    fun findAllByUserId(userId: UUID): List<Role>
    fun assignRoleToUser(userId: UUID, roleId: UUID)
}