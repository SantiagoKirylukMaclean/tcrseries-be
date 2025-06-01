package com.puetsnao.tcrseries.domain.model

import java.util.UUID

/**
 * Represents a permission for a user to access a specific feature.
 */
data class Permission(
    val id: UUID = UUID.randomUUID(),
    val userId: UUID,
    val accesses: Accesses
)