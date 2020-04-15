package org.my.dev;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.springframework.stereotype.Component;

@Component
public class KafkaRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("amq:incomingOrders")
        .setHeader(KafkaConstants.KEY, constant("Camel")) // Key of the message
        .setHeader(KafkaConstants.TOPIC, constant("incomingOrderskafka")) // Topic Name
        .log("${body}")
        .to("kafka:incomingOrderskafka?brokers=fis-cluster-kafka-bootstrap:9092&requestRequiredAcks=all&synchronous=true");

    }
}
