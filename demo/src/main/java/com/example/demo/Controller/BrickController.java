package com.example.demo.Controller;

import com.example.demo.DTO.RequestDTO.BrickCreateDTO;
import com.example.demo.Model.Users;
import com.example.demo.Repository.BrickRepository;
import com.example.demo.Service.BrickService;
import com.example.demo.SessionConst;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
public class BrickController {

    private BrickService brickService;
    private BrickRepository brickRepository;

    public BrickController(BrickService brickService, BrickRepository brickRepository) {
        this.brickService = brickService;
        this.brickRepository = brickRepository;
    }

    @PostMapping("/brick")
    public String createBrick(@ModelAttribute BrickCreateDTO brickDTO, HttpSession session, RedirectAttributes redirectAttributes) {
        Users loginMember = (Users) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (loginMember == null) {
            return "redirect:/login";
        }

        // 시간 체크
        LocalTime now = LocalTime.now();
        LocalTime start = LocalTime.of(18, 0);
        LocalTime end = LocalTime.of(3, 59);
        boolean allowedTime = now.isAfter(start) || now.isBefore(end);
        if (!allowedTime) {
            redirectAttributes.addFlashAttribute("timeError", "지금은 벽돌을 쌓을 수 없는 시간입니다. 다음 시간에 찾아와 주세요!");
            return "build";
        }

        // 중복 체크
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.plusDays(1).atStartOfDay().minusNanos(1);
        if (brickRepository.existsByUserAndCreatedBetween(loginMember, startOfDay, endOfDay)) {
            redirectAttributes.addFlashAttribute("timeError", "오늘은 이미 벽돌을 쌓았습니다!");
            return "build";
        }

        // 저장
        brickService.brickPost(brickDTO, loginMember);
        return "redirect:/";
    }
}
