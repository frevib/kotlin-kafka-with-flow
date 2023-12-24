package com.eventloopsoftware

import example.Cat
import io.confluent.kafka.serializers.KafkaAvroDeserializer
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig
import io.confluent.kafka.serializers.KafkaAvroSerializer
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import java.net.InetAddress
import java.time.Duration
import java.util.*

//import kotlin.time.Duration

class SomeConsumer {
//    https://gist.github.com/cyberdelia/1b9dfd259a64c0c3c50455587f79d41c

    fun run() {

        println("Starting consumer...")

        val topic = "topic_1"

        val props = Properties()
//        props["client.id"] = InetAddress.getLocalHost().hostName
        props["bootstrap.servers"] = "localhost:9092,localhost:9092"
//        props["acks"] = "all"
        props["max.block.ms"] = "2000"
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.qualifiedName
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = KafkaAvroDeserializer::class.qualifiedName
        props[KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG] = "http://localhost:8081"
        props[ConsumerConfig.GROUP_ID_CONFIG] = "group-1-A"

        val consumer = KafkaConsumer<String, Cat>(props).apply {
            subscribe(listOf(topic))
        }

        runBlocking {
            consumer
                .asFlow()
                .map { consumerRecord ->
                    val record = consumerRecord.value()
                    record.toString()
                }.collect { it -> println(it) }

        }
    }


    fun <K, V> KafkaConsumer<K, V>.asFlow(timeout: Duration = Duration.ofMillis(500)): Flow<ConsumerRecord<K, V>> =
        flow {
            poll(timeout).forEach { emit(it) }
        }

}
