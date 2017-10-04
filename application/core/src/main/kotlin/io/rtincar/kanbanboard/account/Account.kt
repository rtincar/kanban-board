package io.rtincar.kanbanboard.account

class Account(
    var id: String?,
    var email: String,
    var firstName: String,
    var lastName: String,
    var password: String,
    var active: Boolean = false)