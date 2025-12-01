package com.quizgame.global.constant;

public abstract class WebsocketTopic {

    public static final String ROOM_LIST_TOPIC = "/topic/room-list";
    public static final String ROOM_USERS_TOPIC = "/topic/room/%d/users";
    public static final String ROOM_TOPIC = "/topic/room/%d";
    public static final String ROOM_QUESTION_TOPIC = "/topic/room/%d/question";

}
