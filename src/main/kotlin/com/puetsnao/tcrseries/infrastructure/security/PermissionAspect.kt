package com.puetsnao.tcrseries.infrastructure.security

import io.jsonwebtoken.Claims
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

/**
 * Aspect to check if a user has the required permission to access an endpoint.
 */
@Aspect
@Component
class PermissionAspect(
    private val jwtService: JwtService
) {
    /**
     * Check if the user has the required permission before executing the method.
     */
    @Around("@annotation(com.puetsnao.tcrseries.infrastructure.security.RequirePermission)")
    fun checkPermission(joinPoint: ProceedingJoinPoint): Any {
        val methodSignature = joinPoint.signature as MethodSignature
        val method = methodSignature.method
        val requiredPermission = method.getAnnotation(RequirePermission::class.java).accesses
        
        // Get the current request
        val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request
        
        // Get the Authorization header
        val authHeader = request.getHeader("Authorization")
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw AccessDeniedException("Authorization header is missing or invalid")
        }
        
        // Extract the token
        val token = authHeader.substring(7)
        
        // Extract permissions from the token
        val permissions = extractPermissionsFromToken(token)
        
        // Check if the user has the required permission
        if (!permissions.contains(requiredPermission.name)) {
            throw AccessDeniedException("You don't have permission to access this resource")
        }
        
        // If the user has the required permission, proceed with the method execution
        return joinPoint.proceed()
    }
    
    /**
     * Extract permissions from the JWT token.
     */
    private fun extractPermissionsFromToken(token: String): List<String> {
        return try {
            val claims = jwtService.extractClaim(token) { obj: Claims -> obj }
            @Suppress("UNCHECKED_CAST")
            (claims["permissions"] as? List<String>) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}