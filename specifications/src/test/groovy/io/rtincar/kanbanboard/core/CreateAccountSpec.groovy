package io.rtincar.kanbanboard.core

import io.rtincar.kanbanboard.account.*
import io.rtincar.kanbanboard.validation.Validation
import spock.lang.Specification

class CreateAccountSpec extends Specification {

    void "Should create new account"() {

        given: "A valid account data"

        AccountStore accountStore = Stub()
        def accountData = new AccountData(
                "account@domain.com",
                "First",
                "Last",
                "#R2mkdskds",
                "#R2mkdskds")
        Validation<AccountData> validation = Stub()
        validation.validate(accountData) >> { new Validation.ValidationResult(true, new HashSet<String>()) }
        def accountManager = new AccountManager(accountStore, validation)

        when: "Try to create an account"

        def account = accountManager.createAccount(accountData)

        then: "An new inactive account is created with received data"

        account.email == accountData.email
        account.firstName == accountData.firstName
        account.lastName == accountData.lastName
        account.password.length() > 0
        account.active == false


    }

    void "Should throw InvalidDataException when data account is not valid"() {

        given: "An invalid account"

        AccountStore accountStore = Stub()
        def accountManager = new AccountManager(accountStore, new AccountDataValidation())
        def accountData = new AccountData("89sad@sds.csas", "s", "t", "dssd", "sd")

        when: "Try to create the account"

        accountManager.createAccount(accountData)

        then: "Should throw InvalidAccountException"

        thrown(InvalidAccountDataException)

    }

    void "Should throw DuplicateAccountException when exists an account with same email"() {
        given: "An AccountData with an email that already exists"

        AccountStore accountStore = new MockAccountStore()
        Validation<AccountData> validation = Stub()
        AccountData accountData = new AccountData("account@domain.com", "First", "Last", "#R2mkdskds", "#R2mkdskds")
        def accountManager = new AccountManager(accountStore, validation)
        validation.validate(accountData) >> { new Validation.ValidationResult(true, new HashSet<String>()) }
        accountManager.createAccount(accountData)

        when: "Try to create an account"

        accountManager.createAccount(accountData)
        accountManager.createAccount(accountData)

        then: "Should throw DuplicateAccountException"

        thrown(DuplicateAccountException)
    }
}
