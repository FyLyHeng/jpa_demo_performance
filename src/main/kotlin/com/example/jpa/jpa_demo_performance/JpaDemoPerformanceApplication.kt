package com.example.jpa.jpa_demo_performance

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.transaction.annotation.EnableTransactionManagement
import th.co.geniustree.springdata.jpa.repository.support.JpaSpecificationExecutorWithProjectionImpl

@EnableJpaRepositories(repositoryBaseClass = JpaSpecificationExecutorWithProjectionImpl::class)
@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
class JpaDemoPerformanceApplication
fun main(args: Array<String>) {
	runApplication<JpaDemoPerformanceApplication>(*args)
}
