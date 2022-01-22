package com.cactus.api.message.CactusMessageApi.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
public class Schedule {
    private int userId;
    private String scheduleRemark;
    private Date startDateTime;
    private Date endDateTime;
    private int centerId;
    private String centerName;
    private String centerTel;
    private String centerRemark;
    private String centerMainAddress;
    private String centerSubAddress;
    private String centerLat;
    private String centerLon;
    private int centerManagerId;
    private String centerManagerName;
    private String centerManagerTel;

    public Schedule() {
		// TODO Auto-generated constructor stub
	}

    @Builder
    public Schedule(int userId, String scheduleRemark, Date startDateTime, Date endDateTime, int centerId, String centerName, String centerTel, String centerRemark, String centerMainAddress, String centerSubAddress, String centerLat, String centerLon, int centerManagerId, String centerManagerName, String centerManagerTel) {
        this.userId = userId;
        this.scheduleRemark = scheduleRemark;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.centerId = centerId;
        this.centerName = centerName;
        this.centerTel = centerTel;
        this.centerRemark = centerRemark;
        this.centerMainAddress = centerMainAddress;
        this.centerSubAddress = centerSubAddress;
        this.centerLat = centerLat;
        this.centerLon = centerLon;
        this.centerManagerId = centerManagerId;
        this.centerManagerName = centerManagerName;
        this.centerManagerTel = centerManagerTel;
    }
}