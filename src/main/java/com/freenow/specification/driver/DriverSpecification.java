package com.freenow.specification.driver;

import com.freenow.domainobject.DriverDO;
import com.freenow.specification.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class DriverSpecification implements Specification<DriverDO> {

    private SearchCriteria criteria;

    public DriverSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<DriverDO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return builder.greaterThanOrEqualTo(
                    root.<String> get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return builder.lessThanOrEqualTo(
                    root.<String> get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(
                        root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } if(root.get(criteria.getKey()).getJavaType()  instanceof Class && ((Class<?>)root.get(criteria.getKey()).getJavaType() ).isEnum()){
                Class c =  (Class<?>) root.get(criteria.getKey()).getJavaType();
                return builder.equal(
                        root.<String>get(criteria.getKey()), Enum.valueOf(c,criteria.getValue()+""));
            } else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }else if (criteria.getOperation().equalsIgnoreCase("=")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(
                        root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } if(root.get(criteria.getKey()).getJavaType()  instanceof Class && ((Class<?>)root.get(criteria.getKey()).getJavaType() ).isEnum()){
                Class c =  (Class<?>) root.get(criteria.getKey()).getJavaType();
                return builder.equal(
                        root.<String>get(criteria.getKey()), Enum.valueOf(c,criteria.getValue()+""));
            } else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }
        return null;
    }

}
