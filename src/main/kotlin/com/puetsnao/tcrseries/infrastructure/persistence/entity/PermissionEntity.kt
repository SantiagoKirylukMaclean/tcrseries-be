package com.puetsnao.tcrseries.infrastructure.persistence.entity

import com.puetsnao.tcrseries.domain.model.Accesses
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

/**
 * Entity representing a user's permission to access a feature in the database.
 */
@Entity
@Table(name = "permissions")
class PermissionEntity(
    @Id
    val id: UUID = UUID.randomUUID(),
    
    @Column(name = "user_id", nullable = false)
    val userId: UUID,
    
    @Enumerated(EnumType.STRING)
    @Column(name = "access", nullable = false)
    val accesses: Accesses
)