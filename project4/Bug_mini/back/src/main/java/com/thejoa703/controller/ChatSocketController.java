package com.thejoa703.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.thejoa703.dto.request.ChatMessageRequestDto;
import com.thejoa703.dto.response.ChatResponseDto;
import com.thejoa703.service.ChatService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatSocketController {
    private final ChatService chatService;

    @MessageMapping("/chat.sendMessage/{roomId}")
    @SendTo("/topic/{roomId}")
    public ChatResponseDto.Message sendMessage(
            @DestinationVariable("roomId") Long roomId, // ("roomId") 명시 추가
            ChatMessageRequestDto requestDto) {
        
        // requestDto에 담긴 senderId와 content를 서비스로 전달
        return chatService.saveMessage(
            roomId,
            requestDto.getSenderId(),
            requestDto.getContent()
        );
    }
}