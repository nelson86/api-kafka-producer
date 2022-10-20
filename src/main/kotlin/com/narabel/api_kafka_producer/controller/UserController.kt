package com.narabel.api_kafka_producer.controller

import com.narabel.api_kafka_producer.publisher.UserPublisher
import com.narabel.api_kafka_producer.user.UserFactory
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/users")
class UserController(
    private val userFactory: UserFactory,
    private val userPublisher: UserPublisher
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("/migrate/{size}")
    fun show(@PathVariable size: Int) {

        repeat(size) {
            val key = if (it % 2 == 0) "key_even" else "key_odd"
            userPublisher.send(
                key, userFactory.createUser()
            )
        }
        log.info("Se han enviado $size eventos de usuario creado")
    }
}
