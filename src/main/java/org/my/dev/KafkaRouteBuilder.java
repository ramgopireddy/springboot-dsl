package org.my.dev;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KafkaRouteBuilder extends RouteBuilder {
    @Value("${kafka.topic.name}")
    private String topicName;
    
    @Value("${kafka.bootstrap.url}")
    private String bootstrapUrl;

    @Override
    public void configure() throws Exception {

        from("amq:incomingOrders?acknowledgementModeName=CLIENT_ACKNOWLEDGE")
        .setHeader(KafkaConstants.KEY, constant("Camel")) // Key of the message
        .setHeader(KafkaConstants.TOPIC, constant(topicName)) // Topic Name
        .log("${body}")
        .to("kafka:${topicName}?brokers=${bootstrapUrl}&requestRequiredAcks=all&synchronous=true");

    }
}
