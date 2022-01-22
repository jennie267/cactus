package com.cactus.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
@Table(name = "schedule")
public class Schedule{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "schd_id")
	private Long schdId;				// 일정 ID
	@Column(name = "period_id")
	private Long periodId;				// 주기 ID
	@Column(name = "schd_Time")
	private LocalDateTime schdTime;			// 날짜
	@Column(name = "is_exe")
	private String isExe;				// 알람발행여부

	@Builder
	public Schedule(Long periodId, LocalDateTime schdTime, String isExe) {
		this.periodId = periodId;
		this.schdTime = schdTime;
		this.isExe = isExe;
	}
}
