package com.chatapp.project.service.impl;

import com.chatapp.project.entity.Room;
import com.chatapp.project.entity.UserEntity;
import com.chatapp.project.repository.RoomRepository;
import com.chatapp.project.repository.UserRepository;
import com.chatapp.project.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Room addUserToRoom(Long roomId, Long userId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        room.getUsers().add(user); // thêm user vào set users của room
        return roomRepository.save(room);
    }
    @Override
    public Room removeUserFromRoom(Long roomId, Long userId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        room.getUsers().remove(user); // xoá user khỏi set users của room
        return roomRepository.save(room);
    }

    @Override
    public Room createRoom(List<UserEntity> userEntityList) {
        if (userEntityList == null || userEntityList.size() < 2) {
            throw new IllegalArgumentException("Must have at least 2 users");
        }

        if (userEntityList.size() == 2) {
            Long userId1 = userEntityList.get(0).getId();
            Long userId2 = userEntityList.get(1).getId();

            Optional<Room> existingRoom = roomRepository.findP2PRoomBetweenUsers(Room.RoomType.P2P, userId1, userId2);
            if (existingRoom.isPresent()) {
                return existingRoom.get();
            }
        }

        // Nếu chưa tồn tại hoặc là group chat thì tạo mới
        Room.RoomType type = userEntityList.size() == 2 ? Room.RoomType.P2P : Room.RoomType.GROUP;

        Room room = new Room();
        room.setRoomType(type);
        room.setCreatedAt(LocalDateTime.now());
        room.getUsers().addAll(userEntityList);

        // Đặt tên nếu cần
        if (type == Room.RoomType.P2P) {
            String name = userEntityList.get(0).getUsername() + " & " + userEntityList.get(1).getUsername();
            room.setName(name);
        } else {
            room.setName("Group chat");
        }

        return roomRepository.save(room);
    }

    @Override
    public List<Room> findByUsersId(Long userId) {
        return roomRepository.findByUsersId(userId);
    }
}
