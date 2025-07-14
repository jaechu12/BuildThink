package com.example.demo.Controller;

import com.example.demo.Model.Brick;
import com.example.demo.Model.Summary;
import com.example.demo.Model.Users;
import com.example.demo.Repository.BrickRepository;
import com.example.demo.Repository.SummaryRepository;
import com.example.demo.Repository.UsersRepository;
import com.example.demo.Service.BrickService;
import com.example.demo.SessionConst;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class MypageController {

    private BrickRepository brickRepository;
    private BrickService brickService;
    private UsersRepository usersRepository;
    private SummaryRepository summaryRepository;

    public MypageController(BrickRepository brickRepository, SummaryRepository summaryRepository, BrickService brickService, UsersRepository usersRepository) {
        this.brickRepository = brickRepository;
        this.summaryRepository = summaryRepository;
        this.brickService = brickService;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/bricks")
    public String getRecentBricks(HttpSession session, Model model) {
        Users loginMember = (Users) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (loginMember == null) {
            return "redirect:/login";
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sevenDaysAgo = now.minusDays(6).toLocalDate().atStartOfDay(); // 오늘 포함 7일
        List<Brick> recentBricks = brickRepository.findByUserAndCreatedBetween(loginMember, sevenDaysAgo, now);

        model.addAttribute("recentBricks", recentBricks);
        return "mypage";
    }

    @Transactional
    @PostMapping("/summary")
    public String getSummary(HttpSession session, Model model) {
        // 로그인 체크
        Users user = (Users) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (user == null) {
            return "redirect:/login";
        }

        // 오늘 날짜와 이번 주 시작일 (예: 월요일 기준)
        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.with(java.time.DayOfWeek.MONDAY);

        java.sql.Date sqlWeekStart = java.sql.Date.valueOf(weekStart);
        // 이미 해당 주에 정산했는지 체크
        Optional<Summary> existingSummary = summaryRepository.findByUseridAndWeekStartDate(user.getId(), weekStart);
        if (existingSummary.isPresent()) {
            // 이미 정산한 경우 해당 페이지로 리다이렉트 혹은 메시지 출력
            DayOfWeek day = today.getDayOfWeek();
            boolean isSunday = (day == DayOfWeek.SATURDAY);
            model.addAttribute("isSunday", isSunday);
            return "redirect:/"; // 정산 결과 페이지 혹은 알림 페이지
        }

        // 이번 주 시작일부터 오늘까지 쌓은 벽돌 가져오기
        LocalDateTime startDateTime = weekStart.atStartOfDay();
        List<Brick> bricks = brickRepository.findAllByUserAndCreatedAfter(user, startDateTime);

        long brickCount = bricks.size();

        // 건물 타입 결정 예시 (벽돌 개수에 따른 구간)
        String buildingType;
        if (brickCount >= 7) {
            buildingType = "대형 빌딩";
        } else if (brickCount >= 4) {
            buildingType = "중형 건물";
        } else if (brickCount >= 1) {
            buildingType = "소형 집";
        } else {
            buildingType = "건물 없음";
        }

        // 정산 Summary 객체 생성 및 저장
        Summary summary = new Summary();
        summary.setUserid(user.getId());
        summary.setWeekStartDate(weekStart);
        summary.setBrickCount(brickCount);
        summary.setBuildingType(buildingType);

        summaryRepository.save(summary);

        // 뷰에 데이터 전달
        model.addAttribute("buildingType", buildingType);
        model.addAttribute("brickCount", brickCount);
        model.addAttribute("message", "정산 완료!");
        return "summary-result"; // 결과 보여줄 템플릿 이름
    }

}
