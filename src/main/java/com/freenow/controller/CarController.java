package com.freenow.controller;

import com.freenow.controller.mapper.CarMapper;
import com.freenow.datatransferobject.CarDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.Manufacturer;
import com.freenow.domainvalue.CarStatus;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.service.car.CarService;
import com.freenow.service.manufacturer.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/v1/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private ManufacturerService manufacturerService;


    @GetMapping("/{carId}")
    public CarDTO getCar(@Valid @PathVariable long carId) throws EntityNotFoundException {
        return CarMapper.makeCarDTO(carService.find(carId));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarDTO createCar(@Valid @RequestBody CarDTO carDTO) throws ConstraintsViolationException, EntityNotFoundException {
        Manufacturer manufacturer = manufacturerService.findById(carDTO.getManufacturerId());
        CarDO carDO = CarMapper.makeCarDO(carDTO, manufacturer);
        return CarMapper.makeCarDTO(carService.create(carDO));
    }


    @DeleteMapping("/{carId}")
    public void deleteCar(@Valid @PathVariable long carId) throws EntityNotFoundException {
        carService.delete(carId);
    }

    @PutMapping("/{carId}")
    public void updateRating(@Valid @PathVariable long carId, @RequestParam BigDecimal rating) throws EntityNotFoundException {
        carService.updateRating(carId, rating);
    }

    @GetMapping
    public List<CarDTO> findCars(@RequestParam CarStatus carStatus) {
        return CarMapper.makeCarDTOList(carService.find(carStatus));
    }
}
