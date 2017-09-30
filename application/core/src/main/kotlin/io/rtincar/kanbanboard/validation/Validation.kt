package io.rtincar.kanbanboard.validation

interface Validation<in T> {
    fun validate(o: T): ValidationResult
    data class ValidationResult(val valid: Boolean, val messages: Set<String>)
}

