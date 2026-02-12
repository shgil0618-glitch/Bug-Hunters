package com.thejoa703.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.thejoa703.entity.ChatMessage;
import com.thejoa703.entity.ChatRoom;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    // 기존의 엔티티 기준 조회
    List<ChatMessage> findByChatRoomOrderBySentAtAsc(ChatRoom chatRoom);
    
    // ⭐ 편리성을 위해 roomId(Long)로 바로 조회하는 메서드 추가
    List<ChatMessage> findByChatRoomIdOrderBySentAtAsc(Long roomId);
}