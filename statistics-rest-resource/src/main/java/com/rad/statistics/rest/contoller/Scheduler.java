package com.rad.statistics.rest.contoller;

import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class Scheduler {

	@Bean
	@Scheduled(fixedRate=6000)
	public static Supplier<Long> setCurrentTime(){
		return System::currentTimeMillis;
	}
	
}
