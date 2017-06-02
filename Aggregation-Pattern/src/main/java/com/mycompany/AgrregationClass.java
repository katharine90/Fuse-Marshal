package com.mycompany;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class AgrregationClass implements AggregationStrategy{

	@Override
	public Exchange aggregate(Exchange orginal, Exchange resource) {

			if (orginal == null) {
				return resource;
			}
		String oldBody = orginal.getIn().getBody(String.class);
	    String newBody =  resource.getIn().getBody(String.class);
	    String body = oldBody + newBody;
        orginal.getIn().setBody(body);
        
		return orginal;
	}

}
