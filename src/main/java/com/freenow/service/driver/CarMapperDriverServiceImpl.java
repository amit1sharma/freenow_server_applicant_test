package com.freenow.service.driver;

import com.freenow.dataaccessobject.CarRepository;
import com.freenow.dataaccessobject.DriverRepository;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.CarStatus;
import com.freenow.domainvalue.OnlineStatus;
import com.freenow.exception.CarAlreadyInUseException;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.exception.ErrorCode;
import com.freenow.exception.ProhibitedOperationException;
import com.freenow.service.car.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("carMapperDriverService")
public class CarMapperDriverServiceImpl extends DefaultDriverService implements CarMapperDriverService {

	@Autowired
	private CarService carService;
	private final DriverRepository driverRepository;
	private final CarRepository carRepository;

	public CarMapperDriverServiceImpl(DriverRepository driverRepository, CarRepository carRepository) {
		super(driverRepository);
		this.driverRepository = driverRepository;
		this.carRepository = carRepository;
	}


	/**
     * Map driver with a car.
     *
     * @param driverId
     * @param carId
	 * @throws CarAlreadyInUseException 
	 * @throws ProhibitedOperationException 
     */
	@Override
	@Transactional
	public DriverDO mapCar(Long driverId, Long carId) throws CarAlreadyInUseException, ProhibitedOperationException, EntityNotFoundException {
		DriverDO driverDO=find(driverId);
		CarDO carDO=carService.find(carId);
			if(driverDO.getOnlineStatus()== OnlineStatus.OFFLINE){
				throw new ProhibitedOperationException("Please be online in order to map a car.", ErrorCode.OFFLINEDRIVER);
			}else if(carDO.getCarStatus()== CarStatus.MAP){
				throw new CarAlreadyInUseException(carDO.getId()+", Already in Use.");
			}else{
				driverDO.setCarDO(carDO);
				carDO.setCarStatus(CarStatus.MAP);
			}
		return driverDO;
	}
	
	/**
     * Un-Map driver with a car.
     *
     * @param driverId
     * @param carId
     * @return driverDO
	 * @throws ProhibitedOperationException 
     */
	@Override
	@Transactional
	public DriverDO unMapCar(Long driverId, Long carId) throws ProhibitedOperationException, EntityNotFoundException {
		DriverDO driverDO=find(driverId);
		CarDO carDO=carService.find(carId);
		if(driverDO.getCarDO()!=null && driverDO.getCarDO().getId()==carDO.getId()){
			driverDO.setCarDO(null);
			carDO.setCarStatus(CarStatus.UNMAP);
		}else{
			throw new ProhibitedOperationException("Car with id-"+carDO.getId()+", Not mapped to you.",ErrorCode.NOTMAPPED);
		}
		return driverDO;
	}


}