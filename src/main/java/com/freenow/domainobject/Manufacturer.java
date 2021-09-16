package com.freenow.domainobject;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="car_manufacturer")
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @OneToMany(fetch = FetchType.LAZY , mappedBy = "manufacturer")
    private Set<CarDO> carDOSet;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CarDO> getCarDOSet() {
        return carDOSet;
    }

    public void setCarDOSet(Set<CarDO> carDOSet) {
        this.carDOSet = carDOSet;
    }

    public Manufacturer(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Manufacturer() {
    }


}
