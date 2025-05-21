package com.puetsnao.tcrseries.domain.port

import com.puetsnao.tcrseries.domain.model.User
import java.util.Optional
import java.util.UUID

interface UserRepository {
    fun save(user: User): User
    fun findById(id: UUID): Optional<User>
    fun findByEmail(email: String): Optional<User>
    fun existsByEmail(email: String): Boolean
}