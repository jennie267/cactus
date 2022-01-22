package com.cactus.controller;

import com.cactus.domain.Schedule;
import com.cactus.domain.dto.ScheduleCount;
import com.cactus.domain.dto.ScheduleDTO;
import com.cactus.service.PeriodService;
import com.cactus.service.ScheduleService;
import com.cactus.util.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Api(value = "일정 Controller")
@Controller
@RestController
@RequestMapping("/period/schedule")
public class ScheduleController {
	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private PeriodService periodService;
	
	@ApiOperation(value = "모든 일정 목록 조회")
	@GetMapping("/")
	public List<Schedule> scheduleList() {
		return scheduleService.findAll();
	}
	
	@ApiOperation(value = "유저 일정 목록 조회")
	@GetMapping("/user/{userId}")
	public List<Schedule> getScheduleByUserId(@PathVariable Long userId) {
		return scheduleService.findByUserId(userId);
	}
	
	@ApiOperation(value = "일정 등록")
	@PostMapping()
	public Optional<Schedule> save(@PathVariable Schedule scheduleObj) {
		return scheduleService.save(scheduleObj);
	}
	
	@ApiOperation(value = "특정 주기 일정 등록")
	@PostMapping("/period/{periodId}")
	public void insertScheduleByUserId(@PathVariable Long periodId) {
		scheduleService.scheduleByPeriodId(periodId);
	}

	@ApiOperation(value = "일정 삭제")
	@PostMapping("/delete/{ScheduleId}")
	public String deletesSchedule(@PathVariable Long ScheduleId) {
		scheduleService.deleteSchedule(ScheduleId);
		return "삭제되었습니다.";
	}

	@ApiOperation(value = "특정 주기 일정 삭제")
	@PostMapping("/period/delete/{periodId}")
	public String deletesScheduleByPeriodId(@PathVariable Long periodId) {
		scheduleService.deletesScheduleByPeriodId(periodId);
		return "삭제되었습니다.";
	}
	
	@ApiOperation(value = "일자별 스케쥴")
	@GetMapping("/{userId}/{date}")
	public List<Date> getSchedulebyDate(@PathVariable Long userId, @PathVariable String date) {
		return scheduleService.findByDate(userId, date);
	}
	
	@ApiOperation(value = "전체 주기 일정 등록")
	@PostMapping("/period")
	public void insertQrtSchedule() {
		scheduleService.insertQrtSchedule();
	}

	@GetMapping(value = "/first/{userId}")
	public ScheduleDTO getFirstScheduleInFuture(@PathVariable Long userId){

		return scheduleService.getFirstScheduleInFuture(userId);
	}

	@GetMapping(value = "/days/user/{days}/{userId}")
	public Map getCountScheduleInDaysByUserId(@PathVariable int days, @PathVariable Long userId){

		Calendar cal = Calendar.getInstance();
		List<ScheduleCount> scheduleCounts = new ArrayList<>();

		for (int i = 0; i < days; i++) {
			cal.add(Calendar.DATE, i);
			LocalDateTime localDate = DateUtil.toLocalDateTime(cal);
			;
			ScheduleCount scheduleCount = scheduleService.getCountScheduleInDaysByUserId((DateTimeFormatter.ISO_LOCAL_DATE)
					.format(localDate), userId);
			scheduleCount.setDate(localDate);

			scheduleCounts.add(scheduleCount);
			cal.add(Calendar.DATE, -i);
		}

		Map<String, List<ScheduleCount>> scheduleCountMap = new HashMap<>();
		scheduleCountMap.put("scheduleCounts", scheduleCounts);

		return scheduleCountMap;
	}


	@GetMapping(value = "/day/user/{day}/{userId}")
	public Map getScheduleOfDayAndUserId(@PathVariable String day, @PathVariable Long userId){
		List<ScheduleDTO> schedules = scheduleService.getScheduleOfDayAndUserId(day, userId);

		Map<String, List<ScheduleDTO>> scheduleCountsMap = new HashMap<>();
		scheduleCountsMap.put("schedules", schedules);

		return scheduleCountsMap;
	}

	@GetMapping(value = "/from/to/user/{fromDate}/{toDate}/{userId}")
	public Map getScheduleOfFromToAndUserId(@PathVariable String fromDate, @PathVariable String toDate, @PathVariable Long userId){
		List<ScheduleDTO> schedules = scheduleService.getScheduleOfFromToAndUserId(fromDate, toDate, userId);

		Map<String, List<ScheduleDTO>> scheduleCountsMap = new HashMap<>();
		scheduleCountsMap.put("schedules", schedules);

		return scheduleCountsMap;
	}

	@GetMapping(value = "/from/to/period/{fromDate}/{toDate}/{periodId}")
	public Map getScheduleOfFromToAndPeriodId(@PathVariable String fromDate, @PathVariable String toDate, @PathVariable Long periodId){
		List<ScheduleDTO> schedules = scheduleService.getScheduleOfFromToAndPeriodId(fromDate, toDate, periodId);

		Map<String, List<ScheduleDTO>> scheduleCountsMap = new HashMap<>();
		scheduleCountsMap.put("schedules", schedules);

		return scheduleCountsMap;
	}

	@GetMapping(value = "/from/to/user/order/{fromDate}/{toDate}/{userId}")
	public Map getScheduleOfFromToAndUserIdOrderByPeriod(@PathVariable String fromDate, @PathVariable String toDate, @PathVariable Long userId){
		List<ScheduleDTO> schedules = scheduleService.getScheduleOfFromToAndUserIdOrderByPeriod(fromDate, toDate, userId);

		Map<String, List<ScheduleDTO>> scheduleCountsMap = new HashMap<>();
		scheduleCountsMap.put("schedules", schedules);

		return scheduleCountsMap;
	}

	@PostMapping(value = "/add/once")
	public ScheduleDTO insertScheduleForOnce(@RequestBody ScheduleDTO scheduleDTO){

		ScheduleDTO newScheduleDTO = scheduleService.insertScheduleForOnce(scheduleDTO);

		return newScheduleDTO;
	}

}
