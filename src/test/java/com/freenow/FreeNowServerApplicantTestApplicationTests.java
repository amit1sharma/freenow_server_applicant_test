package com.freenow;

import com.freenow.datatransferobject.DriverDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.service.car.CarService;
import com.freenow.specification.SearchCriteria;
import com.freenow.specification.car.CarSpecification;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = FreeNowServerApplicantTestApplication.class)
@Transactional
public class FreeNowServerApplicantTestApplicationTests
{

//    @Autowired
//    private CarService carService;
//
//    @Autowired
//    private TestRestTemplate testRestTemplate;
//
//    @Test
//    public void singleCriteriaCheck() {
//        CarSpecification carSpecification = new CarSpecification(new SearchCriteria("licensePlate","=","ABC1234", false));
//        List<CarDO> carDOList = carService.findAllWithSpec(carSpecification);
//        Assert.notEmpty(carDOList, "No result found");
//    }

}
