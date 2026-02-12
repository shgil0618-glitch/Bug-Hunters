package com.thejoa703.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChatMessageResponseDto {
    private Long id;
    private Long roomId;
    private Long senderId;
    private String senderNickname; // 유저 엔티티 대신 닉네임만!
    private String content;
    private LocalDateTime sentAt;
}
