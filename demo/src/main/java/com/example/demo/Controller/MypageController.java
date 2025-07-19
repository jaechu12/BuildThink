package com.example.demo.Controller;

import com.example.demo.DTO.BuildingStatResponseDTO;
import com.example.demo.DTO.SummaryDTO;
import com.example.demo.Model.Brick;
import com.example.demo.Model.Users;
import com.example.demo.Service.MypageService;
import com.example.demo.SessionConst;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Controller
public class MypageController {

    private final MypageService mypageService;

    public MypageController(MypageService mypageService) {
        this.mypageService = mypageService;
    }


    @GetMapping("/bricks")
    public String getRecentBricks(HttpSession session, Model model) {
        Users loginMember = (Users) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (loginMember == null) {
            return "redirect:/login";
        }

        List<Brick> recentBricks = mypageService.getRecentBricks(loginMember);
        model.addAttribute("recentBricks", recentBricks);
        return "mypage";
    }

    @PostMapping("/summary")
    public String getSummary(HttpSession session, Model model) {
        Users user = (Users) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (user == null) {
            return "redirect:/login";
        }

        SummaryDTO summaryDTO = mypageService.createSummaryForUser(user);
        if (summaryDTO == null) {
            // 이미 정산한 경우
            LocalDate today = LocalDate.now();
            DayOfWeek day = today.getDayOfWeek();
            boolean isSunday = (day == DayOfWeek.SATURDAY);
            model.addAttribute("isSunday", isSunday);
            return "redirect:/";
        }

        model.addAttribute("buildingType", summaryDTO.getBuildingType());
        model.addAttribute("brickCount", summaryDTO.getBrickCount());
        model.addAttribute("message", "정산 완료!");
        return "summary-result"; // 기존 html 유지
    }

    @GetMapping("/search/{id}")
    @ResponseBody
    public ResponseEntity<?> getSearchByID(HttpSession session, @PathVariable Long id) {
        Users user = (Users) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return mypageService.findBuildingStatByUserId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/mypage")
    @ResponseBody
    public ResponseEntity<?> getMypage(HttpSession session) {
        Users user = (Users) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return mypageService.findBuildingStatByUserId(user.getId())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
