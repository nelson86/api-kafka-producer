package com.narabel.api_kafka_producer.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.narabel.api_kafka_producer.dto.KafkaMessage
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer

@Configuration
class KafkaProducerConfig(
    private val objectMapper: ObjectMapper
) {

    companion object {
        fun producerProps(kafkaProperties: KafkaProperties): Map<String, Any> {
            return mapOf(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaProperties.bootstrapServers
            )
        }
    }

    @Bean
    fun producerFactory(kafkaProperties: KafkaProperties): ProducerFactory<String, KafkaMessage> {
        val keySerializer = StringSerializer()
        val valueSerializer = JsonSerializer<KafkaMessage>(objectMapper).noTypeInfo()
        val configs = producerProps(kafkaProperties)

        return DefaultKafkaProducerFactory(configs, keySerializer, valueSerializer)
    }

    @Bean
    fun kafkaTemplate(factory: ProducerFactory<String, KafkaMessage>): KafkaTemplate<String, KafkaMessage> {
        return KafkaTemplate(factory)
    }
}
