package com.chatapp.project.controller;

import com.chatapp.project.entity.CustomUserDetails;
import com.chatapp.project.entity.Room;
import com.chatapp.project.entity.UserEntity;
import com.chatapp.project.form.request.user.FormUserIds;
import com.chatapp.project.service.RoomService;
import com.chatapp.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Room> createRoom(@RequestBody FormUserIds userIds, Authentication authentication) {
        // Lấy userId từ JWT (nếu user tự tạo room thì chắc chắn có trong userIds)
        Long currentUserId = getUserIdFromAuthentication(authentication);

        // Đảm bảo user hiện tại có trong danh sách tạo room
        if (!userIds.getUserIds().contains(currentUserId)) {
            userIds.getUserIds().add(currentUserId);
        }

        // Lấy UserEntity từ userIds (giả sử bạn có UserRepository)
        List<UserEntity> users = userService.findUsersByIds(userIds.getUserIds());

        // Tạo room
        Room room = roomService.createRoom(users);

        return ResponseEntity.ok(room);
    }
    private Long getUserIdFromAuthentication(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Unauthenticated");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof Long) {
            return (Long) principal;
        }

        // Hoặc nếu principal là username (String), bạn tra trong DB
        if (principal instanceof String username) {
            UserEntity user = userService.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            return user.getId();
        }

        throw new RuntimeException("Cannot get user id from authentication");
    }
    // Lấy danh sách phòng của user hiện tại từ JWT
    @GetMapping
    public ResponseEntity<List<Room>> getRoomsOfCurrentUser(Authentication authentication) {
        Long currentUserId = getUserIdFromAuthentication(authentication);

        List<Room> rooms = roomService.findByUsersId(currentUserId);


        return ResponseEntity.ok(rooms);
    }
}
