package com.rad.statistics.rest;

import org.junit.Test;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import static java.util.concurrent.TimeUnit.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;


public class TestRestTime {

	
	class BeeperControl {
	    private final ScheduledExecutorService scheduler =
	        Executors.newScheduledThreadPool(1);

	    public void beepForAnHour() {
	        final Runnable beeper = new Runnable() {
	            public void run() { System.out.println("beep"); }
	        };
	        final ScheduledFuture<?> beeperHandle =
	            scheduler.scheduleAtFixedRate(beeper, 61, 61, SECONDS);
	        scheduler.schedule(new Runnable() {
	            public void run() { beeperHandle.cancel(true); }
	        }, 60 * 60, SECONDS);
	    } 
	}
	@Test
	public void name() {
		
		System.out.println();
	}
}
