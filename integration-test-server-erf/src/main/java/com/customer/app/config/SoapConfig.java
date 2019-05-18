package com.customer.app.config;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.customer.app.test.PersonEJBImpl;
import com.sun.mdm.index.webservice.PersonEJB;

@Configuration
public class SoapConfig {

	@Bean
	public ServletRegistrationBean cxfDispatcherServlet() {
		return new ServletRegistrationBean(new CXFServlet(), "/soap/*");
	}

	@Bean(name = Bus.DEFAULT_BUS_ID)
	public SpringBus springBus() {
		SpringBus springBus = new SpringBus();
		return springBus;
	}
	
	@Bean
	public PersonEJB personEJB() {
		return new PersonEJBImpl();
	}
	
	@Bean
	public Endpoint personEJBService() {
		EndpointImpl endpoint = new EndpointImpl(springBus(), personEJB());
		endpoint.getFeatures().add(new LoggingFeature());
		endpoint.publish("/PersonEJBService/PersonEJB");
		return endpoint;
	}

	
}
