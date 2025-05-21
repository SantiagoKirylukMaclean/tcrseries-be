package com.puetsnao.tcrseries.domain.model

import java.util.UUID

enum class RoleType {
    USER,
    ADMIN
}

data class Role(
    val id: UUID = UUID.randomUUID(),
    val name: RoleType
)