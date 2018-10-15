package com.rad.statistics.rest.controller;

import java.time.Instant;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rad.statistics.rest.contoller.TransactionController;
import com.rad.statistics.rest.controller.common.MainController;
import com.rad.statistics.rest.service.TransactionManagerService;
import com.rad.statistics.utils.StorageModel;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=TransactionController.class)
@ContextConfiguration(classes= {PersistanceContext.class})
public class TransactionControllerTest {
	
	@Autowired
	TransactionManagerService trans;
	
	@Autowired
	TransactionController mainController;
			
	MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;
	
	private ObjectMapper objectMapper;
	
	 JacksonTester<StorageModel> json;
	@Before
	public void setUp(){
		objectMapper=new ObjectMapper();
		mockMvc=MockMvcBuilders.webAppContextSetup(this.context).dispatchOptions(true).build();
	}
	@Test
	public void testController(){
		Assert.assertNotNull(mainController);
	}
	
	@Test
	public void putStats(){
	try {
		StorageModel storageModel=new StorageModel();
		storageModel.register(10.10, Instant.now().toEpochMilli());
		JacksonTester.initFields(this, objectMapper);
		
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/transactions")
				.content(json.write(storageModel).getJson())
				.contentType("application-json")).
		andExpect(MockMvcResultMatchers.status().isCreated());
	} catch (Exception e) {
		
	}	
}


}
