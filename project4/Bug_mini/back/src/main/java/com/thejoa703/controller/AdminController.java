package com.thejoa703.controller;

import com.thejoa703.dto.response.UserResponseDto;
import com.thejoa703.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000") // 리액트 포트 허용
public class AdminController {

    private final AppUserService appUserService;

    // 모든 유저 조회
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(appUserService.getAllUsers());
    }

    /**
     * 유저 강제 탈퇴
     * 수정: @PathVariable 에 ("userId") 이름을 명시했습니다.
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> forceDelete(@PathVariable("userId") Long userId) {
        appUserService.forceDeleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 유저 정지/활성화 토글
     * 수정: @PathVariable 에 ("userId") 이름을 명시했습니다.
     */
    @PatchMapping("/{userId}/status")
    public ResponseEntity<Void> toggleStatus(@PathVariable("userId") Long userId) {
        appUserService.toggleUserBlock(userId);
        return ResponseEntity.ok().build();
    }
}