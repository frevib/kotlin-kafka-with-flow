package com.eventloopsoftware

import example.Cat
import io.confluent.kafka.serializers.KafkaAvroDeserializer
import io.confluent.kafka.serializers.KafkaAvroSerializer
import io.confluent.kafka.serializers.KafkaJsonDeserializer
import org.apache.kafka.clients.consumer.ConsumerConfig.*
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
//import org.apache.kafka.common.serialization.StringDeserializer
import java.util.*

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {

    val props: Properties = Properties();


//    props[KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name
//    props[VALUE_DESERIALIZER_CLASS_CONFIG] = KafkaAvroDeserializer::class.java.name
////    props[JSON_VALUE_TYPE] = DataRecord::class.java
//    props[GROUP_ID_CONFIG] = "kotlin_example_group_1"
//    props[AUTO_OFFSET_RESET_CONFIG] = "earliest"

//    val topic = "topic_1"

    val producer = SomeProducer()
    producer.run()

//    val consumer = KafkaConsumer<String, Cat>(props).apply {
//        subscribe(listOf(topic))
//    }
}