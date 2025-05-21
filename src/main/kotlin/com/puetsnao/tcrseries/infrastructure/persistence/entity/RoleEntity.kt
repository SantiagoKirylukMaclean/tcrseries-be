package com.puetsnao.tcrseries.infrastructure.persistence.entity

import com.puetsnao.tcrseries.domain.model.RoleType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "roles")
class RoleEntity(
    @Id
    val id: UUID = UUID.randomUUID(),
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    val name: RoleType
)