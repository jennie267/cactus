package com.cactus.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cactus.domain.Period;
import com.cactus.domain.PeriodRepository;

@Service
public class PeriodServiceImpl implements PeriodService {
	@Autowired
	PeriodRepository periodRepository;
	@Autowired
	ScheduleService scheduleService;
	
	public List<Period> findAll() {
		return periodRepository.findAll();
	}

	public List<Period> findByPeriodId(Long periodId) {
		return periodRepository.findByPeriodId(periodId);
	}
	
	public List<Period> findByUserId(Long userId) {
		return periodRepository.findByUserId(userId);
	}

	public Optional<Period> save(Period periodObj) {
		return Optional.of(periodRepository.save(periodObj));
	}
	
	public List<Period> findByDate(Long userId, String date) {
		return periodRepository.findByDate(userId, date.replaceAll("[^0-9]", ""));
	}
	
	public void deleteByPeriodId(Long periodId) {
		periodRepository.deleteByPeriodId(periodId);
	}
}
