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
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaProperties.bootstrapServers,
                // PROPIEDADES ADICIONALES
                // Define los reintentos que se realizarán en caso de error.
                ProducerConfig.RETRIES_CONFIG to 0,
                // El producer agrupará los registros en batches, mejorando el performance (está definido en bytes).
                ProducerConfig.BATCH_SIZE_CONFIG to 16384,
                // Los batches se agruparan de acuerdo de un periodo de tiempo, está definido en milisegundos
                ProducerConfig.LINGER_MS_CONFIG to 1,
                // Define el espacio de memoria que se asignará para colocar los mensajes que están pendientes por enviar
                ProducerConfig.BUFFER_MEMORY_CONFIG to 33554432
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
