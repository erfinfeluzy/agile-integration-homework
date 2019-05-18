package com.redhat.usecase.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.spi.DataFormat;
import org.springframework.stereotype.Component;

@Component
public class CamelRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		
		DataFormat jaxb = new JaxbDataFormat("com.customer.app");
		
		from("direct:integrateRoute")
			.log("request body :\n ${body}")
			.convertBodyTo(String.class)
			.to("jms:queue:q.empi.deim.in?exchangePattern=InOnly")
			.setBody(constant(2));

		
	}

}
