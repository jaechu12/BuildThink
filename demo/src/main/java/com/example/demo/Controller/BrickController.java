package com.example.demo.Controller;

import com.example.demo.Model.Brick;
import com.example.demo.Model.Users;
import com.example.demo.Repository.BrickRepository;
import com.example.demo.Service.BrickService;
import com.example.demo.SessionConst;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
public class BrickController {

    private BrickService brickService;
    private BrickRepository brickRepository;

    public BrickController(BrickService brickService, BrickRepository brickRepository) {
        this.brickService = brickService;
        this.brickRepository = brickRepository;
    }

    @PostMapping("/brick")
    public String brickPost(@ModelAttribute Brick brick, HttpSession session, RedirectAttributes redirectAttributes){

        Users loginMember = (Users) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (loginMember == null) {
            return "redirect:/login";
        }

        // 현재 시간과 허용 시간 범위 확인
        LocalTime now = LocalTime.now();
        LocalTime start = LocalTime.of(18, 0); // 오후 6시
        LocalTime end = LocalTime.of(3, 59);   // 다음날 새벽 3:59

        boolean allowedTime = now.isAfter(start) || now.isBefore(end);
        if (!allowedTime) {
            redirectAttributes.addFlashAttribute("timeError", "지금은 벽돌을 쌓을 수 없는 시간입니다. 다음 시간에 찾아와 주세요!");
            return "redirect:/";
        }

        // 오늘 날짜의 시작과 끝 시각 계산
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.plusDays(1).atStartOfDay().minusNanos(1);

        // 오늘 이미 벽돌을 쌓았는지 확인
        if (brickRepository.existsByUserAndCreatedBetween(loginMember, startOfDay, endOfDay)) {
            redirectAttributes.addFlashAttribute("timeError", "오늘은 이미 벽돌을 쌓았습니다!");
            return "redirect:/";
        }

        brick.setUsers(loginMember);  // 메서드 이름 user로 맞춰서 수정 필요
        brickService.brickPost(brick);
        return "redirect:/";
    }


}
