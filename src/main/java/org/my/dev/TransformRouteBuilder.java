package org.my.dev;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.XmlJsonDataFormat;


public class TransformRouteBuilder extends RouteBuilder {
	
	
	//add the XmlJsonDataFormat 
	XmlJsonDataFormat xmlJson = new XmlJsonDataFormat();
	
	@Override
	public void configure() throws Exception {
				
		from("amq:queue:orderInput?username=admin&password=admin")
		.log("receive message from amq")
		.marshal().jaxb()
		.log("XML Body: ${body}")
		//Marshal JSON
		.marshal(xmlJson)
		.log("JSON Body: ${body}")
		//Filter JSON
		.filter().jsonpath("$[?(@.delivered != 'true')]")
		//wire tap
		.wireTap("direct:jsonOrderLog")
		.to("mock:fulfillmentSystem");		
		//add direct route to mock order log end point
		from("direct:jsonOrderLog")
			.startupOrder(1)
			.log("Order received: ${body}")
			.to("mock:orderLog");
	}

}
