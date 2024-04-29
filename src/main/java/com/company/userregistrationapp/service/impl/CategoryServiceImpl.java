package com.company.userregistrationapp.service.impl;

import com.company.userregistrationapp.dao.entity.CategoryEntity;
import com.company.userregistrationapp.dao.repository.CategoryRepository;
import com.company.userregistrationapp.enums.ExceptionEnum;
import com.company.userregistrationapp.exception.NotFoundException;
import com.company.userregistrationapp.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import static com.company.userregistrationapp.enums.ExceptionEnum.CATEGORY_NOT_FOUND;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;

    @Override
    public CategoryEntity findById(Long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() ->
                        NotFoundException.of(CATEGORY_NOT_FOUND.getCode(),
                                String.format(CATEGORY_NOT_FOUND.getMessage(), id)));

    }
}
