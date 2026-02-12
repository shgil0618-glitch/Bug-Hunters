package com.thejoa703.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ChatMessageRequestDto {
    private Long roomId;
    private Long senderId;
    private String content;
}
