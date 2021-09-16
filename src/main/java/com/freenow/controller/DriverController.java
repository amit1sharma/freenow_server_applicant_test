package com.freenow.controller;

import com.freenow.controller.mapper.DriverMapper;
import com.freenow.datatransferobject.DriverDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.CarStatus;
import com.freenow.domainvalue.OnlineStatus;
import com.freenow.exception.CarAlreadyInUseException;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.exception.ProhibitedOperationException;
import com.freenow.service.car.CarService;
import com.freenow.service.driver.CarMapperDriverService;
import com.freenow.service.driver.DriverService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * All operations with a driver will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("v1/drivers")
public class DriverController
{

    @Autowired
    private CarMapperDriverService carMapperDriverService;
    private final DriverService driverService;
    @Autowired
    private CarService carService;


    @Autowired
    public DriverController(final DriverService driverService)
    {
        this.driverService = driverService;
    }


    @GetMapping("/{driverId}")
    public DriverDTO getDriver(@PathVariable long driverId) throws EntityNotFoundException
    {
        return DriverMapper.makeDriverDTO(driverService.find(driverId));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DriverDTO createDriver(@Valid @RequestBody DriverDTO driverDTO) throws ConstraintsViolationException
    {
        DriverDO driverDO = DriverMapper.makeDriverDO(driverDTO);
        return DriverMapper.makeDriverDTO(driverService.create(driverDO));
    }


    @DeleteMapping("/{driverId}")
    public void deleteDriver(@PathVariable long driverId) throws EntityNotFoundException
    {
        driverService.delete(driverId);
    }


    @PutMapping("/{driverId}")
    public void updateLocation(
        @PathVariable long driverId, @RequestParam double longitude, @RequestParam double latitude)
        throws EntityNotFoundException
    {
        driverService.updateLocation(driverId, longitude, latitude);
    }


    @GetMapping
    public List<DriverDTO> findDrivers(@RequestParam OnlineStatus onlineStatus)
    {
        List<DriverDTO> lst = DriverMapper.makeDriverDTOList(driverService.find(onlineStatus));
        return lst;
    }
    @PatchMapping("/{driverId}")
    public ResponseEntity<DriverDTO> mapUnMapCar(@Valid @PathVariable long driverId, @RequestParam long carId, @RequestParam CarStatus action)
            throws ConstraintsViolationException, EntityNotFoundException, CarAlreadyInUseException, ProhibitedOperationException
    {
        DriverDO driverDO=null;
        if(action==CarStatus.MAP)
            driverDO = carMapperDriverService.mapCar(driverId, carId);
        else
            driverDO= carMapperDriverService.unMapCar(driverId, carId);
        return new ResponseEntity<>(DriverMapper.makeDriverDTO(driverDO), HttpStatus.ACCEPTED);
    }

}
