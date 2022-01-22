package com.cactus.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleDTO {
    private Long schdId;
    private LocalDateTime schdTime;
    private Long periodId;
    private String isExe;
    private String periodName;
    private String periodRemark;
    private Long userId;
    private Long regUserId;
    private String manager;
    private String place;
    private String periodType;
    private String name;
    private String remark;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime lastTime;
    private Long freq;
    private String cycle;
    private String isUse;
    private String isAlarm;

    @Builder
    public ScheduleDTO(Long schdId, LocalDateTime schdTime, Long periodId, String isExe, String periodName, String periodRemark, Long userId, Long regUserId, String manager, String place, String periodType, String name, String remark, LocalDateTime startTime, LocalDateTime endTime, LocalDateTime lastTime, Long freq, String cycle, String isUse, String isAlarm) {
        this.schdId = schdId;
        this.schdTime = schdTime;
        this.periodId = periodId;
        this.isExe = isExe;
        this.periodName = periodName;
        this.periodRemark = periodRemark;
        this.userId = userId;
        this.regUserId = regUserId;
        this.manager = manager;
        this.place = place;
        this.periodType = periodType;
        this.name = name;
        this.remark = remark;
        this.startTime = startTime;
        this.endTime = endTime;
        this.lastTime = lastTime;
        this.freq = freq;
        this.cycle = cycle;
        this.isUse = isUse;
        this.isAlarm = isAlarm;
    }
}
