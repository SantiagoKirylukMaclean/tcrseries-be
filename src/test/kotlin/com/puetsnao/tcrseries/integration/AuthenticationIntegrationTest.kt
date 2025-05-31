package com.puetsnao.tcrseries.integration

import com.fasterxml.jackson.databind.ObjectMapper
import com.puetsnao.tcrseries.config.TestConfig
import com.puetsnao.tcrseries.domain.model.User
import com.puetsnao.tcrseries.domain.port.AuthenticationResult
import com.puetsnao.tcrseries.domain.port.UserRepository
import com.puetsnao.tcrseries.infrastructure.web.dto.AuthenticationRequest
import com.puetsnao.tcrseries.infrastructure.web.dto.AuthenticationResponse
import com.puetsnao.tcrseries.infrastructure.web.dto.RefreshTokenRequest
import com.puetsnao.tcrseries.infrastructure.web.dto.RegisterRequest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestConfig::class)
@ActiveProfiles("test")
class AuthenticationIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun `should register, authenticate, and refresh token successfully`() {
        // Register a new user
        val registerRequest = RegisterRequest(
            email = "integration-test@example.com",
            password = "password123",
            firstName = "Integration",
            lastName = "Test"
        )

        val registerResult = mockMvc.perform(
            post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.email").value(registerRequest.email))
            .andExpect(jsonPath("$.firstName").value(registerRequest.firstName))
            .andExpect(jsonPath("$.lastName").value(registerRequest.lastName))
            .andReturn()

        // Verify user was created in the database
        val user = userRepository.findByEmail(registerRequest.email)
        assertTrue(user.isPresent, "User should be created in the database")
        assertNotNull(user.get().id, "User ID should not be null")

        // Authenticate the user
        val authRequest = AuthenticationRequest(
            email = registerRequest.email,
            password = registerRequest.password
        )

        val authResult = mockMvc.perform(
            post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authRequest))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.accessToken").exists())
            .andExpect(jsonPath("$.refreshToken").exists())
            .andExpect(jsonPath("$.email").value(registerRequest.email))
            .andReturn()

        // Extract tokens from authentication response
        val authResponse = objectMapper.readValue(
            authResult.response.contentAsString,
            AuthenticationResponse::class.java
        )

        // Refresh the token
        val refreshRequest = RefreshTokenRequest(
            refreshToken = authResponse.refreshToken
        )

        mockMvc.perform(
            post("/api/auth/refresh-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(refreshRequest))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.accessToken").exists())
            .andExpect(jsonPath("$.refreshToken").value(authResponse.refreshToken))
            .andExpect(jsonPath("$.email").value(registerRequest.email))

        // Logout
        mockMvc.perform(
            post("/api/auth/logout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(refreshRequest))
        )
            .andExpect(status().isOk)

        // Try to refresh token after logout (should fail)
        mockMvc.perform(
            post("/api/auth/refresh-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(refreshRequest))
        )
            .andExpect(status().isBadRequest)
    }
}
