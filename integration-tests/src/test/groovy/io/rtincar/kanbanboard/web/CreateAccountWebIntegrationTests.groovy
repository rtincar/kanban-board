package io.rtincar.kanbanboard.web

import groovy.json.JsonOutput
import io.rtincar.kanbanboard.account.Account
import io.rtincar.kanbanboard.configuration.AccountConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title

@Title("Create account")
@SpringBootTest(
        //webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = [AccountConfiguration]
        //properties = [ "spring.main.webApplicationType=reactive" ]
)

class CreateAccountWebIntegrationTests extends Specification {

    /*@LocalServerPort
    Integer port*/

    @Autowired
    RouterFunction<ServerResponse> accountRouter




    def "Should return code 201 when new account is created"() {
        given: "JSON account data"
        WebTestClient client = WebTestClient
                .bindToRouterFunction(accountRouter)
                .build()

        def jsonAccount = JsonOutput.toJson([a: 2, b: 'Data'])


        when: "User send account data"

        def exchange = client.post()
                .uri("/api/v1/account")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(jsonAccount))
                .exchange()

        then: "Should get created"

        exchange.expectStatus().isCreated()
        exchange.expectBody(String.class).returnResult().responseBody.contains('domain@account.com')

    }

    def "Should return code 400 and JSON error message when request is not correct"() {

    }
}
