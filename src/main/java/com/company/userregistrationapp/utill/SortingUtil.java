package com.company.userregistrationapp.utill;

import com.company.userregistrationapp.criteria.SortingCriteria;
import com.company.userregistrationapp.enums.SortEnum;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class SortingUtil {
    private Optional<Sort.Order> getOrder(String field, SortEnum sortEnum){
        return Optional.ofNullable(field)
                .flatMap(f -> Optional.ofNullable(sortEnum)
                        .map(d -> new Sort.Order(Sort.Direction.valueOf(sortEnum.name()),f)));
    }

    public List<Sort.Order> buildSortOrders(SortingCriteria sortingCriteria) {

        List<Sort.Order> orders = new ArrayList<>();

        getOrder("createdAt", sortingCriteria.getCreatedAt()).ifPresent(orders::add);
        getOrder("name", sortingCriteria.getName()).ifPresent(orders::add);
        getOrder("priority", sortingCriteria.getPriority()).ifPresent(orders::add);
        getOrder("deadline", sortingCriteria.getDeadline()).ifPresent(orders::add);

        return orders;}
}
