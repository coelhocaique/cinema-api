package com.coelhocaique.cinema.api

import com.coelhocaique.cinema.core.CoreConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.PropertySource

@SpringBootApplication
@ComponentScan(basePackageClasses = [ApiStarter::class])
@Import(CoreConfiguration::class)
@PropertySource(value=["classpath:api-application.properties"])
class ApiStarter

fun main(args: Array<String>) {
	runApplication<ApiStarter>(*args)
}
