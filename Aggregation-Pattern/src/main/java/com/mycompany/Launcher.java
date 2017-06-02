package com.mycompany;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.main.Main;
import org.apache.camel.util.ExchangeHelper;

public class Launcher {

    public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new CamelRoute());
		
		context.start();		
		Thread.sleep(5000);
		ProducerTemplate template = context.createProducerTemplate();
		
		Object Api1 = template.requestBody("direct:firstApi", null, String.class);
		Object Api2 = template.requestBody("direct:secondApi", null, String.class);
		Exchange exchange = new DefaultExchange(context);

		String response1 = ExchangeHelper.convertToType(exchange, String.class, Api1); 
		String response2 = ExchangeHelper.convertToType(exchange, String.class, Api2); 
		//System.out.println("RESPONSE: " + response1 + response2);
		
		template.sendBodyAndHeader("direct:APIstart", response1, "HEADERID", 1);
		template.sendBodyAndHeader("direct:APIstart", response2, "HEADERID", 1);
		
		//Object a = template.requestBody("direct:Orders", new NewProcessorClass());
		//template.sendBody("direct:Orders", new NewProcessorClass());
		
		
		/* ------------------------------------------------------------------*/
		
		// template.sendBody("file:Output");
		
		/* ------------------------------------------------------------------*/
			
		context.stop();
    }
}
