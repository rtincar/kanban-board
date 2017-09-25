package io.rtincar.account

data class Account(val email: String, val firstName: String, val lastName: String, val password: String, val active: Boolean = false)