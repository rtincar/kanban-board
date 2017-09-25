package io.rtincar.accont

interface AccountStore {

    fun save(account: Account)
    fun find(email: String): Account?

}