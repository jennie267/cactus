package com.cactus.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ScheduleCount {
    private int count;
    private LocalDateTime date;
    public ScheduleCount() {
		// TODO Auto-generated constructor stub
	}

    @Builder
    public ScheduleCount(int count, LocalDateTime date) {
        this.count = count;
        this.date = date;
    }
}
