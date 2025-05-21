package com.puetsnao.tcrseries.application.service

import com.puetsnao.tcrseries.domain.model.RoleType
import com.puetsnao.tcrseries.domain.model.Token
import com.puetsnao.tcrseries.domain.model.TokenType
import com.puetsnao.tcrseries.domain.model.User
import com.puetsnao.tcrseries.domain.port.AuthenticationResult
import com.puetsnao.tcrseries.domain.port.AuthenticationService
import com.puetsnao.tcrseries.domain.port.RoleRepository
import com.puetsnao.tcrseries.domain.port.TokenRepository
import com.puetsnao.tcrseries.domain.port.UserRepository
import com.puetsnao.tcrseries.infrastructure.security.JwtService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class DefaultAuthenticationService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val tokenRepository: TokenRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager
) : AuthenticationService {

    @Transactional
    override fun register(email: String, password: String, firstName: String, lastName: String): User {
        if (userRepository.existsByEmail(email)) {
            throw IllegalArgumentException("Email already exists")
        }

        val user = User(
            email = email,
            password = passwordEncoder.encode(password),
            firstName = firstName,
            lastName = lastName
        )

        val savedUser = userRepository.save(user)

        val userRole = roleRepository.findByName(RoleType.USER)
            .orElseThrow { IllegalStateException("Default role USER not found") }

        roleRepository.assignRoleToUser(savedUser.id, userRole.id)

        return savedUser
    }

    @Transactional
    override fun authenticate(email: String, password: String): AuthenticationResult {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(email, password)
        )

        val user = userRepository.findByEmail(email)
            .orElseThrow { UsernameNotFoundException("User not found with email: $email") }

        val accessToken = jwtService.generateToken(
            org.springframework.security.core.userdetails.User.builder()
                .username(user.email)
                .password(user.password)
                .build()
        )

        val refreshToken = jwtService.generateRefreshToken(
            org.springframework.security.core.userdetails.User.builder()
                .username(user.email)
                .password(user.password)
                .build()
        )

        revokeAllUserTokens(user.id)
        saveUserToken(user.id, refreshToken)

        return AuthenticationResult(
            accessToken = accessToken,
            refreshToken = refreshToken,
            user = user
        )
    }

    @Transactional
    override fun refreshToken(refreshToken: String): AuthenticationResult {
        val token = tokenRepository.findByToken(refreshToken)
            .orElseThrow { IllegalArgumentException("Invalid refresh token") }

        if (token.expired || token.revoked) {
            throw IllegalArgumentException("Refresh token is expired or revoked")
        }

        val user = userRepository.findById(token.userId)
            .orElseThrow { UsernameNotFoundException("User not found") }

        val accessToken = jwtService.generateToken(
            org.springframework.security.core.userdetails.User.builder()
                .username(user.email)
                .password(user.password)
                .build()
        )

        return AuthenticationResult(
            accessToken = accessToken,
            refreshToken = refreshToken,
            user = user
        )
    }

    @Transactional
    override fun logout(refreshToken: String) {
        val token = tokenRepository.findByToken(refreshToken)
            .orElseThrow { IllegalArgumentException("Invalid refresh token") }

        token.copy(revoked = true, expired = true).let {
            tokenRepository.save(it)
        }
    }

    private fun saveUserToken(userId: UUID, jwtToken: String) {
        val token = Token(
            token = jwtToken,
            tokenType = TokenType.BEARER,
            revoked = false,
            expired = false,
            userId = userId
        )
        tokenRepository.save(token)
    }

    private fun revokeAllUserTokens(userId: UUID) {
        tokenRepository.revokeAllUserTokens(userId)
    }
}
