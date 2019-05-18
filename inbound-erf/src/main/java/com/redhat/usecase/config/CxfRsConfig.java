package com.redhat.usecase.config;

import java.util.Arrays;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.redhat.usecase.service.DEIMService;
import com.redhat.usecase.service.impl.DEIMServiceImpl;

@Configuration
public class CxfRsConfig {

	@Bean
	public DEIMService deimService() {
		return new DEIMServiceImpl();
	}
	
	@Bean(name = Bus.DEFAULT_BUS_ID)
	public SpringBus springBus() {
		SpringBus springBus = new SpringBus();
		return springBus;
	}
	
	@Bean
    public Server rsServer() {
        JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
        endpoint.setBus(springBus());
        endpoint.setAddress("http://localhost:9098/cxf/demos");
        endpoint.setServiceBeans(Arrays.<Object>asList(deimService()));

        return endpoint.create();
    }   
}
