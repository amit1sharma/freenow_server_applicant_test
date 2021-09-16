package com.freenow.service.car;

import com.freenow.domainobject.CarDO;
import com.freenow.domainvalue.CarStatus;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface CarService {
    CarDO find(Long carId) throws EntityNotFoundException;

    CarDO create(CarDO carDO) throws ConstraintsViolationException;

    @Transactional
    void delete(Long carId) throws EntityNotFoundException;

    List<CarDO> find(CarStatus carStatus);

    @Transactional
    void updateRating(Long carId, BigDecimal rating) throws EntityNotFoundException;

    List<CarDO> findAll();

    List<CarDO> findAllWithSpec(Specification<CarDO> spec);
}
