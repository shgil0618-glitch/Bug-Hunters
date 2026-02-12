package com.thejoa703.entity;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "chat_room")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chatroom_seq")
    @SequenceGenerator(name = "chatroom_seq", sequenceName = "CHATROOM_SEQ", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 200)
    private String roomName;

    @ManyToMany
    @JoinTable(
        name = "chat_room_user",
        joinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "app_user_id", referencedColumnName = "APP_USER_ID")
    )
    private List<AppUser> participants = new ArrayList<>();

    // ⭐ 중요: 메시지 목록을 가져올 때 다시 방 정보를 부르지 않도록 설정
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore 
    private List<ChatMessage> messages = new ArrayList<>();
}