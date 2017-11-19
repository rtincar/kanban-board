package io.rtincar.kanbanboard.data

import io.rtincar.kanbanboard.account.Account
import io.rtincar.kanbanboard.data.accounts.AccountDocument

fun Account.toAccountDocument() = AccountDocument(this.id, this.email, this.firstName, this.lastName, this.password, this.active)