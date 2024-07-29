package com.til.application.category;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.til.domain.category.dto.CategoryDto;
import com.til.domain.category.model.Category;
import com.til.domain.category.repository.CategoryRepository;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    void 카테고리_리스트를_정상적으로_반환한다() {
        // given
        int size = 10;
        List<Category> categoryList = createCategoryList(size);

        given(categoryRepository.findAll()).willReturn(categoryList);

        // when
        List<CategoryDto> categoryDtoList = categoryService.getCategoryList();

        // then
        assertThat(categoryDtoList.size()).isEqualTo(size);
    }

    private List<Category> createCategoryList(int size) {
        List<Category> categoryList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            categoryList.add(createCategory());
        }

        return categoryList;
    }

    private Category createCategory() {
        return Category.builder()
            .id(1L)
            .name("NETWORK")
            .topic("HTTP")
            .build();
    }

}
