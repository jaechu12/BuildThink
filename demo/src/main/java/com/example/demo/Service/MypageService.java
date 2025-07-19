package com.example.demo.Service;

import com.example.demo.DTO.SummaryDTO;
import com.example.demo.Model.Brick;
import com.example.demo.Model.Summary;
import com.example.demo.Model.Users;
import com.example.demo.Repository.BrickRepository;
import com.example.demo.Repository.BuildingStatRepository;
import com.example.demo.Repository.SummaryRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MypageService {

    private final BrickRepository brickRepository;
    private final SummaryRepository summaryRepository;
    private final BuildingStatRepository buildingStatRepository;

    public MypageService(BrickRepository brickRepository,
                         SummaryRepository summaryRepository,
                         BuildingStatRepository buildingStatRepository) {
        this.brickRepository = brickRepository;
        this.summaryRepository = summaryRepository;
        this.buildingStatRepository = buildingStatRepository;
    }

    public List<Brick> getRecentBricks(Users user) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sevenDaysAgo = now.minusDays(6).toLocalDate().atStartOfDay(); // 오늘 포함 7일
        return brickRepository.findByUserAndCreatedBetween(user, sevenDaysAgo, now);
    }

    public Optional<Summary> findSummaryByUserAndWeekStart(Users user, LocalDate weekStart) {
        return summaryRepository.findByUseridAndWeekStartDate(user.getId(), weekStart);
    }

    public SummaryDTO createSummaryForUser(Users user) {
        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.with(DayOfWeek.MONDAY);

        Optional<Summary> existing = findSummaryByUserAndWeekStart(user, weekStart);
        if (existing.isPresent()) {
            return null; // 이미 정산했음
        }

        LocalDateTime startDateTime = weekStart.atStartOfDay();
        List<Brick> bricks = brickRepository.findAllByUserAndCreatedAfter(user, startDateTime);

        long brickCount = bricks.size();

        String buildingType;
        if (brickCount >= 7) buildingType = "타워";
        else if (brickCount >= 5) buildingType = "아파트";
        else if (brickCount >= 3) buildingType = "빌라";
        else buildingType = "주택";

        Summary summary = new Summary();
        summary.setUserid(user.getId());
        summary.setWeekStartDate(weekStart);
        summary.setBrickCount(brickCount);
        summary.setBuildingType(buildingType);

        summaryRepository.save(summary);

        return new SummaryDTO(user.getId(), weekStart, brickCount, buildingType);
    }

    public Optional<?> findBuildingStatByUserId(Long userId) {
        return buildingStatRepository.findById(userId);
    }
}
