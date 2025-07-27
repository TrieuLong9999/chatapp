package com.chatapp.project.repository;

import com.chatapp.project.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("""
    SELECT r FROM Room r
    JOIN r.users u
    WHERE r.roomType = :type
      AND u.id IN (:userId1, :userId2)
    GROUP BY r
    HAVING COUNT(DISTINCT u.id) = 2
""")
    Optional<Room> findP2PRoomBetweenUsers(
            @Param("type") Room.RoomType type,
            @Param("userId1") Long userId1,
            @Param("userId2") Long userId2
    );
    List<Room> findByUsersId(Long userId);
}
