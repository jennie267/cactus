package com.cactus.service;

import java.util.List;
import java.util.Optional;

import com.cactus.domain.Period;

public interface PeriodService {
	List<Period> findAll();
	
	List<Period> findByPeriodId(Long periodId);
	
	List<Period> findByUserId(Long userId);
	
	List<Period> findByDate(Long user, String date);
	
	Optional<Period> save(Period periodObj);
	
	void deleteByPeriodId(Long periodId);
}
