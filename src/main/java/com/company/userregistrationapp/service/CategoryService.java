package com.company.userregistrationapp.service;

import com.company.userregistrationapp.dao.entity.CategoryEntity;

public interface CategoryService {
    CategoryEntity findById(Long id);
}
