package io.rtincar.kanbanboard.core

import io.rtincar.kanbanboard.account.*
import io.rtincar.kanbanboard.validation.Validation
import spock.lang.Narrative
import spock.lang.Specification

@Narrative('''

 As User
 I want to create an account 
 To access the system
 
''')
class UserWantsToCreateAnAccountSpec extends Specification {

    def accountStore = Stub(AccountStore)
    def validation = Stub(Validation)
    def accountManager = new AccountManager(accountStore, validation)

    void "Should create new account and receive an activation email"() {

        given: "A valid account data"

        AccountData accountData = new AccountData(
                "account@domain.com",
                "First",
                "Last",
                "#R2mkdskds",
                "#R2mkdskds")


        accountStore.save(_) >> {
            def acc = accountData.toAccount()
            acc.id = UUID.randomUUID().toString()
            acc
        }


        validation.validate(accountData) >> { new Validation.ValidationResult(true, new HashSet<String>()) }



        when: "Try to create an account"

        def account = accountManager.createAccount(accountData)

        then: "An new inactive account is created with received data"

        account.email == accountData.email
        account.firstName == accountData.firstName
        account.lastName == accountData.lastName
        account.password.length() > 0
        account.id != null
        account.active == false


    }

    void "Should throw InvalidDataException when data account is not valid"() {

        given: "An invalid account"


        def accountData = new AccountData(
                "89sad@sds.csas",
                "s",
                "t",
                "dssd",
                "sd")

        validation.validate(accountData) >> { new Validation.ValidationResult(false, new HashSet<String>()) }

        when: "Try to create the account"

        accountManager.createAccount(accountData)

        then: "Should throw InvalidAccountException"

        thrown(InvalidAccountDataException)

    }

    void "Should throw DuplicateAccountException when exists an account with same email"() {
        given: "An AccountData with an email that already exists"

        AccountData accountData = new AccountData(
                "account@domain.com",
                "First",
                "Last",
                "#R2mkdskds",
                "#R2mkdskds")

        validation.validate(accountData) >> { new Validation.ValidationResult(true, new HashSet<String>()) }
        accountStore.save(_) >> { throw new DuplicateAccountException("") }

        when: "Try to create an account"

        accountManager.createAccount(accountData)


        then: "Should throw DuplicateAccountException"

        thrown(DuplicateAccountException)
    }
}
