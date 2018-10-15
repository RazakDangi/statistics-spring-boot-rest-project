package com.rad.statistics.rest.service;

import java.util.Optional;

import javax.activity.InvalidActivityException;

import com.rad.statistics.rest.models.TransactionModel;
import com.rad.statistics.utils.StorageModel;


public interface TransactionManagerService {

	 void register(TransactionModel transaction) throws InvalidActivityException;

	 StorageModel getTransactionStatistics();

	 void deleteAll();
}
