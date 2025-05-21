package com.puetsnao.tcrseries.infrastructure.persistence.repository

import com.puetsnao.tcrseries.domain.model.RoleType
import com.puetsnao.tcrseries.infrastructure.persistence.entity.RoleEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface RoleJpaRepository : JpaRepository<RoleEntity, UUID> {
    fun findByName(name: RoleType): Optional<RoleEntity>
}