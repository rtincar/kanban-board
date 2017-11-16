package io.rtincar.kanbanboard.account
// TODO: rename
interface IAccountManager {

    fun createAccount(data: AccountData): Account
}