package com.cactus.controller;

import com.cactus.domain.Period;
import com.cactus.service.PeriodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Api(value = "주기 Controller")
@Controller
@RestController
@RequestMapping("/period")
public class PeriodController {
	@Autowired
	private PeriodService periodService;
	
	@ApiOperation(value = "모든 주기 목록 조회")
	@GetMapping
	public List<Period> periodList() {
		return periodService.findAll();
	}
	@ApiOperation(value = "특정 주기 조회")
	@GetMapping("/{periodId}")
	public List<Period> getPeriodById(@PathVariable Long periodId) {
		return periodService.findByPeriodId(periodId);
	}
	
	@ApiOperation(value = "사용자로 주기 조회")
	@GetMapping("/user/{userId}")
	public List<Period> getPeriodByUerId(@PathVariable Long userId) {
		return periodService.findByUserId(userId);
	}

	@ApiOperation(value = "날짜 별 사용자로 주기 조회")
	@GetMapping("/user/date/{userId}/{date}")
	public Map<String, List<Period>> getPeriodByDate(@PathVariable Long userId, @PathVariable String date) {
		List<Period> periods = periodService.findByDate(userId, date);

		Map<String, List<Period>> periodMap = new HashMap<>();
		periodMap.put("period", periods);

		return periodMap;
	}
	
	@ApiOperation(value = "주기 등록")
	@PostMapping
	public Optional<Period> insertPeriod(@RequestBody Period periodObj) {
		return periodService.save(periodObj);
	}
	
	@ApiOperation(value = "주기 삭제")
	@DeleteMapping("/{id}")
	public String deletePeriodById(@PathVariable Long periodId) {
		periodService.deleteByPeriodId(periodId);
		return "삭제되었습니다.";
	}
	
	@ApiOperation(value = "주기 수정")
	@PostMapping("/update/{periodId}")
	public void updatePeriod(@RequestBody Period periodObj) {
		periodService.save(periodObj);
	}
	
	@ApiOperation(value = "마지막 일정 발행일자 업데이트")
	@PostMapping("/update/{periodId}/{lastTime}")
	public void updateLastTimeforPeriod(@PathVariable Long periodId, @PathVariable String lastTime) {
		
	}
}
