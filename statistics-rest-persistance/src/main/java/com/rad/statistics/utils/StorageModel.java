package com.rad.statistics.utils;

import java.util.function.Consumer;


public class StorageModel implements Storage,Comparable<StorageModel>{

	private int count;
	private double sum;
	private double max;
	private double min;
	
    private double amount;
	
	private long timestamp;
		
	public StorageModel(int count, double sum, double max, double min, double amount, long timestamp) {
		super();
		this.count = count;
		this.sum = sum;
		this.max = max;
		this.min = min;
		this.amount = amount;
		this.timestamp = timestamp;
	}

	public StorageModel() {
		// TODO Auto-generated constructor stub
	}

	public double getAmount() {
		return amount;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public int getCount() {
		return count;
	}

	public double getSum() {
	
		return sum;
	}

	public double getMax() {
				return max;
	}

	
	public double getMin() {
		
		return min;
	}	
	
	public StorageModel register(double amount,long timeStamp) {
		return merge(new StorageModelBuilder()
		.count(1)
        .sum(amount)
        .max(amount)
        .min(amount)
        .timestamp(timeStamp)
        .createStorageObject());}
	
	 public StorageModel builder() {
	       return new StorageModelBuilder()
	    		   .count(0)
	               .sum(0.0)
	               .max(Double.NaN)
	               .min(Double.NaN)
	               .createStorageObject();
	    }
	            
		
	public  StorageModel merge(StorageModel that) {
	        if (this.equals(builder())) {
	            return that;
	        }
	        if (that.equals(builder())) {
	            return this;
	        }
	        else if(that !=null)
	        return new StorageModelBuilder()
	                .count(this.getCount() + that.getCount())
	                .sum(this.getSum() + that.getSum())
	                .min(Math.min(this.getMin(), that.getMin()))
	                .max(Math.max(this.getMax(), that.getMax()))
	                .createStorageObject();
			return that;
	    }
		
	
	public class StorageModelBuilder {
		
		private int count;
		private double sum;
		private double max;
		private double min;

		private double amount;

		private long timestamp;
		public StorageModelBuilder with(
	            Consumer<StorageModelBuilder> builderFunction) {
	        builderFunction.accept(this);
	        return this;
	    }
		public StorageModelBuilder timestamp(long timeStamp2) {
			this.timestamp=timeStamp2;
			return this;
		}
		public StorageModel createStorageObject() {
	        return new StorageModel(count, sum, max,
	        		min,amount, timestamp);
	    }		
		
		public StorageModelBuilder count(int count) {
			this.count=count;
			return this;
		}
		
		public StorageModelBuilder sum(double sum) {
			this.sum=sum;
			return this;
		}
		public StorageModelBuilder max(double max) {
			this.max=max;
			return this;
		}
		public StorageModelBuilder min(double min) {
			this.min=min;
			return this;
		}	
	
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + count;
		temp = Double.doubleToLongBits(sum);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StorageModel other = (StorageModel) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (count != other.count)
			return false;
		if (Double.doubleToLongBits(sum) != Double.doubleToLongBits(other.sum))
			return false;
		if (timestamp != other.timestamp)
			return false;
		return true;
	}

	@Override
	public int compareTo(StorageModel o) {
		Long current=this.getTimestamp();
		Long others=o.getTimestamp();
		return current.compareTo(others);
	}
}
