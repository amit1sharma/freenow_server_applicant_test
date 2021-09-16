package com.freenow.service.car;

import com.freenow.dataaccessobject.CarRepository;
import com.freenow.domainobject.CarDO;
import com.freenow.domainvalue.CarStatus;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.service.driver.DefaultDriverService;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DefaultCarServiceImpl implements CarService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultCarServiceImpl.class);

    @Autowired
    private CarRepository carRepository;

    @Override
    public CarDO find(Long carId) throws EntityNotFoundException {
        return findCarChecked(carId);
    }

    @Override
    public CarDO create(CarDO carDO) throws ConstraintsViolationException {
        CarDO car;
        try {
            car = carRepository.save(carDO);
        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            LOG.warn("Some constraints are thrown due to car creation", e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return car;
    }

    @Override
    @Transactional
    public void delete(Long carId) throws EntityNotFoundException {
        CarDO carDO = findCarChecked(carId);
        carDO.setDeleted(true);
    }

    @Override
    public List<CarDO> find(CarStatus carStatus) {
        return carRepository.findByCarStatus(carStatus);
    }

    private CarDO findCarChecked(Long carId) throws EntityNotFoundException {
        return carRepository.findById(carId).orElseThrow(() -> new EntityNotFoundException("Could not find car with id: " + carId));
    }

    @Override
    @Transactional
    synchronized public void updateRating(Long carId, BigDecimal rating) throws EntityNotFoundException {
        CarDO carDO=findCarChecked(carId);
        long noOfRating = carDO.getNumberOfRating()+1;
        BigDecimal averageRating = (carDO.getRating().multiply(BigDecimal.valueOf(carDO.getNumberOfRating())).add(rating)).divide(BigDecimal.valueOf(noOfRating));
        carDO.setNumberOfRating(noOfRating);
        carDO.setRating(averageRating);
    }

    @Override
    public List<CarDO> findAll(){
        return carRepository.findAll();
    }


}
