package com.crossover.techtrial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crossover.techtrial.exceptions.PanelNotFoundException;
import com.crossover.techtrial.model.Panel;
import com.crossover.techtrial.repository.PanelRepository;


/**
 * PanelServiceImpl for panel related handling.
 * @author Crossover
 *
 */
@Service
public class PanelServiceImpl implements PanelService {

  @Autowired
  PanelRepository panelRepository;
  
  /* (non-Javadoc)
   * @see com.crossover.techtrial.service.PanelService#register(com.crossover.techtrial.model.Panel)
   */
  
  @Override
  public void register(Panel panel) { 
    panelRepository.save(panel);
  }
  
  public Panel findBySerial(String serial) throws PanelNotFoundException {
	  return panelRepository.findBySerial(serial)
			  				.orElseThrow(() -> new PanelNotFoundException("Panel not found with serial " + serial));
  }
}
