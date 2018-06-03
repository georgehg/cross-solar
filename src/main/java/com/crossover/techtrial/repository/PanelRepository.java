package com.crossover.techtrial.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.crossover.techtrial.model.Panel;
/**
 * PanelRepository allows all operations to Panel Entity.
 * @author Crossover
 *
 */

@RestResource(exported = false)
public interface PanelRepository extends PagingAndSortingRepository<Panel, Long> {
	Optional<Panel> findBySerial(String serial);
}
