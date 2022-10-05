package com.narabel.api_kafka_producer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ApiKafkaProducerApplication

fun main(args: Array<String>) {
    runApplication<ApiKafkaProducerApplication>(*args)
}
