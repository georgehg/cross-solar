package com.crossover.techtrial.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.crossover.techtrial.dto.DailyElectricity;
import com.crossover.techtrial.model.HourlyElectricity;

/**
 * HourlyElectricityService interface for all services realted to HourlyElectricity.
 * @author Crossover
 *
 */
public interface HourlyElectricityService {
  HourlyElectricity save(HourlyElectricity hourlyElectricity);
  
  Page<HourlyElectricity> getAllHourlyElectricityByPanelId(Long panelId, Pageable pageable);
  
  List<DailyElectricity> getDailyElectricityByPanelId(Long panelId);
}
