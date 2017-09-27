package io.rtincar.validation

interface Validatable<out T> {
    fun isValid(validation: Validation<T>): Validation.ValidationResult
}