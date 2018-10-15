package com.rad.statistics.rest.controller.common;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=MainController.class)
public class MainControllerTest {
	
	@Autowired
	MainController mainController;
			
	MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;
	
	@Before
	public void setUp(){
		mockMvc=MockMvcBuilders.webAppContextSetup(this.context).dispatchOptions(true).build();
	}
	@Test
	public void testController(){
		Assert.assertNotNull(mainController);
	}
	
	@Test
	public void testGetBaseUri(){
	try {
		mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(MockMvcResultMatchers.status().isOk());
	} catch (Exception e) {
		
	}	
		Assert.assertEquals("hello", mainController.getBaseURI());
	}

}
