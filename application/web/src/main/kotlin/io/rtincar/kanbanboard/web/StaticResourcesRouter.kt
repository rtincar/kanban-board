package io.rtincar.kanbanboard.web

import org.springframework.core.io.ClassPathResource
import org.springframework.web.reactive.function.server.router

class StaticResourcesRouter {

    fun staticRouter() = router {
        resources("/**", ClassPathResource("static/"))
    }
}