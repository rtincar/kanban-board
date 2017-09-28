package io.rtincar.kanbanboard.validation

interface Validatable<out T> {
    fun isValid(validation: Validation<T>): Validation.ValidationResult
}