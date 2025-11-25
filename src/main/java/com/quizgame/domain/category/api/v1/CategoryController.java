package com.quizgame.domain.category.api.v1;

import com.quizgame.domain.category.api.v1.dto.CategoryResponse;
import com.quizgame.domain.category.service.SearchCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final SearchCategoryService searchCategoryService;

    @GetMapping("/list")
    public ResponseEntity<List<CategoryResponse>> list() {
        return ResponseEntity.ok(searchCategoryService.execute());
    }

}
