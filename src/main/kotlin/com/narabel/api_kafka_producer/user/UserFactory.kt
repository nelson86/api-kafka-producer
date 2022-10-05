package com.narabel.api_kafka_producer.user

import io.github.serpro69.kfaker.Faker
import org.springframework.stereotype.Component

@Component
class UserFactory {

    private val faker = Faker()

    fun createUser(): User {
        return User(
            firstName = faker.name.firstName(),
            lastName = faker.name.lastName(),
            email = faker.internet.email(),
            phone = faker.phoneNumber.phoneNumber()
        )
    }
}
