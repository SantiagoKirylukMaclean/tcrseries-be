package com.puetsnao.tcrseries.domain.port

import com.puetsnao.tcrseries.domain.model.Accesses
import com.puetsnao.tcrseries.domain.model.User

interface AuthenticationService {
    fun register(email: String, password: String, firstName: String, lastName: String, accesses: List<Accesses> = emptyList()): User
    fun authenticate(email: String, password: String): AuthenticationResult
    fun refreshToken(refreshToken: String): AuthenticationResult
    fun logout(refreshToken: String)
}

data class AuthenticationResult(
    val accessToken: String,
    val refreshToken: String,
    val user: User
)
