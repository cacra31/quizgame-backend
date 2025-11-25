package com.quizgame.domain.game.domain;

import com.quizgame.domain.question.domain.Question;
import com.quizgame.domain.room.domain.Room;
import com.quizgame.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Game {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Question question;

    @Column(nullable = false)
    private int round;

    @Column
    private String userAnswer;

    @Column(nullable = false)
    private String correctYn;

    @Column
    private int orderNo;

}