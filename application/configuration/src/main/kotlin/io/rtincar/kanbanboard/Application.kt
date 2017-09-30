package io.rtincar.kanbanboard

import io.rtincar.kanbanboard.account.Account
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application {

    @Bean
    fun getAccount() : Account =
        Account("", "", "", "")

}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}