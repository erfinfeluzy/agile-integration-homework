package com.redhat.usecase.junit;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

import com.customer.app.response.ESBResponse;

public class CamelRouteTest {

	@Test
	public void restStatusCodeTest() throws ClientProtocolException, IOException {
		
		// Given
	    HttpUriRequest request = new HttpGet( "http://localhost:9098/cxf/demos/match");
	 
	    // When
	    HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );
	 
	    // Then
	    assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
		
	}
	
	@Test
	public void restPayloadTest() throws ClientProtocolException, IOException, JAXBException {
		
		// Given
		HttpPost request = new HttpPost( "http://localhost:9098/cxf/demos/match");
	    
	    request.addHeader("Content-Type", "application/xml");
	    String xmlPayload = "<p:Person xmlns:p=\"http://www.app.customer.com\"\n" + 
	    		"  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" + 
	    		"  xsi:schemaLocation=\"http://www.app.customer.com PatientDemographics.xsd \">\n" + 
	    		"  <p:age>30</p:age>\n" + 
	    		"  <p:legalname>\n" + 
	    		"    <p:given>First</p:given>\n" + 
	    		"    <p:family>Last</p:family>\n" + 
	    		"  </p:legalname>\n" + 
	    		"  <p:fathername>Dad</p:fathername>\n" + 
	    		"  <p:mothername>Mom</p:mothername>\n" + 
	    		"  <p:gender xsi:type=\"p:Code\">\n" + 
	    		"    <p:code>Male</p:code>\n" + 
	    		"  </p:gender>\n" + 
	    		"</p:Person>";
	    
	    StringEntity entity = new StringEntity(xmlPayload);
	    request.setEntity(entity);
	 
	    // When
	    HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );
	    
	    ResponseHandler<String> handler = new BasicResponseHandler();
	 
	    // Then
	    String expected = "DONE";
	    
	    assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
	    
	    
	    JAXBContext jaxbContext = JAXBContext.newInstance(ESBResponse.class);
	    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	    
	    StringReader reader = new StringReader(handler.handleResponse(httpResponse));
	    
	    ESBResponse esbResp = (ESBResponse) unmarshaller.unmarshal(reader);
	    
	    //check <comment>
	    assertEquals(expected, esbResp.getComment());
		
	}
	
}
