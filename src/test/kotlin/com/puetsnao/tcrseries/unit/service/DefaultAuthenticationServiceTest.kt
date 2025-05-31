package com.puetsnao.tcrseries.unit.service

import com.puetsnao.tcrseries.application.service.DefaultAuthenticationService
import com.puetsnao.tcrseries.domain.model.RoleType
import com.puetsnao.tcrseries.domain.model.Token
import com.puetsnao.tcrseries.domain.model.TokenType
import com.puetsnao.tcrseries.domain.model.User
import com.puetsnao.tcrseries.domain.port.AuthenticationResult
import com.puetsnao.tcrseries.domain.port.RoleRepository
import com.puetsnao.tcrseries.domain.port.TokenRepository
import com.puetsnao.tcrseries.domain.port.UserRepository
import com.puetsnao.tcrseries.infrastructure.security.JwtService
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class DefaultAuthenticationServiceTest {

    @Test
    fun `register should create a new user when email doesn't exist`() {
        // Given
        val userRepository = mock<UserRepository>()
        val roleRepository = mock<RoleRepository>()
        val tokenRepository = mock<TokenRepository>()
        val passwordEncoder = mock<PasswordEncoder>()
        val jwtService = mock<JwtService>()
        val authenticationManager = mock<AuthenticationManager>()

        val email = "test@example.com"
        val password = "password"
        val firstName = "Test"
        val lastName = "User"
        val encodedPassword = "encodedPassword"
        val userId = UUID.randomUUID()
        val roleId = UUID.randomUUID()

        val user = User(
            id = userId,
            email = email,
            password = encodedPassword,
            firstName = firstName,
            lastName = lastName
        )

        val role = com.puetsnao.tcrseries.domain.model.Role(
            id = roleId,
            name = RoleType.USER
        )

        whenever(userRepository.existsByEmail(email)).thenReturn(false)
        whenever(passwordEncoder.encode(password)).thenReturn(encodedPassword)
        whenever(userRepository.save(any())).thenReturn(user)
        whenever(roleRepository.findByName(RoleType.USER)).thenReturn(Optional.of(role))

        val authenticationService = DefaultAuthenticationService(
            userRepository,
            roleRepository,
            tokenRepository,
            passwordEncoder,
            jwtService,
            authenticationManager
        )

        // When
        val result = authenticationService.register(email, password, firstName, lastName)

        // Then
        assertNotNull(result)
        assertEquals(email, result.email)
        assertEquals(encodedPassword, result.password)
        assertEquals(firstName, result.firstName)
        assertEquals(lastName, result.lastName)

        verify(userRepository).existsByEmail(email)
        verify(passwordEncoder).encode(password)
        verify(userRepository).save(any())
        verify(roleRepository).findByName(RoleType.USER)
        verify(roleRepository).assignRoleToUser(userId, roleId)
    }
}
