package com.rad.statistics.rest.service.utils.storage.impl;

import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.SECONDS;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.activity.InvalidActivityException;

import com.rad.statistics.rest.models.StatisticsModel;
import com.rad.statistics.rest.service.utils.storage.MemoryStorageManager;
import com.rad.statistics.utils.ConcurrentBoundedBufferArray;
import com.rad.statistics.utils.StorageModel;

public class MemoryStorageProvider<T> implements MemoryStorageManager<T> {

	private ConcurrentBoundedBufferArray<StorageModel> bufferStorage;
	private Supplier<T> factory;
	private Supplier<Long> now;

	public MemoryStorageProvider(int size, Supplier<Long> now, Supplier<T> factory) {
		this.bufferStorage = new ConcurrentBoundedBufferArray<>(size);
		this.now = now;
		this.factory = factory;
	}

	public static <T> MemoryStorageProvider<T> init(final Supplier<T> supplier) {
		return new MemoryStorageProvider<T>(60, System::currentTimeMillis, supplier);
	}

	@Override
	public void update( StorageModel storageModel) throws InvalidActivityException {
	    bufferStorage.add(storageModel);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T reduce(final BinaryOperator<T> reducer) {	
		if(!bufferStorage.isEmpty())
		return  (T) Arrays.stream(bufferStorage.toArray()).filter(Objects::nonNull).map(e->(T)e)
					.reduce(reducer)
					.get();
		return null;
	}

	public void clear() {
		bufferStorage.clear();
		
	}

}
