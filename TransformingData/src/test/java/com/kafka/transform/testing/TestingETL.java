package com.kafka.transform.testing;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.TopologyTestDriver;
import org.apache.kafka.streams.test.ConsumerRecordFactory;
import org.apache.kafka.streams.test.OutputVerifier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.kafka.transform.TransformingData;



public class TestingETL {

	TopologyTestDriver testDriver;
	String Example;

    StringSerializer stringSerializer = new StringSerializer();
    
    ConsumerRecordFactory<String, String> recordFactory =
            new ConsumerRecordFactory<>(stringSerializer, stringSerializer);  //For providing the data to source_topic
    
    @Before
    public void setUpTopologyTestDriver(){
    	
    	//Creation of Topology for Testing
    
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "test");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:1234");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

       
        TransformingData gettingBuild = new TransformingData();
        testDriver = new TopologyTestDriver( gettingBuild.createTopology() , config);
       
    }
    
    @After
    public void closeTestDriver(){
    	
    	
    	//Closing the Driver after testing
    	 	
    	try{
            testDriver.close();
        }catch(Exception  exception){
            try {
				FileUtils.deleteDirectory(new File("\\tmp\\kafka-streams\\test\\0_0"));
			} catch (IOException e) {
				
				e.printStackTrace();
			} //there is a bug on Windows that does not delete the state directory properly. In order for the test to pass, the directory must be deleted manually
        }
    }
    
    public void pushNewInputRecord(String value){
    	
        testDriver.pipeInput(recordFactory.create("source_topic", "key" , value));//piping data to source_topic
    }

    public ProducerRecord<String, String> readOutput(){
    	
    return testDriver.readOutput("target_topic", new StringDeserializer(), new StringDeserializer());//pipes data to target_topic
    
    }

    @Test
	public void makeSureTranformationIsDone_1() {
    	
    	Example = "5,6,*";
    	pushNewInputRecord(Example);
    	OutputVerifier.compareKeyValue(readOutput(), "key", "5,6,*,30"); // For Verifying the output
		
	}
    
    @Test
   	public void makeSureTranformationIsDone_2() {
       	
       	String Example = "5,6,+";
       	pushNewInputRecord(Example);
       	OutputVerifier.compareKeyValue(readOutput(), "key", "5,6,+,11");
   		
   	}
    
    @Test
   	public void makeSureTranformationIsDone_3() {
       	
       	String Example = "9,6,-";
       	pushNewInputRecord(Example);
       	OutputVerifier.compareKeyValue(readOutput(), "key", "9,6,-,3");
   		
   	}
    
    @Test
   	public void makeSureTranformationIsDone_4() {
       	
       	String Example = "9,3,/";
       	pushNewInputRecord(Example);
       	OutputVerifier.compareKeyValue(readOutput(), "key", "9,3,/,3");
   		
   	}
	
}
