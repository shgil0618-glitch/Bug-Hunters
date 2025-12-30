package com.thejoa703.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thejoa703.service.UserStatusService;

@Controller
@RequestMapping("/admin/user-status")
public class AdminUserStatusController {

    @Autowired
    private UserStatusService userStatusService;

    // ================= 사용자 목록 페이지 =================
    @GetMapping
    public String userStatusPage(Model model) {
        model.addAttribute("users", userStatusService.getAllUsers());
        return "admin/user_status";  // 파일명 user_status.html
    }

    // ================= 정지 해제 =================
    @PostMapping("/activate")
    public String activateUser(@RequestParam int appUserId) {
        userStatusService.activateUser(appUserId);
        return "redirect:/admin/user-status";
    }

    // ================= 정지 =================
    @PostMapping("/suspend")
    public String suspendUser(
            @RequestParam int appUserId,
            @RequestParam String reason,
            @RequestParam String untilDate) {

        userStatusService.suspendUser(appUserId, reason, untilDate);
        return "redirect:/admin/user-status";
    }
}
