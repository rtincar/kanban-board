package io.rtincar.kanbanboard.configuration

import io.rtincar.kanbanboard.web.StaticResourcesRouter
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.config.EnableWebFlux

@EnableWebFlux
class WebConfiguration {

    @Bean
    fun staticRouter() = StaticResourcesRouter().staticRouter()

}