package com.cactus.service;

import com.cactus.domain.Period;
import com.cactus.domain.Schedule;
import com.cactus.domain.ScheduleRepository;
import com.cactus.domain.dto.ScheduleCount;
import com.cactus.domain.dto.ScheduleDTO;
import com.cactus.util.DateUtil;
import com.cactus.util.RestTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ScheduleServiceImpl implements ScheduleService {
	@Autowired
	ScheduleRepository scheduleRepository;
	@Autowired
	PeriodService periodService;
	
	@Transactional
	public List<Schedule> findAll() {
		return scheduleRepository.findAll();
	}

	@Transactional
	public List<Schedule> findByUserId(Long userId) {
		return scheduleRepository.findByUserId(userId);
	}

	@Transactional
	public Optional<Schedule> save(Schedule scheduleObj) {
		return Optional.of(scheduleRepository.save(scheduleObj));
	}

	@Transactional
	public void deleteSchedule(Long scheduleId) {
		scheduleRepository.deleteSchedule(scheduleId);
	}

	@Transactional
	public void scheduleByPeriodId(Long periodId) {
		List<Period> period = periodService.findByPeriodId(periodId);
		Period periodObj = new Period();
		
		for (Object obj : period) {
			periodObj = (Period) obj;
		}

		nextSchedule(periodObj);
	}

	@Override
	@Scheduled(cron = "0 0 0 * * ?")
	public void insertQrtSchedule() {
		List<Period> periodList = periodService.findAll();
		for(int i=0; i<periodList.size(); i++) {
			nextSchedule(periodList.get(i));
		}
	}

	@Transactional
	public void nextSchedule(Period periodObj) {
		if (periodObj.getFreq() == null || periodObj.getCycle() == null) {
			periodObj.setFreq(1L);
			periodObj.setCycle("D");
			periodObj.setEndTime(periodObj.getStartTime());
		}

		// 시작날짜(시간)
		LocalDateTime startDateTime = periodObj.getStartTime();
		// 종료날짜(시간)
		LocalDateTime endDateTime = null;
		if(periodObj.getEndTime() != null) {
			endDateTime = periodObj.getEndTime();
		}
		// 마지막 스케줄 날짜(시간)

		LocalDateTime dateCursor;
		if(periodObj.getLastTime() == null) {
			dateCursor = startDateTime;
		}
		else {
			dateCursor = periodObj.getLastTime();
		}

		// 1년 후의 날짜
		LocalDateTime maxDate = DateUtil.getNextDate(startDateTime, 1L, "Y");

		// 현재시간보다 종료시간이 작을 경우에 다음 스케쥴 생성 후 INSERT
		// 또는 마지막 주기발행 1년 이후 까지의 일정 발행
		while ((endDateTime == null || !dateCursor.isAfter(endDateTime)) && !dateCursor.isAfter(maxDate)) {
			Schedule scheduleObj = new Schedule();
			scheduleObj.setSchdTime(dateCursor);
			scheduleObj.setPeriodId(periodObj.getPeriodId());
			// 알람발행여부
			if("Y".equals(periodObj.getIsAlarm())) {
				scheduleObj.setIsExe("N");
			}
			else {
				scheduleObj.setIsExe("Y");
			}

			save(scheduleObj);

			// 다음 날짜로 이동
			dateCursor = DateUtil.getNextDate(dateCursor, periodObj.getFreq(), periodObj.getCycle());
		}
		periodObj.setLastTime(dateCursor);
		periodService.save(periodObj);
	}

	@Override
	public List<Date> findByDate(Long userId, String date) {
		return scheduleRepository.findByDate(userId, date);
	}

	@Override
	public ScheduleDTO getFirstScheduleInFuture(Long userId) {

		Map<String, Object> result = scheduleRepository.getFirstScheduleInFuture(userId);

		ScheduleDTO scheduleDTO = ScheduleDTO.builder()
				.schdId(Long.parseLong(String.valueOf(result.get("schdId"))))
				.schdTime(((Timestamp)result.get("schdTime")).toLocalDateTime())
				.isExe((String)result.get("isExe"))
				.periodName((String)result.get("periodName"))
				.periodRemark((String)result.get("periodRemark"))
				.userId(Long.parseLong(String.valueOf(result.get("userId"))))
				.regUserId(Long.parseLong(String.valueOf(result.get("regUserId"))))
				.periodId(Long.parseLong(String.valueOf(result.get("periodId"))))
				.manager((String)result.get("manager"))
				.place((String)result.get("place"))
				.build();

		return scheduleDTO;
	}

	@Override
	public ScheduleCount getCountScheduleInDaysByUserId(String date, Long userId) {
		Map<String, Object> result = scheduleRepository.getCountScheduleInDaysByUserId(date, userId);

		ScheduleCount scheduleCount = ScheduleCount.builder()
				.count(Integer.parseInt(String.valueOf(result.get("count"))))
				.build();

		return scheduleCount;
	}

	@Override
	public List<ScheduleDTO> getScheduleOfDayAndUserId(String day, Long userId) {
		List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
		List<Map<String, Object>> list = scheduleRepository.getScheduleOfDayAndUserId(day, userId);

		for(Map<String, Object> result : list){
			ScheduleDTO scheduleDTO = ScheduleDTO.builder()
					.schdId(Long.parseLong(String.valueOf(result.get("schdId"))))
					.schdTime(((Timestamp)result.get("schdTime")).toLocalDateTime())
					.isExe((String)result.get("isExe"))
					.periodName((String)result.get("periodName"))
					.periodRemark((String)result.get("periodRemark"))
					.userId(Long.parseLong(String.valueOf(result.get("userId"))))
					.regUserId(Long.parseLong(String.valueOf(result.get("regUserId"))))
					.periodId(Long.parseLong(String.valueOf(result.get("periodId"))))
					.manager((String)result.get("manager"))
					.place((String)result.get("place"))
					.periodType((String)result.get("periodType"))
					.name((String)result.get("name"))
					.remark((String)result.get("remark"))
					.startTime(result.get("startTime")==null?null:((Timestamp)result.get("startTime")).toLocalDateTime())
					.endTime(result.get("endTime")==null?null:((Timestamp)result.get("endTime")).toLocalDateTime())
					.lastTime(result.get("lastTime")==null?null:((Timestamp)result.get("lastTime")).toLocalDateTime())
					.freq(result.get("freq")==null?null:Long.parseLong(String.valueOf(result.get("freq"))))
					.cycle((String)result.get("cycle"))
					.isUse((String)result.get("isUse"))
					.isAlarm((String)result.get("isAlarm"))
					.build();
			scheduleDTOS.add(scheduleDTO);
		}
		return scheduleDTOS;
	}

	@Override
	public ScheduleDTO insertScheduleForOnce(ScheduleDTO scheduleDTO) {
		//주기 넣기
		Period period = Period.builder()
				.periodType("일정")
				.name(scheduleDTO.getPeriodName())
				.remark(scheduleDTO.getPeriodRemark())
				.startTime(scheduleDTO.getSchdTime())
				.endTime(scheduleDTO.getSchdTime())
				.lastTime(scheduleDTO.getSchdTime())
				.userId(scheduleDTO.getUserId())
				.regUserId(scheduleDTO.getRegUserId())
				.isUse("Y")
				.isAlarm("Y")
				.manager(scheduleDTO.getManager())
				.place(scheduleDTO.getPlace())
				.build();

		Period newPeriod = periodService.save(period).get();

		//스케쥴 넣기
		Schedule schedule = Schedule.builder()
				.periodId(newPeriod.getPeriodId())
				.schdTime(scheduleDTO.getSchdTime())
				.isExe("N")
				.build();

		Schedule newSchedule = scheduleRepository.save(schedule);
		ScheduleDTO newScheduleDTO = ScheduleDTO.builder()
				.schdId(newSchedule.getSchdId())
				.schdTime(newSchedule.getSchdTime())
				.isExe(newSchedule.getIsExe())
				.periodName(newPeriod.getName())
				.periodRemark(newPeriod.getRemark())
				.userId(newPeriod.getUserId())
				.regUserId(newPeriod.getRegUserId())
				.manager(newPeriod.getManager())
				.place(newPeriod.getPlace())
				.build();

		return newScheduleDTO;
	}

	@Override
	@Scheduled(cron = "0 * * * * ?")
	public void alertScheduleMessage() {
		List<Map<String, Object>> scheduleList = scheduleRepository.findMessageTarget();

		for(Map scheduleMap:scheduleList){
			ResponseEntity<Map> result = RestTemplateUtil.alertScheduleMessage(scheduleMap);
			if(result.getStatusCode().value()==200) {
				scheduleRepository.updateIsExe(Long.parseLong(scheduleMap.get("schdId").toString()));
			}
		}
	}

	@Override
	public List<ScheduleDTO> getScheduleOfFromToAndUserId(String fromDate, String toDate, Long userId) {
		List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
		List<Map<String, Object>> list = scheduleRepository.getScheduleOfFromToAndUserId(fromDate, toDate, userId);

		for(Map<String, Object> result : list){
			ScheduleDTO scheduleDTO = ScheduleDTO.builder()
					.schdId(Long.parseLong(String.valueOf(result.get("schdId"))))
					.schdTime(((Timestamp)result.get("schdTime")).toLocalDateTime())
					.isExe((String)result.get("isExe"))
					.periodName((String)result.get("periodName"))
					.periodRemark((String)result.get("periodRemark"))
					.userId(Long.parseLong(String.valueOf(result.get("userId"))))
					.regUserId(Long.parseLong(String.valueOf(result.get("regUserId"))))
					.periodId(Long.parseLong(String.valueOf(result.get("periodId"))))
					.manager((String)result.get("manager"))
					.place((String)result.get("place"))
					.periodType((String)result.get("periodType"))
					.name((String)result.get("name"))
					.remark((String)result.get("remark"))
					.startTime(result.get("startTime")==null?null:((Timestamp)result.get("startTime")).toLocalDateTime())
					.endTime(result.get("endTime")==null?null:((Timestamp)result.get("endTime")).toLocalDateTime())
					.lastTime(result.get("lastTime")==null?null:((Timestamp)result.get("lastTime")).toLocalDateTime())
					.freq(result.get("freq")==null?null:Long.parseLong(String.valueOf(result.get("freq"))))
					.cycle((String)result.get("cycle"))
					.isUse((String)result.get("isUse"))
					.isAlarm((String)result.get("isAlarm"))
					.build();
			scheduleDTOS.add(scheduleDTO);
		}
		return scheduleDTOS;
	}

	@Override
	public List<ScheduleDTO> getScheduleOfFromToAndUserIdOrderByPeriod(String fromDate, String toDate, Long userId) {
		List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
		List<Map<String, Object>> list = scheduleRepository.getScheduleOfFromToAndUserIdOrderByPeriod(fromDate, toDate, userId);

		for(Map<String, Object> result : list){
			ScheduleDTO scheduleDTO = ScheduleDTO.builder()
					.schdId(Long.parseLong(String.valueOf(result.get("schdId"))))
					.schdTime(((Timestamp)result.get("schdTime")).toLocalDateTime())
					.isExe((String)result.get("isExe"))
					.periodName((String)result.get("periodName"))
					.periodRemark((String)result.get("periodRemark"))
					.userId(Long.parseLong(String.valueOf(result.get("userId"))))
					.regUserId(Long.parseLong(String.valueOf(result.get("regUserId"))))
					.periodId(Long.parseLong(String.valueOf(result.get("periodId"))))
					.manager((String)result.get("manager"))
					.place((String)result.get("place"))
					.periodType((String)result.get("periodType"))
					.name((String)result.get("name"))
					.remark((String)result.get("remark"))
					.startTime(result.get("startTime")==null?null:((Timestamp)result.get("startTime")).toLocalDateTime())
					.endTime(result.get("endTime")==null?null:((Timestamp)result.get("endTime")).toLocalDateTime())
					.lastTime(result.get("lastTime")==null?null:((Timestamp)result.get("lastTime")).toLocalDateTime())
					.freq(result.get("freq")==null?null:Long.parseLong(String.valueOf(result.get("freq"))))
					.cycle((String)result.get("cycle"))
					.isUse((String)result.get("isUse"))
					.isAlarm((String)result.get("isAlarm"))
					.build();
			scheduleDTOS.add(scheduleDTO);
		}
		return scheduleDTOS;
	}

	@Override
	public void deletesScheduleByPeriodId(Long periodId) {
		scheduleRepository.deleteScheduleByPeriodId(periodId);
	}

	@Override
	public List<ScheduleDTO> getScheduleOfFromToAndPeriodId(String fromDate, String toDate, Long periodId) {
		List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
		List<Map<String, Object>> list = scheduleRepository.getScheduleOfFromToAndPeriodId(fromDate, toDate, periodId);

		for(Map<String, Object> result : list){
			ScheduleDTO scheduleDTO = ScheduleDTO.builder()
					.schdId(Long.parseLong(String.valueOf(result.get("schdId"))))
					.schdTime(((Timestamp)result.get("schdTime")).toLocalDateTime())
					.isExe((String)result.get("isExe"))
					.periodName((String)result.get("periodName"))
					.periodRemark((String)result.get("periodRemark"))
					.userId(Long.parseLong(String.valueOf(result.get("userId"))))
					.regUserId(Long.parseLong(String.valueOf(result.get("regUserId"))))
					.periodId(Long.parseLong(String.valueOf(result.get("periodId"))))
					.manager((String)result.get("manager"))
					.place((String)result.get("place"))
					.periodType((String)result.get("periodType"))
					.name((String)result.get("name"))
					.remark((String)result.get("remark"))
					.startTime(result.get("startTime")==null?null:((Timestamp)result.get("startTime")).toLocalDateTime())
					.endTime(result.get("endTime")==null?null:((Timestamp)result.get("endTime")).toLocalDateTime())
					.lastTime(result.get("lastTime")==null?null:((Timestamp)result.get("lastTime")).toLocalDateTime())
					.freq(result.get("freq")==null?null:Long.parseLong(String.valueOf(result.get("freq"))))
					.cycle((String)result.get("cycle"))
					.isUse((String)result.get("isUse"))
					.isAlarm((String)result.get("isAlarm"))
					.build();
			scheduleDTOS.add(scheduleDTO);
		}
		return scheduleDTOS;
	}
}
