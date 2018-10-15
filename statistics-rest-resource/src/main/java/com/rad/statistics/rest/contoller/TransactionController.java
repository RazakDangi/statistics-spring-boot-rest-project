package com.rad.statistics.rest.contoller;

import javax.activity.InvalidActivityException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rad.statistics.rest.models.TransactionModel;
import com.rad.statistics.rest.service.TransactionManagerService;

@RestController
@Component
public class TransactionController {

	private static final Logger log=LoggerFactory.getLogger(TransactionController.class);
	
	@Autowired
	private TransactionManagerService transactionManagerService;
	
	@PostMapping(path="/transactions",consumes="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public void addTranaction(@RequestBody TransactionModel transaction) throws InvalidActivityException{
		transactionManagerService.register(transaction);
		
	}
	
	@DeleteMapping(path="/transactions")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteTranactions() {
		transactionManagerService.deleteAll();
	}
	
}
