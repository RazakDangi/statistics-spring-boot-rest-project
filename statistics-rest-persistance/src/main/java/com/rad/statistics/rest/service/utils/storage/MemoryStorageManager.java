package com.rad.statistics.rest.service.utils.storage;

import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import javax.activity.InvalidActivityException;

import com.rad.statistics.utils.StorageModel;

public interface MemoryStorageManager<T> {
	 void update(StorageModel updater) throws InvalidActivityException;

	 T reduce(BinaryOperator<T> reducer);
}
