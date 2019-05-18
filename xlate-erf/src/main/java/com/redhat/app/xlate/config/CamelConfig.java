package com.redhat.app.xlate.config;

import org.apache.camel.dataformat.soap.name.ServiceInterfaceStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sun.mdm.index.webservice.PersonEJB;

@Configuration
public class CamelConfig {

	@Bean("serviceStrategy")
	public ServiceInterfaceStrategy serviceStrategy() {
		
		return new ServiceInterfaceStrategy(PersonEJB.class, true);
		
	}
	
}
