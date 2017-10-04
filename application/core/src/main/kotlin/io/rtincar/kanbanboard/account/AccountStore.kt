package io.rtincar.kanbanboard.account

interface AccountStore {

    fun save(account: Account): Account
    fun find(email: String): Account?
    fun exists(email: String): Boolean

}