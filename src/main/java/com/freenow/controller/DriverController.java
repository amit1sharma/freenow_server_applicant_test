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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.validation.Valid;

import com.freenow.specification.car.CarSpecificationBuilder;
import com.freenow.specification.driver.DriverSpecificationBuilder;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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


    /**
     * for searching by online status
     * @param onlineStatus
     * @return
     */
    @GetMapping
    public List<DriverDTO> findDrivers(@RequestParam OnlineStatus onlineStatus)
    {
        List<DriverDTO> lst = DriverMapper.makeDriverDTOList(driverService.find(onlineStatus));
        return lst;
    }
    @PatchMapping("/{driverId}")
    public ResponseEntity<DriverDTO> mapUnMapCar(@Valid @PathVariable long driverId, @RequestParam long carId,
                                                 @RequestParam CarStatus action) throws EntityNotFoundException, CarAlreadyInUseException, ProhibitedOperationException
    {
        DriverDO driverDO=null;
        if(action==CarStatus.MAP)
            driverDO = carMapperDriverService.mapCar(driverId, carId);
        else
            driverDO= carMapperDriverService.unMapCar(driverId, carId);
        return new ResponseEntity<>(DriverMapper.makeDriverDTO(driverDO), HttpStatus.ACCEPTED);
    }

    @GetMapping("/find")
    public ResponseEntity<List<DriverDTO>> findDriverByCarSpecification(
            @ApiParam(value="driver attributes to search for. And and Or both operations supported",
                    example="username=driver04,,username=driver05")
            @RequestParam(required = false) String dAttrSearchTerm,
            @ApiParam(value="car attributes to search for. And and Or both operations supported",
                    example="licensePlate=ABC1234,engineType=PETROL")
            @RequestParam(required = false) String cAttrSearchTerm){

        List<DriverDTO> driverDTOList = new ArrayList<>();
        if (cAttrSearchTerm != null) {
            CarSpecificationBuilder builder = new CarSpecificationBuilder();
            Pattern patternAnd = Pattern.compile("(\\w+?)(:|=|<|>)(\\w+?),");
            Matcher matcherAnd = patternAnd.matcher(cAttrSearchTerm + ",");


            while (matcherAnd.find()) {
                builder.add(matcherAnd.group(1), matcherAnd.group(2), matcherAnd.group(3), false);
            }
            Pattern patternOr = Pattern.compile("(\\w+?)(:|=|<|>)(\\w+?),,");
            Matcher matcherOr = patternOr.matcher(cAttrSearchTerm + ",,");
            while (matcherOr.find()) {
                builder.add(matcherOr.group(1), matcherOr.group(2), matcherOr.group(3), true);
            }
            Specification<CarDO> spec= builder.build();
            List<CarDO> carDOList = carService.findAllWithSpec(spec);
//            List<DriverDO> dd = carDOList.stream().map(CarDO::getDriverDO).collect(Collectors.toCollection(ArrayList::new));
            List<DriverDO> dd = carDOList.stream().
                    filter(car->car.getDriverDO()!=null).
                    map(CarDO::getDriverDO).collect(Collectors.toCollection(ArrayList::new));

//            if(dd!=null && !dd.isEmpty())
            driverDTOList.addAll(DriverMapper.makeDriverDTOList(dd));
//                return new ResponseEntity<>(DriverMapper.makeDriverDTOList(dd), HttpStatus.OK);
            /*else
                return new ResponseEntity<>(driverDTOList, HttpStatus.OK);*/
            /*for(CarDO carDO : carDOList){
                DriverDO driverDO = driverService.findByCar(carDO.getId());
                if(driverDO != null){
                    driverDTOList.add(DriverMapper.makeDriverDTO(driverDO));
                }
            }*/
        }

        if (dAttrSearchTerm != null) {
            DriverSpecificationBuilder builder = new DriverSpecificationBuilder();
            Pattern patternAnd = Pattern.compile("(\\w+?)(:|=|<|>)(\\w+?),");
            Matcher matcherAnd = patternAnd.matcher(dAttrSearchTerm + ",");


            while (matcherAnd.find()) {
                builder.add(matcherAnd.group(1), matcherAnd.group(2), matcherAnd.group(3), false);
            }
            Pattern patternOr = Pattern.compile("(\\w+?)(:|=|<|>)(\\w+?),,");
            Matcher matcherOr = patternOr.matcher(dAttrSearchTerm + ",,");
            while (matcherOr.find()) {
                builder.add(matcherOr.group(1), matcherOr.group(2), matcherOr.group(3), true);
            }
            Specification<DriverDO> spec= builder.build();
            List<DriverDO> driverDOList = driverService.findAllBySpec(spec);
//            List<DriverDO> dd = carDOList.stream().map(CarDO::getDriverDO).collect(Collectors.toCollection(ArrayList::new));


//            if(dd!=null && !dd.isEmpty())
            driverDTOList.addAll(DriverMapper.makeDriverDTOList(driverDOList));
//            return new ResponseEntity<>(DriverMapper.makeDriverDTOList(carDOList), HttpStatus.OK);
            /*else
                return new ResponseEntity<>(driverDTOList, HttpStatus.OK);*/
            /*for(CarDO carDO : carDOList){
                DriverDO driverDO = driverService.findByCar(carDO.getId());
                if(driverDO != null){
                    driverDTOList.add(DriverMapper.makeDriverDTO(driverDO));
                }
            }*/
        }
        return new ResponseEntity<>(driverDTOList, HttpStatus.OK);
    }

}
