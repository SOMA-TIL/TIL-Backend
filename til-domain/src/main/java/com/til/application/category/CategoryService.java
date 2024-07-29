package com.til.application.category;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.til.domain.category.dto.CategoryDto;
import com.til.domain.category.model.Category;
import com.til.domain.category.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDto> getCategoryList() {
        List<Category> categoryList = categoryRepository.findAll();

        return categoryList.stream()
            .map(CategoryDto::of)
            .collect(Collectors.toList());
    }
}
