package com.crossover.techtrial.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Panel class hold information related to a Solar panel.
 * 
 * @author Crossover
 *
 */
@Entity
@Table(name = "panel")
public class Panel implements Serializable {

	private static final long serialVersionUID = -8527695980909864257L;

	@Id
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@NotNull
	@Column(name = "serial")
	String serial;

	@NotNull
	@Column(name = "longitude")
	Double longitude;

	@NotNull
	@Column(name = "latitude")
	Double latitude;

	@Nullable
	@Column(name = "brand")
	String brand;
	
	protected Panel() {
		this(null, null, null, null);
	}
	
	private Panel(@NotNull String serial, @NotNull Double longitude, @NotNull Double latitude, String brand) {
		this.serial = serial;
		this.longitude = longitude;
		this.latitude = latitude;
		this.brand = brand;
	}
	
	public static Panel of(String serial, Double longitude, Double latitude, String brand) {
		return new Panel(serial, longitude, latitude, brand);
	}

	public Long getId() {
		return id;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	/*
	 * Id, Serial and Brand are only fields required to uniquely identify a
	 * Panel
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((brand == null) ? 0 : brand.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((serial == null) ? 0 : serial.hashCode());
		return result;
	}

	/*
	 * Id, Serial and Brand are only fields required to uniquely identify a
	 * Panel
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Panel other = (Panel) obj;
		if (brand == null) {
			if (other.brand != null) {
				return false;
			}
		} else if (!brand.equals(other.brand)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (serial == null) {
			if (other.serial != null) {
				return false;
			}
		} else if (!serial.equals(other.serial)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Panel [id=" + id + ", serial=" + serial + ", longitude=" + longitude + ", latitude=" + latitude
				+ ", brand=" + brand + "]";
	}
}
