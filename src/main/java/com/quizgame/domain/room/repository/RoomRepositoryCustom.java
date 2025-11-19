package com.quizgame.domain.room.repository;

import com.quizgame.domain.room.api.v1.dto.RoomListResponse;

import java.util.List;

public interface RoomRepositoryCustom {

    List<RoomListResponse> findRoomList();

}
