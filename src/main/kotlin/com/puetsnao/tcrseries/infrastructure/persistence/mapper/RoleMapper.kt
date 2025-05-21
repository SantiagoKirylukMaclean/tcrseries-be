package com.puetsnao.tcrseries.infrastructure.persistence.mapper

import com.puetsnao.tcrseries.domain.model.Role
import com.puetsnao.tcrseries.infrastructure.persistence.entity.RoleEntity

fun Role.toEntity(): RoleEntity {
    return RoleEntity(
        id = this.id,
        name = this.name
    )
}

fun RoleEntity.toModel(): Role {
    return Role(
        id = this.id,
        name = this.name
    )
}