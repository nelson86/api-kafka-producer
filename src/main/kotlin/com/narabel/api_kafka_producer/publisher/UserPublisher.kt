package com.narabel.api_kafka_producer.publisher

import com.narabel.api_kafka_producer.dto.KafkaMessage
import com.narabel.api_kafka_producer.user.User
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class UserPublisher(
    val producer: KafkaTemplate<String, KafkaMessage>
) {

    private val log = LoggerFactory.getLogger(javaClass)
    private val topic: String = "users.v1.created"

    fun send(user: User) {
        val response = producer.send(topic, KafkaMessage("created", user))
        log.trace(user.toString())
    }
}
