package com.rad.statistics.rest.controller;

import java.time.Instant;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.MvcRequestMatcherProvider;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.AbstractJsonMarshalTester;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rad.statistics.rest.contoller.StatsController;
import com.rad.statistics.rest.service.TransactionManagerService;
import com.rad.statistics.utils.StorageModel;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=StatsController.class)
@ContextConfiguration(classes= {PersistanceContext.class})
public class StatsControllerTest {

	@Autowired
	TransactionManagerService trans;
	
		@Autowired
		StatsController statsController;
				
		MockMvc mockMvc;

		@Autowired
		private WebApplicationContext context;
		
		JacksonTester json;
		ObjectMapper objectMapper;
		@Before
		public void setUp(){
			  objectMapper=new ObjectMapper();

			mockMvc=MockMvcBuilders.webAppContextSetup(this.context).dispatchOptions(true).build();
		}
		@Test
		public void testController(){
			Assert.assertNotNull(statsController);
		}
		
		@Test
		public void testGetStats(){
		try {
		 	StorageModel storageModel=new StorageModel();
				storageModel.register(10.10, Instant.now().toEpochMilli());
				JacksonTester.initFields(this, objectMapper);
				
				
				mockMvc.perform(MockMvcRequestBuilders.post("/api/transactions")
						.content(json.write(storageModel).getJson())
						.contentType("application-json")).
				andExpect(MockMvcResultMatchers.status().isCreated());
				
			mockMvc.perform(MockMvcRequestBuilders.get("/api/statistics")
					)
			.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk());
		} catch (Exception e) {
			
		}
		}		

}
