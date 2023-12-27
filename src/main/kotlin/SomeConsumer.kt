package com.eventloopsoftware

import example.Cat
import io.confluent.kafka.serializers.KafkaAvroDeserializer
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import java.time.Duration
import java.util.*
import kotlin.random.Random


class SomeConsumer {

    fun run() {

        println("Starting consumer...")

        val topic = "topic_1"

        val props = Properties()
        props["bootstrap.servers"] = "localhost:9092"
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.qualifiedName
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = KafkaAvroDeserializer::class.qualifiedName
        props[KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG] = "http://localhost:8081"
        props[ConsumerConfig.GROUP_ID_CONFIG] = "ConsumerGroup1"
        // use SpecificRecord and not GenericRecord
        props[KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG] = "true"

        val consumer = KafkaConsumer<String, Cat>(props).apply {
            subscribe(listOf(topic))
        }

        runBlocking {
            consumer
                .asFlow()
                .onEach { println("consuming.... $it") }
                .map(processKafkaMessage())
                .collect { message ->
                    println(message)
                }

            delay(30000)
        }

        println("after join")

    }

    // lambda with generic type variable is not possible in Kotlin. Use fun to return a lambda
    fun <K, V> processKafkaMessage(): suspend (ConsumerRecord<K, V>) -> V = { record -> record.value() }

    fun <K, V> KafkaConsumer<K, V>.asFlow(timeout: Duration = Duration.ofMillis(500)): Flow<ConsumerRecord<K, V>> =
        flow {
            use {
                while (true) {
                    println("debug polling...${Random.nextInt()}")
                    poll(timeout)
                        .forEach { emit(it) }
                }
            }
        }

}
