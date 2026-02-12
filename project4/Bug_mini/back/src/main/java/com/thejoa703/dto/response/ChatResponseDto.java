package com.thejoa703.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class ChatResponseDto {

    // 채팅방 요약 정보
    @Getter @Builder
    public static class Room {
        private Long id;
        private String roomName;
        private List<UserResponseDto> participants;
        private String lastMessage;
        private LocalDateTime lastTime;
    }

    // 메시지 상세 정보
    @Getter @Builder
    public static class Message {
        private Long id;
        private Long roomId;
        private String senderNickname;
        private String content;
        private LocalDateTime sentAt;
    }
}