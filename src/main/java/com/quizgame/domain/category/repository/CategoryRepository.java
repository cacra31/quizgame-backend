package com.quizgame.domain.category.repository;

import com.quizgame.domain.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
