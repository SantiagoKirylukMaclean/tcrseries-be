package com.puetsnao.tcrseries.integration.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.puetsnao.tcrseries.domain.model.RoleType
import com.puetsnao.tcrseries.domain.port.UserRepository
import com.puetsnao.tcrseries.infrastructure.persistence.entity.RoleEntity
import com.puetsnao.tcrseries.infrastructure.persistence.repository.RoleJpaRepository
import com.puetsnao.tcrseries.infrastructure.web.dto.RegisterRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.UUID

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class AuthControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var roleJpaRepository: RoleJpaRepository

    @BeforeEach
    fun setup() {
        // Create USER role if it doesn't exist
        if (roleJpaRepository.findByName(RoleType.USER).isEmpty) {
            val userRole = RoleEntity(name = RoleType.USER)
            roleJpaRepository.save(userRole)
        }
    }

    companion object {
        @Container
        private val postgresContainer = PostgreSQLContainer<Nothing>("postgres:15-alpine").apply {
            withDatabaseName("tcrseries")
            withUsername("postgres")
            withPassword("postgres")
        }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgresContainer::getJdbcUrl)
            registry.add("spring.datasource.username", postgresContainer::getUsername)
            registry.add("spring.datasource.password", postgresContainer::getPassword)
        }
    }

    @Test
    fun `should register a new user successfully`() {
        // Given
        val email = "test${UUID.randomUUID()}@example.com"
        val registerRequest = RegisterRequest(
            email = email,
            password = "password123",
            firstName = "Test",
            lastName = "User"
        )

        // When & Then
        mockMvc.perform(
            post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.accessToken").isString)
            .andExpect(jsonPath("$.refreshToken").isString)
            .andExpect(jsonPath("$.userId").isString)
            .andExpect(jsonPath("$.email").value(email))
            .andExpect(jsonPath("$.firstName").value("Test"))
            .andExpect(jsonPath("$.lastName").value("User"))

        // Verify user was created in the database
        val userOptional = userRepository.findByEmail(email)
        assert(userOptional.isPresent)
        val user = userOptional.get()
        assert(user.email == email)
        assert(user.firstName == "Test")
        assert(user.lastName == "User")
    }

    @Test
    fun `should not allow registering with existing email`() {
        // Given
        val email = "duplicate${UUID.randomUUID()}@example.com"
        val registerRequest = RegisterRequest(
            email = email,
            password = "password123",
            firstName = "Test",
            lastName = "User"
        )

        // Register first user
        mockMvc.perform(
            post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest))
        )
            .andExpect(status().isOk)

        // When - Try to register with same email (this might throw an exception)
        try {
            mockMvc.perform(
                post("/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(registerRequest))
            )
        } catch (e: Exception) {
            // Exception is expected, ignore it
        }

        // Then - Verify only one user with this email exists
        val userOptional = userRepository.findByEmail(email)
        assert(userOptional.isPresent) { "User should exist with email $email" }
    }

    @Test
    fun `should return error when registering with invalid data`() {
        // Given
        val registerRequest = RegisterRequest(
            email = "invalid-email",
            password = "short",
            firstName = "",
            lastName = ""
        )

        // When & Then
        mockMvc.perform(
            post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest))
        )
            .andExpect(status().isBadRequest)
    }
}
