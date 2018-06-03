package com.crossover.techtrial.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.crossover.techtrial.dto.DailyElectricity;
import com.crossover.techtrial.model.Panel;
import com.crossover.techtrial.service.HourlyElectricityService;
import com.crossover.techtrial.service.PanelService;

/**
 * PanelControllerTest class will test all APIs in PanelController.java.
 * 
 * @author Crossover
 *
 */

@RunWith(SpringRunner.class)
@WebMvcTest(PanelController.class)
@AutoConfigureJsonTesters
public class PanelControllerTest {

	@MockBean
	private PanelService panelService;

	@MockBean
	private HourlyElectricityService hourlyElectricityService;

	@Autowired
	private JacksonTester<Panel> json;

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Before
	public void setup() throws Exception {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	@Test
	public void testShouldFindPanel() throws Exception {
		// Arrange
		Panel resultEntity = Panel.of("1234567890123457", 25.543412, 26.098763, "Tesla");
		when(panelService.findBySerial(any())).thenReturn(resultEntity);

		// Act
		ResultActions result = this.mvc.perform(get("/api/panels/1234567890123457")
				.content(json.write(resultEntity).getJson()));

		// Assert
		result.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("serial", is("1234567890123457")))
				.andExpect(jsonPath("longitude", is(25.543412)))
				.andExpect(jsonPath("latitude", is(26.098763)))
				.andExpect(jsonPath("brand", is("Tesla")));
	}
	
	@Test
	public void testPanelShouldBeRegistered() throws Exception {
		// Arrange
		Panel resultEntity = Panel.of("1234567890123457", 25.543412, 26.098763, "Tesla");
		doNothing().when(panelService).register(any());

		// Act
		ResultActions result = this.mvc.perform(post("/api/panels/register").contentType(contentType)
				.content(json.write(resultEntity).getJson()));

		// Assert
		result.andExpect(status().isCreated())
			  .andExpect(header().string("Location", "http://localhost:8080/api/panels/1234567890123457"));
	}
	
	@Test
	public void testShouldFindDailyElectricit() throws Exception {
		// Arrange
		Panel panel = Panel.of("1234567890123457", 25.543412, 26.098763, "Tesla");
		DailyElectricity reading = new DailyElectricity(new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-03"), 30000L, 1000L, 500L, 4000L);
		List<DailyElectricity> readings = new ArrayList<>();
		readings.add(reading);
		
		when(panelService.findBySerial(any())).thenReturn(panel);
		when(hourlyElectricityService.getDailyElectricityByPanelId(any())).thenReturn(readings);

		// Act
		ResultActions result = this.mvc.perform(get("/api/panels/{panel-serial}/daily", "1234567890123457"));

		// Assert
		result.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("[*]", hasSize(1)))
				.andExpect(jsonPath("[0].sum", is(30000)))
				.andExpect(jsonPath("[0].average", is(1000)))
				.andExpect(jsonPath("[0].min", is(500)))
				.andExpect(jsonPath("[0].max", is(4000)));
	}
}
