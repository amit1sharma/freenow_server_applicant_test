package com.freenow.datatransferobject;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.freenow.domainvalue.EngineType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDTO
{
    @JsonIgnore
    private Long id;

    @NotNull(message = "licensePlate field is mandatory")
    private String licensePlate;

    @NotNull(message = "seatCount field is mandatory")
    private int seatCount;

    @NotNull(message = "engineType field is mandatory.")
    private EngineType engineType;
    
    @NotNull(message = "manufacturer field is mandatory.")
    private Long manufacturerId;

    private BigDecimal rating=null;
    
    private Boolean convertible=false;

    private CarDTO()
    {
    }

	public CarDTO(Long id, String licensePlate, int seatCount, EngineType engineType, Long manufacturer, BigDecimal rating, Boolean convertible) {
		this.id = id;
		this.licensePlate = licensePlate;
		this.seatCount = seatCount;
		this.engineType = engineType;
		this.manufacturerId = manufacturer;
		this.rating = rating;
		this.convertible = convertible;
	}

    @JsonProperty
    public Long getId()
    {
        return id;
    }

    public String getLicensePlate() {
		return licensePlate;
	}

	public int getSeatCount() {
		return seatCount;
	}

	public EngineType getEngineType() {
		return engineType;
	}

	public Long getManufacturerId() {
		return manufacturerId;
	}

	public void setManufacturerId(Long manufacturerId) {
		this.manufacturerId = manufacturerId;
	}

	public BigDecimal getRating() {
		return rating;
	}

	public Boolean getConvertible() {
		return convertible;
	}

	public static CarDTOBuilder newBuilder()
	{
		return new CarDTOBuilder();
	}

	public static class CarDTOBuilder
	{
		private Long id;
		private String licensePlate;
		private int seatCount;
		private EngineType engineType;
		private Long manufacturerId;
		private BigDecimal rating;
		private Boolean convertible;


		public CarDTOBuilder setId(Long id)
		{
			this.id = id;
			return this;
		}

		public CarDTOBuilder setLicensePlate(String licensePlate) {
			this.licensePlate = licensePlate;
			return this;
		}

		public CarDTOBuilder setSeatCount(int seatCount) {
			this.seatCount = seatCount;
			return this;
		}

		public CarDTOBuilder setEngineType(EngineType engineType) {
			this.engineType = engineType;
			return this;
		}

		public CarDTOBuilder setManufacturer(Long manufacturerId) {
			this.manufacturerId = manufacturerId;
			return this;
		}

		public CarDTOBuilder setRating(BigDecimal rating) {
			this.rating = rating;
			return this;
		}

		public CarDTOBuilder setConvertible(Boolean convertible) {
			this.convertible = convertible;
			return this;
		}

		public CarDTO createCarDTO() {
			return new CarDTO(id, licensePlate, seatCount, engineType, manufacturerId, rating, convertible);
		}

	}

}