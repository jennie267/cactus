package com.cactus.service;

import com.cactus.domain.Schedule;
import com.cactus.domain.dto.ScheduleCount;
import com.cactus.domain.dto.ScheduleDTO;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ScheduleService {
	List<Schedule> findAll();
	
	List<Schedule> findByUserId(Long userId);
	
	Optional<Schedule> save(Schedule scheduleObj);
	
	void deleteSchedule(Long scheduleId);
	
	void scheduleByPeriodId(Long periodId);
	
	void insertQrtSchedule();
	
	List<Date> findByDate(Long userId, String date);

	ScheduleDTO getFirstScheduleInFuture(Long userId);

	ScheduleCount getCountScheduleInDaysByUserId(String date, Long userId);

	List<ScheduleDTO> getScheduleOfDayAndUserId(String day, Long userId);

	ScheduleDTO insertScheduleForOnce(ScheduleDTO scheduleDTO);

	void alertScheduleMessage();

    List<ScheduleDTO> getScheduleOfFromToAndUserId(String fromDate, String toDate, Long userId);

	List<ScheduleDTO> getScheduleOfFromToAndUserIdOrderByPeriod(String fromDate, String toDate, Long userId);

    void deletesScheduleByPeriodId(Long periodId);

    List<ScheduleDTO> getScheduleOfFromToAndPeriodId(String fromDate, String toDate, Long periodId);
}
