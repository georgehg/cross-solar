package com.crossover.techtrial.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.crossover.techtrial.dto.DailyElectricity;
import com.crossover.techtrial.model.HourlyElectricity;

/**
 * HourlyElectricity Repository is for all operations for HourlyElectricity.
 * 
 * @author Crossover
 */
@RestResource(exported = false)
public interface HourlyElectricityRepository extends PagingAndSortingRepository<HourlyElectricity, Long> {
	Page<HourlyElectricity> findAllByPanelId(Long panelId, Pageable pageable);

	@Query(value = "SELECT new com.crossover.techtrial.dto.DailyElectricity("
			+ "cast(h.readingAt as date), sum(h.generatedElectricity), cast(ROUND(avg(h.generatedElectricity)) as java.lang.Long), min(h.generatedElectricity), "
			+ "max(h.generatedElectricity)) "
			+ "FROM HourlyElectricity h JOIN Panel p ON h.panel.id = p.id "
			+ "WHERE p.id = :panelId "
			+ "AND cast(h.readingAt as date) < current_date() "
			+ "GROUP BY cast(h.readingAt as date) "
			+ "ORDER BY cast(h.readingAt as date)")
	List<DailyElectricity> getDailyElectricity(Long panelId);

}
