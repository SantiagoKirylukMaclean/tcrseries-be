package com.puetsnao.tcrseries.infrastructure.security

import com.puetsnao.tcrseries.domain.model.Accesses
import io.jsonwebtoken.Claims
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.reflect.MethodSignature
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import jakarta.servlet.http.HttpServletRequest
import java.lang.reflect.Method
import java.util.function.Function

@ExtendWith(MockitoExtension::class)
class PermissionAspectTest {

    @Mock
    private lateinit var jwtService: JwtService

    @Mock
    private lateinit var joinPoint: ProceedingJoinPoint

    @Mock
    private lateinit var methodSignature: MethodSignature

    @Mock
    private lateinit var method: Method

    @Mock
    private lateinit var requestAttributes: ServletRequestAttributes

    @Mock
    private lateinit var request: HttpServletRequest

    @Mock
    private lateinit var claims: Claims

    @InjectMocks
    private lateinit var permissionAspect: PermissionAspect

    @BeforeEach
    fun setUp() {
        RequestContextHolder.setRequestAttributes(requestAttributes)
        `when`(requestAttributes.request).thenReturn(request)
        `when`(joinPoint.signature).thenReturn(methodSignature)
        `when`(methodSignature.method).thenReturn(method)
    }

    @Test
    fun `should proceed when user has required permission`() {
        // Given
        val requiredPermission = Accesses.STANDINGS
        val annotation = RequirePermission(requiredPermission)
        val token = "valid.jwt.token"
        val permissions = listOf("STANDINGS", "NOTES")

        `when`(method.getAnnotation(RequirePermission::class.java)).thenReturn(annotation)
        `when`(request.getHeader("Authorization")).thenReturn("Bearer $token")
        `when`(jwtService.extractClaim(token, Function { it })).thenReturn(claims)
        `when`(claims["permissions"]).thenReturn(permissions)
        `when`(joinPoint.proceed()).thenReturn("result")

        // When
        val result = permissionAspect.checkPermission(joinPoint)

        // Then
        verify(joinPoint).proceed()
        assert(result == "result")
    }

    @Test
    fun `should throw AccessDeniedException when user does not have required permission`() {
        // Given
        val requiredPermission = Accesses.STANDINGS
        val annotation = RequirePermission(requiredPermission)
        val token = "valid.jwt.token"
        val permissions = listOf("NOTES", "PARTS_LIST")

        `when`(method.getAnnotation(RequirePermission::class.java)).thenReturn(annotation)
        `when`(request.getHeader("Authorization")).thenReturn("Bearer $token")
        `when`(jwtService.extractClaim(token, Function { it })).thenReturn(claims)
        `when`(claims["permissions"]).thenReturn(permissions)

        // When/Then
        assertThrows<AccessDeniedException> {
            permissionAspect.checkPermission(joinPoint)
        }
    }

    @Test
    fun `should throw AccessDeniedException when Authorization header is missing`() {
        // Given
        val requiredPermission = Accesses.STANDINGS
        val annotation = RequirePermission(requiredPermission)

        `when`(method.getAnnotation(RequirePermission::class.java)).thenReturn(annotation)
        `when`(request.getHeader("Authorization")).thenReturn(null)

        // When/Then
        assertThrows<AccessDeniedException> {
            permissionAspect.checkPermission(joinPoint)
        }
    }

    @Test
    fun `should throw AccessDeniedException when Authorization header is invalid`() {
        // Given
        val requiredPermission = Accesses.STANDINGS
        val annotation = RequirePermission(requiredPermission)

        `when`(method.getAnnotation(RequirePermission::class.java)).thenReturn(annotation)
        `when`(request.getHeader("Authorization")).thenReturn("Invalid")

        // When/Then
        assertThrows<AccessDeniedException> {
            permissionAspect.checkPermission(joinPoint)
        }
    }
}