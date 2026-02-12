package com.thejoa703.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thejoa703.dto.request.ChatRequestDto;
import com.thejoa703.dto.response.ChatResponseDto;
import com.thejoa703.dto.response.UserResponseDto;
import com.thejoa703.entity.AppUser;
import com.thejoa703.entity.ChatMessage;
import com.thejoa703.entity.ChatRoom;
import com.thejoa703.repository.AppUserRepository;
import com.thejoa703.repository.ChatMessageRepository;
import com.thejoa703.repository.ChatRoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository roomRepo;
    private final ChatMessageRepository msgRepo;
    private final AppUserRepository userRepo;

    // 채팅방 생성
    @Transactional
    public ChatResponseDto.Room createRoom(ChatRequestDto dto) {
        AppUser u1 = userRepo.findById(dto.getUser1Id())
                .orElseThrow(() -> new RuntimeException("User not found: " + dto.getUser1Id()));
        AppUser u2 = userRepo.findById(dto.getUser2Id())
                .orElseThrow(() -> new RuntimeException("User not found: " + dto.getUser2Id()));
        
        ChatRoom room = ChatRoom.builder()
                .roomName(u1.getNickname() + " & " + u2.getNickname())
                .participants(Arrays.asList(u1, u2))
                .build();
        
        return convertToRoomDto(roomRepo.save(room));
    }

 // ChatService.java 에 추가
    @Transactional
    public ChatResponseDto.Message saveMessage(Long roomId, Long senderId, String content) {
        ChatRoom room = roomRepo.findById(roomId)
                .orElseThrow(() -> new RuntimeException("방을 찾을 수 없습니다."));
        AppUser sender = userRepo.findById(senderId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        ChatMessage message = ChatMessage.builder()
                .chatRoom(room)
                .sender(sender)
                .content(content)
                .sentAt(LocalDateTime.now()) // 👈 수동으로라도 현재 시간을 넣어주면 안전합니다.
                .build();
        
        msgRepo.save(message);

        return convertToMessageDto(message);
    }

    // 특정 방의 메시지 내역 조회
    @Transactional(readOnly = true)
    public List<ChatResponseDto.Message> getMessages(Long roomId) {
        return msgRepo.findByChatRoomIdOrderBySentAtAsc(roomId).stream()
                .map(this::convertToMessageDto)
                .collect(Collectors.toList());
    }

    // 사용자가 참여 중인 모든 채팅방 목록 조회
    @Transactional(readOnly = true)
    public List<ChatResponseDto.Room> getUserRooms(Long userId) {
        AppUser user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return roomRepo.findByParticipantsContaining(user).stream()
                .map(this::convertToRoomDto)
                .collect(Collectors.toList());
    }

    // --- DTO 변환 헬퍼 메서드 ---
    private ChatResponseDto.Room convertToRoomDto(ChatRoom room) {
        List<UserResponseDto> participantDtos = room.getParticipants().stream()
                .map(UserResponseDto::fromEntity)
                .collect(Collectors.toList());

        String lastMessage = "";
        LocalDateTime lastTime = null;
        
        if (room.getMessages() != null && !room.getMessages().isEmpty()) {
            ChatMessage last = room.getMessages().get(room.getMessages().size() - 1);
            lastMessage = last.getContent();
            lastTime = last.getSentAt();
        }

        return ChatResponseDto.Room.builder()
                .id(room.getId())
                .roomName(room.getRoomName())
                .participants(participantDtos)
                .lastMessage(lastMessage)
                .lastTime(lastTime)
                .build();
    }

    private ChatResponseDto.Message convertToMessageDto(ChatMessage msg) {
        return ChatResponseDto.Message.builder()
                .id(msg.getId())
                .roomId(msg.getChatRoom().getId())
                // 프론트의 m.senderNickname과 매칭됨
                .senderNickname(msg.getSender().getNickname()) 
                .content(msg.getContent())
                // 나중에 시간을 다시 쓸 때를 대비해 유지
                .sentAt(msg.getSentAt()) 
                .build();
    }
}