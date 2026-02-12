package com.thejoa703.dto.request;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ChatRequestDto {
    private Long user1Id;
    private Long user2Id;
}