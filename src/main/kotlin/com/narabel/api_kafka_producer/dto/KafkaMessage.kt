package com.narabel.api_kafka_producer.dto

data class KafkaMessage(
    val action: String,
    val data: Any
)
