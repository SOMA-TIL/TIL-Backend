package com.til.controller.category;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.til.application.category.CategoryService;
import com.til.common.response.ApiResponse;
import com.til.controller.category.response.CategoryListResponse;
import com.til.domain.category.dto.CategoryDto;
import com.til.domain.category.enums.CategorySuccessCode;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("")
    public ApiResponse<CategoryListResponse> getCategoryList() {
        List<CategoryDto> categoryList = categoryService.getCategoryList();
        return ApiResponse.ok(CategorySuccessCode.SUCCESS_GET_CATEGORY_LIST, CategoryListResponse.of(categoryList));
    }
}
