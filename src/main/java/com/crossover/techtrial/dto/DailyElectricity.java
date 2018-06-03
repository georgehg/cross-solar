package com.crossover.techtrial.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * DailyElectricity class will hold sum, average,minimum and maximum electricity for a given day.
 * @author Crossover
 *
 */

public class DailyElectricity implements Serializable {
  
  private static final long serialVersionUID = 3605549122072628877L;

  private Date date;
  
  private Long sum;
  
  private Long average;
  
  private Long min;
  
  private Long max;

  public DailyElectricity(Date date, Long sum, Long average, Long min, Long max) {
	this.date = date;
	this.sum = sum;
	this.average = average;
	this.min = min;
	this.max = max;
}

public Date getDate() {
    return date;
  }

  public Long getSum() {
    return sum;
  }

  public Long getAverage() {
    return average;
  }

  public Long getMin() {
    return min;
  }

  public Long getMax() {
    return max;
  }
  
  @Override
  public String toString() {
    return "DailyElectricity [date=" + date + ", sum=" + sum + ", average="
        + average + ", min=" + min + ", max=" + max + "]";
  }

}
