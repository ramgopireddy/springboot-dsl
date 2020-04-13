package org.my.dev;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.springframework.stereotype.Component;

@Component
public class KafkaRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("direct:start")
        .setBody(constant("Message from Camel"))          // Message to send
        .setHeader(KafkaConstants.KEY, constant("Camel")) // Key of the message
        .to("kafka:sampleTopic?brokers=localhost:9092");

        from("kafka:sampleTopic?brokers=localhost:9092&groupId=1234")
        .log("Message received from Kafka : ${body}")
        .log("    on the topic ${headers[kafka.TOPIC]}")
        .log("    on the partition ${headers[kafka.PARTITION]}")
        .log("    with the offset ${headers[kafka.OFFSET]}")
        .log("    with the key ${headers[kafka.KEY]}");

    }
}
