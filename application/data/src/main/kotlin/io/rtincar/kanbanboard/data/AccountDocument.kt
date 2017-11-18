package io.rtincar.kanbanboard.data

import io.rtincar.kanbanboard.account.Account
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "accounts")
data class AccountDocument(

        @Id var id: String?,
        var email: String,
        var firstName: String,
        var lastName: String,
        var password: String,
        var active: Boolean = false
) {
    fun toAccount() = Account(id, email, firstName, lastName, password, active)

}