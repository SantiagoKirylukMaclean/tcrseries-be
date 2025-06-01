package com.puetsnao.tcrseries.infrastructure.security

import com.puetsnao.tcrseries.domain.model.Accesses
import kotlin.annotation.AnnotationRetention
import kotlin.annotation.AnnotationTarget

/**
 * Annotation to require a specific feature permission to access an endpoint.
 * 
 * @param accesses The feature that requires permission
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequirePermission(val accesses: Accesses)