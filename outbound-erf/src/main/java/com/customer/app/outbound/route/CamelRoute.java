package com.customer.app.outbound.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.model.dataformat.SoapJaxbDataFormat;
import org.apache.camel.spi.DataFormat;
import org.springframework.stereotype.Component;

@Component
public class CamelRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		DataFormat jaxb = new JaxbDataFormat("com.sun.mdm.index.webservice");
		SoapJaxbDataFormat soapJaxb = new SoapJaxbDataFormat("com.sun.mdm.index.webservice");
		
		from("jms:queue:q.empi.nextgate.out")
			.log("Received from queue:q.empi.nextgate.out: \n$ {body}")
			.unmarshal(jaxb)
			.marshal(soapJaxb)
			.to("cxf://{{nextgate.url}}?serviceClass=com.sun.mdm.index.webservice.PersonEJB&dataFormat=MESSAGE")
			.log("Sent to {{nextgate.url}} with response \n ${body}");
			
	}

	
	
}
