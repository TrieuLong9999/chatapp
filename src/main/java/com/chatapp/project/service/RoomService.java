package com.chatapp.project.service;

import com.chatapp.project.entity.Room;
import com.chatapp.project.entity.UserEntity;

import java.util.List;

public interface RoomService {
    Room addUserToRoom(Long roomId, Long userId);
    Room removeUserFromRoom(Long roomId, Long userId);
    Room createRoom(List<UserEntity> userEntityList);
    List<Room> findByUsersId(Long userId);
}
