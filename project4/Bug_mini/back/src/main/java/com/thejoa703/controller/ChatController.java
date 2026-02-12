package com.thejoa703.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.thejoa703.dto.request.ChatRequestDto;
import com.thejoa703.dto.response.ChatResponseDto;
import com.thejoa703.service.ChatService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    // 채팅방 생성
    @PostMapping("/room")
    public ResponseEntity<ChatResponseDto.Room> createRoom(@RequestBody ChatRequestDto requestDto) {
        return ResponseEntity.ok(chatService.createRoom(requestDto));
    }

    // 내 채팅방 목록 가져오기
    @GetMapping("/rooms")
    public ResponseEntity<List<ChatResponseDto.Room>> getMyRooms(@RequestParam("userId") Long userId) { // ("userId") 명시 추가
        return ResponseEntity.ok(chatService.getUserRooms(userId));
    }

    // 채팅 내역 불러오기
    @GetMapping("/room/{roomId}/messages")
    public ResponseEntity<List<ChatResponseDto.Message>> getMessages(@PathVariable("roomId") Long roomId) { // ("roomId") 명시 추가
        return ResponseEntity.ok(chatService.getMessages(roomId));
    }
}