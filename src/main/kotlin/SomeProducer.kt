package com.eventloopsoftware

//import example.Breed
import example.Breed
import example.Cat
import io.confluent.kafka.serializers.KafkaAvroSerializer
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig
import io.confluent.kafka.serializers.KafkaJsonDeserializerConfig
import io.confluent.kafka.serializers.KafkaJsonSerializer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.apache.kafka.common.serialization.StringSerializer
import java.net.InetAddress
import java.util.*


class SomeProducer {

    fun run() {
        val props = Properties()
        props["client.id"] = InetAddress.getLocalHost().hostName
        props["bootstrap.servers"] = "localhost:9092,localhost:9092"
        props["acks"] = "all"
        props["max.block.ms"] = "2000"
        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.qualifiedName
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = KafkaAvroSerializer::class.qualifiedName
        props[KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG] = "http://localhost:8081"
//        props[KafkaJsonDeserializerConfig.JSON_VALUE_TYPE] = Cat::class.java

        val topic = "topic_1"

        KafkaProducer<String, Cat>(props).use { producer ->
            repeat(2) { i ->
                val key = "monkey"
                val record = Cat(Breed.ABYSSINIAN)
                println("Producing record: $key\t$record")

                producer.send(ProducerRecord(topic, key, record)) { m: RecordMetadata, e: Exception? ->
                    when (e) {
                        // no exception, good to go!
                        null -> println("Produced record to topic ${m.topic()} partition [${m.partition()}] @ offset ${m.offset()}")
                        // print stacktrace in case of exception
                        else -> e.printStackTrace()
                    }
                }
            }

            producer.flush()
            println("10 messages were produced to topic $topic")
        }
    }
}