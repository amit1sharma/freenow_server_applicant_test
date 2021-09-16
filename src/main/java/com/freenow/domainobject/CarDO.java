package com.freenow.domainobject;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.freenow.domainvalue.CarStatus;
import com.freenow.domainvalue.EngineType;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(
    name = "car",
    uniqueConstraints = @UniqueConstraint(name = "uc_licenseplate", columnNames = {"licenseplate"})
)
public class CarDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="car_id")
    private Long Id;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCreated = ZonedDateTime.now();

    @Column(nullable = false)
    @NotNull(message = "licensePlate field is mandatory.")
    @Size(min = 5, max = 10)
    private String licensePlate;

    @Column(nullable = false)
    @NotNull(message = "seatCount field is mandatory.")
    @Min(2)
    private Integer seatCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
	@NotNull(message = "carStatus field is mandatory.")
    private CarStatus carStatus;

    private Boolean convertible=false;
    
    @Column(nullable=true,scale=1)
    @Min(1) @Max(5)
    private BigDecimal rating=null;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EngineType engineType;

//    @Column(nullable = false)
    @NotNull(message="Car manufacturer cannot be null!")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "manu_id")
    private Manufacturer manufacturer;

    @Column(nullable = false)
    private Boolean deleted = false;

    @Column
    private long numberOfRating;

    @OneToOne(mappedBy = "carDO", fetch = FetchType.LAZY)
    private DriverExtendedDO driverExtendedDO;

    private CarDO(){
    }

	public CarDO(String licensePlate, int seatCount, EngineType engineType, Manufacturer manufacturer) {
		this.licensePlate = licensePlate;
		this.seatCount = seatCount;
		this.engineType = engineType;
		this.manufacturer = manufacturer;
		this.carStatus = CarStatus.UNMAP;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		this.Id = id;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public int getSeatCount() {
		return seatCount;
	}

	public CarStatus getCarStatus() {
		return carStatus;
	}

	public void setCarStatus(CarStatus carStatus) {
		this.carStatus = carStatus;
	}

	public Boolean getConvertible() {
		return convertible;
	}

	public void setConvertible(Boolean convertible) {
		this.convertible = convertible;
	}

	public BigDecimal getRating() {
		return rating;
	}

	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}

	public EngineType getEngineType() {
		return engineType;
	}

	public void setEngineType(EngineType engineType) {
		this.engineType = engineType;
	}

	public Manufacturer getManufacturer() {
		return manufacturer;
	}

    public Boolean getDeleted()
    {
        return deleted;
    }

    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }

	public Long getNumberOfRating() {
		return numberOfRating;
	}

	public void setNumberOfRating(Long numberOfRating) {
		this.numberOfRating = numberOfRating;
	}

	public DriverExtendedDO getDriverExtendedDO() {
		return driverExtendedDO;
	}

	public void setDriverExtendedDO(DriverExtendedDO driverExtendedDO) {
		this.driverExtendedDO = driverExtendedDO;
	}
}