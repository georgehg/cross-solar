package com.crossover.techtrial.config;

import org.springframework.context.annotation.Bean;

import com.crossover.techtrial.service.HourlyElectricityService;
import com.crossover.techtrial.service.HourlyElectricityServiceImpl;
import com.crossover.techtrial.service.PanelService;
import com.crossover.techtrial.service.PanelServiceImpl;

public class ServiceConfig {
	
	@Bean
	public PanelService getPanelService() {
		return new PanelServiceImpl();
	}
	
	@Bean
	public HourlyElectricityService getHourlyElectricityService() {
		return new HourlyElectricityServiceImpl();
	}

}
