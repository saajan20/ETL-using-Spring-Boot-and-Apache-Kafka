package com.example.demo.testing;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.model.MathOperation;
import com.example.demo.repository.LoadingRepository;
import com.example.demo.service.LoadingService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestingLoadingApplication {
	
	
	@InjectMocks
	LoadingService service;
	
	@Mock
	private LoadingRepository repo;
	
	
	@Test
	public void testWhetherDataisLoaded() {
	
		String data = "6,6,+,12";
	    service.loadDataToDB(data);
	    MathOperation objectData = new MathOperation("6","6","+","12");
	    verify(repo, times(1)).save(objectData);
		
	}
	

}
