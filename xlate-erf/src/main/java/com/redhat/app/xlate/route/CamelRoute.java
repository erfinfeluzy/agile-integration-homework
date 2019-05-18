package com.redhat.app.xlate.route;

import org.apache.camel.TypeConversionException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.model.dataformat.SoapJaxbDataFormat;
import org.apache.camel.spi.DataFormat;
import org.springframework.stereotype.Component;

import com.customer.app.Person;
import com.sun.mdm.index.webservice.ExecuteMatchUpdate;

@Component
public class CamelRoute extends RouteBuilder {
	

	@Override
	public void configure() throws Exception {
		
		DataFormat jaxb = new JaxbDataFormat("com.customer.app");
		SoapJaxbDataFormat soapJaxb = new SoapJaxbDataFormat("com.sun.mdm.index.webservice", "serviceStrategy");
		
		//enable auto-scan typeConverter
		getContext().setTypeConverterStatisticsEnabled(true);
		
		onException(TypeConversionException.class)
			.redeliveryDelay(0)
			.maximumRedeliveries(3)
			.to("jms:queue:q.empi.transform.dlq");
		
		from("jms:queue:q.empi.deim.in")
			.convertBodyTo(Person.class)
			.convertBodyTo(ExecuteMatchUpdate.class)
			.convertBodyTo(String.class)
			.to("jms:queue:q.empi.nextgate.out?exchangePattern=InOnly");
		
		
	}

}
