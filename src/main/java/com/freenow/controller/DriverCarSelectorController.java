package com.freenow.controller;

import com.freenow.controller.mapper.DriverMapper;
import com.freenow.datatransferobject.DriverDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainobject.DriverExtendedDO;
import com.freenow.domainvalue.CarStatus;
import com.freenow.exception.CarAlreadyInUseException;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.exception.ProhibitedOperationException;
import com.freenow.service.car.CarService;
import com.freenow.service.driver.CarMapperDriverService;
import com.freenow.service.driver.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/drivers")
public class DriverCarSelectorController extends DriverController{

    private final CarMapperDriverService carMapperDriverService;
    private final DriverService driverService;

    @Autowired
    private CarService carService;

    public DriverCarSelectorController(DriverService driverService, CarMapperDriverService carMapperDriverService) {
        super(driverService);
        this.driverService = driverService;
        this.carMapperDriverService = carMapperDriverService;
    }

    @PatchMapping("/{driverId}")
    public ResponseEntity<DriverDTO> mapUnMapCar(@Valid @PathVariable long driverId, @RequestParam long carId, @RequestParam CarStatus action)
            throws ConstraintsViolationException, EntityNotFoundException, CarAlreadyInUseException, ProhibitedOperationException
    {
        DriverExtendedDO driverDO=carMapperDriverService.findById(driverId);
        CarDO carDO=carService.find(carId);
        driverDO.setCarDO(carDO);
        if(action==CarStatus.MAP)
            driverDO = carMapperDriverService.mapCar(driverDO, carDO);
        else
            driverDO=carMapperDriverService.unMapCar(driverDO, carDO);
        return new ResponseEntity<>(DriverMapper.makeDriverDTO(driverDO), HttpStatus.ACCEPTED);
    }
}
