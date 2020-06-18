package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import com.example.demo.service.LoadingService;

@Controller
public class LoadingController {
	
	@Autowired
	private LoadingService service;
	
	
	@KafkaListener(topics = "target_topic")
	public void listen(String message) {
		
		System.out.println(message);
	    service.loadDataToDB(message);
	    
	}

}
