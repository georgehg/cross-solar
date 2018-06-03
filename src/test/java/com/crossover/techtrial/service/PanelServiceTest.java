package com.crossover.techtrial.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import javax.validation.ConstraintViolationException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.crossover.techtrial.config.ServiceConfig;
import com.crossover.techtrial.exceptions.PanelNotFoundException;
import com.crossover.techtrial.model.Panel;
import com.crossover.techtrial.repository.PanelRepository;


@RunWith(SpringRunner.class)
@DataJpaTest
@Import(ServiceConfig.class)
public class PanelServiceTest {
	
	@Autowired
	private PanelService panelService;
	
	@Autowired
	private PanelRepository panelRepository;
	
	@Before
	@Sql("/delete_readings.sql")
	public void setup() {}
	
	@Test
	public void shouldRegisterPanel() throws PanelNotFoundException {
		panelService.register(Panel.of("1234567890123457", 25.543412, 26.098763, "Tesla"));
		Panel newPanel = panelRepository.findBySerial("1234567890123457").get();
		
		assertThat(newPanel).isNotNull();
		assertThat(newPanel.getId()).isNotNull();
		assertThat(newPanel.getBrand()).isEqualTo("Tesla");
	}
	
	@Test
	public void shouldFindPanel() throws PanelNotFoundException {
		panelRepository.save(Panel.of("1234567890123458", 29.543412, 32.098763, "3M"));
		Panel newPanel = panelService.findBySerial("1234567890123458");
		
		assertThat(newPanel).isNotNull();
		assertThat(newPanel.getBrand()).isEqualTo("3M");
	}
	
	@Test
	public void shouldIssueErroRegisterPanel_WithNullSerial() throws PanelNotFoundException {		
		assertThatThrownBy(() ->
				panelService.register(Panel.of(null, 25.543412, 26.098763, "Tesla")))
		.isInstanceOf(ConstraintViolationException.class);
	}
	
	@Test
	public void shouldIssueErroRegisterPanel_WithNullLongitude() throws PanelNotFoundException {		
		assertThatThrownBy(() ->
				panelService.register(Panel.of("1234567890123459", null, 26.098763, "Tesla")))
		.isInstanceOf(ConstraintViolationException.class);
	}

}
