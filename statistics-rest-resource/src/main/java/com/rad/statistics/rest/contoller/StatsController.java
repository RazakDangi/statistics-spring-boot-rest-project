package com.rad.statistics.rest.contoller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rad.statistics.rest.models.StatisticsModel;
import com.rad.statistics.rest.models.TransactionModel;
import com.rad.statistics.rest.service.TransactionManagerService;
import com.rad.statistics.utils.StorageModel;

@RestController
@Component
public class StatsController {

	private static final Logger log = LoggerFactory.getLogger(TransactionController.class);

	@Autowired
	private TransactionManagerService transactionManagerService;
	
	@Autowired
	Scheduler schedular;

	@GetMapping(path = "/api/statistics", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public StatisticsModel getTranactions() {
		StorageModel storageModel = transactionManagerService.getTransactionStatistics();
		return getTransactionModel(storageModel);

	}

	private StatisticsModel getTransactionModel(StorageModel storageModel) {
		StatisticsModel model=null;
		if(storageModel!=null)
		{
		 model = new StatisticsModel();
		model.setCount(storageModel.getCount());
		model.setMax(storageModel.getMax());
		model.setMin(storageModel.getMin());
		model.setSum(storageModel.getSum());
		model.setAvg(storageModel.getSum()/storageModel.getCount());
		return model;
		}
		
		return new StatisticsModel();
	}

}
