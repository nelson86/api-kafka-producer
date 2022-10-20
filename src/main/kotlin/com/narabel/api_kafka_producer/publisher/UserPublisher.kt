package com.narabel.api_kafka_producer.publisher

import com.narabel.api_kafka_producer.dto.KafkaMessage
import com.narabel.api_kafka_producer.user.User
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaProducerException
import org.springframework.kafka.core.KafkaSendCallback
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import org.springframework.util.concurrent.ListenableFuture

@Component
class UserPublisher(
    val producer: KafkaTemplate<String, KafkaMessage>
) {

    private val log = LoggerFactory.getLogger(javaClass)
    private val topic: String = "users.v1.created"

    fun send(key: String, user: User) {
        // asynchronous send: faster
        producer.send(topic, key, KafkaMessage("created", user))

        // synchronous send
        // producer.send(topic, key, KafkaMessage("created", user)).get()

        log.trace(user.toString())
    }

    fun sendWithCallbacks(key: String, user: User) {

        val future: ListenableFuture<SendResult<String, KafkaMessage>> = producer.send(topic, key, KafkaMessage("created", user))

        future.addCallback(object : KafkaSendCallback<String, KafkaMessage> {

            override fun onSuccess(result: SendResult<String, KafkaMessage>?) {
                log.info("Message sent")
            }

            override fun onFailure(ex: Throwable) {
                log.error("Error sending message ", ex)
            }

            override fun onFailure(ex: KafkaProducerException) {
                log.error("Error sending message ", ex)
            }
        })
    }
}
