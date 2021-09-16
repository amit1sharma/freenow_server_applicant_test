package com.freenow.dataaccessobject;

import com.freenow.domainobject.CarDO;
import com.freenow.domainvalue.CarStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Database Access Object for car table.
 */
public interface CarRepository extends JpaRepository<CarDO, Long>, JpaSpecificationExecutor<CarDO> {
    List<CarDO> findByCarStatus  (CarStatus carStatus);

}
