package com.rad.statistics.rest.service.impl;

import javax.activity.InvalidActivityException;

import org.springframework.stereotype.Repository;

import com.rad.statistics.rest.models.TransactionModel;
import com.rad.statistics.rest.service.TransactionManagerService;
import com.rad.statistics.rest.service.utils.storage.impl.MemoryStorageProvider;
import com.rad.statistics.utils.StorageModel;



@Repository
public class TransactionManagerServiceImpl implements TransactionManagerService {

	private final MemoryStorageProvider<StorageModel> storageService;
	
	public TransactionManagerServiceImpl()
	{
		storageService=MemoryStorageProvider.init(()-> new StorageModel().builder());
	}

	@Override
	public void register(TransactionModel transaction) throws InvalidActivityException {
		storageService.update(new StorageModel().builder().register(transaction.getAmount(),transaction.getEntryTime()));
	}

	@Override
	public StorageModel getTransactionStatistics() {
		
		return storageService.reduce(StorageModel::merge);
	}

	@Override
	public void deleteAll() {
		storageService.clear();		
	}
}
