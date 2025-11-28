package com.quizgame.global.constant;

public abstract class RedisKey {

    // ID 생성용 시퀀스 Key
    public static final String ROOM_SEQ_KEY = "quiz:seq:room";
    public static final String GAME_SEQ_KEY = "quiz:seq:game";
    public static final String QUESTION_SEQ_KEY = "quiz:seq:question";
    public static final String ANSWER_SEQ_KEY = "quiz:seq:answer";

    // Room
    public static final String ROOM = "quiz:room:%d"; //방 정보
    public static final String WAITING_ROOM = "quiz:room:waiting:%d"; //카테고리별 대기방

    // User
    public static final String USER = "quiz:user:%d"; // 유저정보
    public static final String ROOM_USER = "quiz:user:%d:room";  //유저입장 방 정보

}
