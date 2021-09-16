package com.freenow.service.manufacturer;

import com.freenow.domainobject.Manufacturer;
import com.freenow.exception.EntityNotFoundException;

public interface ManufacturerService {
    Manufacturer findById(Long id) throws EntityNotFoundException;
}
