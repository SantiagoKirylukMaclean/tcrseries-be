package com.puetsnao.tcrseries.infrastructure.persistence.mapper

import com.puetsnao.tcrseries.domain.model.User
import com.puetsnao.tcrseries.infrastructure.persistence.entity.UserEntity

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = this.id,
        email = this.email,
        password = this.password,
        firstName = this.firstName,
        lastName = this.lastName,
        enabled = this.enabled,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}

fun UserEntity.toModel(): User {
    return User(
        id = this.id,
        email = this.email,
        password = this.password,
        firstName = this.firstName,
        lastName = this.lastName,
        enabled = this.enabled,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}