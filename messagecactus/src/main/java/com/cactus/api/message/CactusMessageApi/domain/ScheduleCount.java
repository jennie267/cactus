package com.cactus.api.message.CactusMessageApi.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ScheduleCount {
    private int count;
    private Date date;
    public ScheduleCount() {
		// TODO Auto-generated constructor stub
	}

    @Builder
    public ScheduleCount(int count, Date date) {
        this.count = count;
        this.date = date;
    }
}
