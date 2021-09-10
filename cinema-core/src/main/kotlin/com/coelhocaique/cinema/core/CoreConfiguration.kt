package com.coelhocaique.cinema.core

import com.coelhocaique.cinema.core.persistance.MovieRepository
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@Configuration
@ComponentScan(basePackageClasses = [CoreConfiguration::class])
@PropertySource(value=["classpath:core-application.properties"])
@EnableReactiveMongoRepositories(basePackageClasses = [MovieRepository::class])
@EnableCaching
class CoreConfiguration