package com.mycompany;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class NewProcessorClass implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {
		Object body = exchange.getIn().getBody();
		String payload = exchange.getIn().getBody(String.class);
		// exchange.getIn().setBody("Changed body");
		System.out.println("BODY: " + payload);
	}

}
