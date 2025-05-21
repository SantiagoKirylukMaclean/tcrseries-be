package com.puetsnao.tcrseries.infrastructure.web.controller

import com.puetsnao.tcrseries.domain.port.AuthenticationService
import com.puetsnao.tcrseries.infrastructure.web.dto.AuthenticationRequest
import com.puetsnao.tcrseries.infrastructure.web.dto.AuthenticationResponse
import com.puetsnao.tcrseries.infrastructure.web.dto.RefreshTokenRequest
import com.puetsnao.tcrseries.infrastructure.web.dto.RegisterRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Authentication management API")
class AuthController(
    private val authenticationService: AuthenticationService
) {

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a new user account")
    fun register(@Valid @RequestBody request: RegisterRequest): ResponseEntity<AuthenticationResponse> {
        val user = authenticationService.register(
            email = request.email,
            password = request.password,
            firstName = request.firstName,
            lastName = request.lastName
        )
        
        val authResult = authenticationService.authenticate(
            email = request.email,
            password = request.password
        )
        
        return ResponseEntity.ok(
            AuthenticationResponse(
                accessToken = authResult.accessToken,
                refreshToken = authResult.refreshToken,
                userId = user.id,
                email = user.email,
                firstName = user.firstName,
                lastName = user.lastName
            )
        )
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user", description = "Authenticates a user and returns tokens")
    fun authenticate(@Valid @RequestBody request: AuthenticationRequest): ResponseEntity<AuthenticationResponse> {
        val authResult = authenticationService.authenticate(
            email = request.email,
            password = request.password
        )
        
        return ResponseEntity.ok(
            AuthenticationResponse(
                accessToken = authResult.accessToken,
                refreshToken = authResult.refreshToken,
                userId = authResult.user.id,
                email = authResult.user.email,
                firstName = authResult.user.firstName,
                lastName = authResult.user.lastName
            )
        )
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "Refresh token", description = "Refreshes the access token using a valid refresh token")
    fun refreshToken(@Valid @RequestBody request: RefreshTokenRequest): ResponseEntity<AuthenticationResponse> {
        val authResult = authenticationService.refreshToken(request.refreshToken)
        
        return ResponseEntity.ok(
            AuthenticationResponse(
                accessToken = authResult.accessToken,
                refreshToken = authResult.refreshToken,
                userId = authResult.user.id,
                email = authResult.user.email,
                firstName = authResult.user.firstName,
                lastName = authResult.user.lastName
            )
        )
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout", description = "Invalidates the refresh token")
    fun logout(@Valid @RequestBody request: RefreshTokenRequest): ResponseEntity<Void> {
        authenticationService.logout(request.refreshToken)
        return ResponseEntity.ok().build()
    }
}