package com.mycompany;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;



public class CamelRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		from("direct:firstApi")
		.setHeader(Exchange.HTTP_METHOD, simple("GET"))
		.to("http://maps.googleapis.com/maps/api/geocode/json?address=stockholm,sweden")
		.log("HEADER JSON: ${headers}")
		.to("direct:Orders");

		
		from("direct:secondApi")
		.setHeader(Exchange.HTTP_METHOD, simple("GET"))
		.to("http://data.stockholm.se/set/Befolkning/Arbetslosa?$filter=AREA_CODE%20eq%20%27SDO21%27%20and%20KONK_TEXT%20eq%20%27kvinnor%27%20and%20YEAR%20eq%20%272013%27&apikey=L4529E30G1I67B96C1R140PC88K10528")
		//.to("http://gturnquist-quoters.cfapps.io/api/random")
		.log("HEADER XML: ${headers}")
		.to("direct:Orders");

		
		from("direct:Orders")
		.choice()
		.when(header("Content-Type").isEqualTo("application/xml")) 
	    .to("file:XmlApi");
//	    .otherwise()
//        .to("file:JsonApi");				
		
		from("file:XmlApi")
		.to("file:Marshal");
		
//		from("direct:marshal")
//		.marshal().xmljson()
//      .to("file:Converted");
		
		from("direct:APIstart")
		.aggregate(header("HEADERID"), new AgrregationClass())
		.completionSize(2)
        .to("file:Output?fileName=Response.txt");
		
		
		
		/* ------------------------------------------------------------------*/ 
		/*
		from("file:Output")
		//.unmarshal().xmljson()
		.to("file:Endpoint");
		*/
		/* ------------------------------------------------------------------*/ 
		
		
	}
}
