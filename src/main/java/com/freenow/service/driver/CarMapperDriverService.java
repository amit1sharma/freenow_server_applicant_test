package com.freenow.service.driver;

import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;
import com.freenow.exception.CarAlreadyInUseException;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.exception.ProhibitedOperationException;
import org.springframework.transaction.annotation.Transactional;

public interface CarMapperDriverService extends DriverService {

    @Transactional
    DriverDO mapCar(Long driverId, Long carId) throws CarAlreadyInUseException, ProhibitedOperationException, EntityNotFoundException;

    @Transactional
    DriverDO unMapCar(Long driverId, Long carId) throws ProhibitedOperationException, EntityNotFoundException;
}
