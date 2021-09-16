package com.freenow.dataaccessobject;

import com.freenow.domainobject.DriverDO;
import com.freenow.domainobject.Manufacturer;
import com.freenow.domainvalue.OnlineStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Database Access Object for car_manufacturer table.
 * <p/>
 */
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

}
