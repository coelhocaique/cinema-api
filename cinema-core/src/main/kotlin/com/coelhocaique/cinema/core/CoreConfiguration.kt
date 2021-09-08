package com.coelhocaique.cinema.core

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@ComponentScan(basePackageClasses = [CoreConfiguration::class])
@PropertySource(value=["classpath:core-application.properties"])
class CoreConfiguration