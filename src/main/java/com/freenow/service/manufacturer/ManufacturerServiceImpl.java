package com.freenow.service.manufacturer;

import com.freenow.dataaccessobject.ManufacturerRepository;
import com.freenow.domainobject.Manufacturer;
import com.freenow.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManufacturerServiceImpl implements ManufacturerService{

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Override
    public Manufacturer findById(Long id) throws EntityNotFoundException {
        return manufacturerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Could not find manufacturer with id: " + id));
    }
}
