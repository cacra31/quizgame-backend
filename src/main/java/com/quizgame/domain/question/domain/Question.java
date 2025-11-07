package com.quizgame.domain.question.domain;

import com.quizgame.domain.category.domain.Category;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String difficulty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Category category;

}