package com.crossover.techtrial.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.crossover.techtrial.config.ServiceConfig;
import com.crossover.techtrial.dto.DailyElectricity;
import com.crossover.techtrial.model.HourlyElectricity;
import com.crossover.techtrial.model.Panel;
import com.crossover.techtrial.repository.PanelRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(ServiceConfig.class)
public class HourlyElectricityServiceTest {
		
	@Autowired
	private HourlyElectricityService hourlyElectricityService;
	
	@Autowired
	private PanelRepository panelRepository;
	
	@Before
	@Sql("/delete_readings.sql")
	public void setup() {}
	
	@Test
	public void shouldSavePanelReading() {
		Panel newPanel = Panel.of("1234567890123457", 25.543412, 26.098763, "Tesla");
		panelRepository.save(newPanel);
		
		HourlyElectricity reading = hourlyElectricityService.save(HourlyElectricity.of(newPanel, 1000L, LocalDateTime.parse("2018-05-03T08:00:00")));
		
		assertThat(reading).isNotNull();
		assertThat(reading.getPanel()).isEqualTo(newPanel);
		assertThat(reading.getGeneratedElectricity()).isEqualTo(1000L);
		assertThat(reading.getReadingAt()).isEqualTo(LocalDateTime.parse("2018-05-03T08:00:00"));
	}
	
	@Test
	@Sql("/insert_panel_readings.sql")
	public void shouldGetHourlyElectricityByPanelId() {
		Panel newPanel = panelRepository.findBySerial("1234567890123457").get();
		assertThat(newPanel).isNotNull();
		
		List<HourlyElectricity> readings = hourlyElectricityService.getAllHourlyElectricityByPanelId(newPanel.getId(), Pageable.unpaged()).getContent();
		assertThat(readings).isNotEmpty();
		assertThat(readings.get(0).getPanel()).isEqualTo(newPanel);		
	}
	
	@Test
	@Sql("/insert_panel_readings.sql")
	public void shouldGeDailyElectricityByPanelId() {
		Panel newPanel = panelRepository.findBySerial("1234567890123457").get();
		assertThat(newPanel).isNotNull();	
		
		List<DailyElectricity> readings = hourlyElectricityService.getDailyElectricityByPanelId(newPanel.getId());
		assertThat(readings).isNotEmpty();
	}

}
