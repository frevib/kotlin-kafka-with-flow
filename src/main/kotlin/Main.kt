package com.eventloopsoftware

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {

    val topic = "topic_1"
    val producer = SomeProducer(topic)
    producer.run()

    Thread.sleep(1000)

    val consumer = SomeConsumer(topic)
    consumer.run()


}