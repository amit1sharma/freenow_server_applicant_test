package com.freenow.specification.driver;

import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;
import com.freenow.specification.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DriverSpecificationBuilder {
    List<SearchCriteria> searchCriteriaList;

    public DriverSpecificationBuilder() {
        this.searchCriteriaList = new ArrayList<>();
    }

    public DriverSpecificationBuilder add(String key, String operation, Object value, boolean orOperation){
        this.searchCriteriaList.add(new SearchCriteria(key,operation, value, orOperation));
        return this;
    }

    public Specification build(){
        if(searchCriteriaList.size()==0){
            return null;
        }
        List<Specification<DriverDO>> specs = searchCriteriaList.stream()
                .map(DriverSpecification::new)
                .collect(Collectors.toList());

        Specification result = specs.get(0);
        /*searchCriteriaList.stream()
                .map(searchCriteria -> {
                    searchCriteria.isOrOperation()?Specification.where(specs.get(0)).or(specs.get(i))
                });*/

        for (int i = 1; i < searchCriteriaList.size(); i++) {
            result = searchCriteriaList.get(i).isOrOperation()
                    ? Specification.where(result).or(specs.get(i))
                    : Specification.where(result).and(specs.get(i));
        }
        return result;
    }
}
