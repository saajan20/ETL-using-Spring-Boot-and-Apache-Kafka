package com.kafka.transform;


import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.Produced;


public class TransformingData 
{
	
	 public Topology createTopology(){
		 
		 StreamsBuilder builder = new StreamsBuilder();
	        
	        KStream<String, String> textLines = builder.stream("source_topic");
	        
	        
	        //Performing required operation
	        
	       KStream<String, String> textWithResult = textLines.map(new KeyValueMapper<String,String, KeyValue<String,String>>(){

			@Override
			public KeyValue<String, String> apply(String key, String value) {
				
		
	            int res=0;
	        	
	        	String result = value;
	        	String[] fileData = result.split(",");
	        	String  operator = fileData[2];
	        	
	        	switch(operator) {
	        	
	        	case "+":res = Integer.parseInt(fileData[0]) + Integer.parseInt(fileData[1]);
	        	               break;
	        	               
	        	case "-":res = Integer.parseInt(fileData[0]) - Integer.parseInt(fileData[1]);
	                           break;
	                           
	        	case "*":res = Integer.parseInt(fileData[0]) * Integer.parseInt(fileData[1]);
	                           break;
	                           
	        	case "/":res = Integer.parseInt(fileData[0]) / Integer.parseInt(fileData[1]);
	                           break;
	        	
	        	}
	        	result = value + "," + res;
	        	value = new String(result);
				
				return new KeyValue<>(key,value);
				
			} 
			});
	    	
	 
	       textWithResult.to("target_topic", Produced.with(Serdes.String(), Serdes.String()));

	        return builder.build();
	    }
	
    public static void main( String[] args )
    {
    	
    	//Setting the properties
    	Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "data-transformation");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        
       TransformingData gettingBuild = new TransformingData();
       
       KafkaStreams streams = new KafkaStreams(gettingBuild.createTopology(), properties);
       streams.start();
       
       
       //printing the topology
       System.out.println(streams.toString());
       
       // shutdown hook to correctly close the stream application
       Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
 
    }
}
