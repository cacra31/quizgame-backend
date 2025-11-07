package com.quizgame.domain.answer.domain;

import com.quizgame.domain.category.domain.Category;
import com.quizgame.domain.question.domain.Question;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Category category;

    @Column(nullable = false)
    private String answer;

    @Column(nullable = false)
    private int orderNo;

    @Column(nullable = false)
    private String correctYn;

}