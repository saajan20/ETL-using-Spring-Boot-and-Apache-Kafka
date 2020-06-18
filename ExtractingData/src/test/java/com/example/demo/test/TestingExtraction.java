package com.example.demo.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;



@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestingExtraction {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void getBestEmployee() throws Exception{
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/")
				  .accept(MediaType.APPLICATION_JSON);
		          mockMvc.perform(requestBuilder)
			      .andExpect(MockMvcResultMatchers.status().isOk());
		
	}

}
