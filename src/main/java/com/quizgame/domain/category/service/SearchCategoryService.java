package com.quizgame.domain.category.service;

import com.quizgame.domain.category.api.v1.dto.CategoryResponse;
import com.quizgame.domain.category.domain.Category;
import com.quizgame.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchCategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> execute() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> CategoryResponse.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .build())
                .toList();
    }

}
