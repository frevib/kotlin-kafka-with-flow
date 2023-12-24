package com.eventloopsoftware

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

    Thread.sleep(1000)

    val consumer = SomeConsumer()
    consumer.run()


}