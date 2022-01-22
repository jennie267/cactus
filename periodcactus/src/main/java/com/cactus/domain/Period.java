package com.cactus.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
@Table(name = "period")
public class Period {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "period_id")
	private Long periodId;				// 주기 ID
	@Column(name = "period_type")
	private String periodType;			// 주기 타입
	@Column(name = "name")
	private String	name;				// 주기명
	@Column(name = "remark")
	private String 	remark;				// 내용
	@Column(name = "start_Time")
	private LocalDateTime 	startTime;			// 시작일자
	@Column(name = "end_Time")
	private LocalDateTime 	endTime;			// 종료일자
	@Column(name = "last_Time")
	private LocalDateTime lastTime;			// 마지막일자
	@Column(name = "freq")
	private Long freq;					// 빈도
	@Column(name = "cycle")
	private String	cycle;				// 주기(Y, M, D, H, mm)
	@Column(name = "user_id")
	private Long userId;				// 사용자 ID
	@Column(name = "reg_user_id")
	private Long regUserId;				// 등록자 ID
	@Column(name = "is_use")
	private String 	isUse;				// 사용여부(Y, N)
	@Column(name = "is_alarm")
	private String 	isAlarm;			// 알람여부(Y, N)
	@Column(name = "manager")
	private String manager;				// 담당자
	@Column(name = "place")
	private String place;				// 장소

	@Builder
	public Period(String periodType, String name, String remark, LocalDateTime startTime, LocalDateTime endTime, LocalDateTime lastTime, Long freq, String cycle, Long userId, Long regUserId, String isUse, String isAlarm, String manager, String place) {
		this.periodType = periodType;
		this.name = name;
		this.remark = remark;
		this.startTime = startTime;
		this.endTime = endTime;
		this.lastTime = lastTime;
		this.freq = freq;
		this.cycle = cycle;
		this.userId = userId;
		this.regUserId = regUserId;
		this.isUse = isUse;
		this.isAlarm = isAlarm;
		this.manager = manager;
		this.place = place;
	}
}
