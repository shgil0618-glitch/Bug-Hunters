package com.thejoa703.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.thejoa703.entity.ChatRoom;
import com.thejoa703.entity.AppUser;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    // ⭐ 내가 참여 중인 채팅방 목록을 가져오기 위한 메서드 추가
    List<ChatRoom> findByParticipantsContaining(AppUser user);
}