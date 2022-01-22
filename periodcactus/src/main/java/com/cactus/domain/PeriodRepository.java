package com.cactus.domain;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodRepository extends JpaRepository<Period, Long> {
	
    List<Period> findByPeriodId(Long periodId);
    
    List<Period> findByUserId(Long userId);
    
    @Modifying
    @Transactional
    @Query(nativeQuery = true, 
    			 value = "SELECT *"
    			 	   + "  FROM period x"
    			 	   + " WHERE x.user_id = :userId"
    			 	   + "   AND x.period_id IN (SELECT a.period_id"
    			 	   + "                         FROM schedule a"
    			 	   + "                        WHERE a.schd_time = :date)" )
    List<Period> findByDate(@Param("userId") Long userId, @Param("date") String date);
	    
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from period x where x.period_id=:periodId")
	public void deleteByPeriodId(Long periodId);
    
    @Modifying
    @Transactional
    @Query(nativeQuery = true, 
    			 value = "UPDATE period x "
    			 	   + "   SET period_type_id = :periodTypeId"
    			 	   + "	   , name = :name"
    			 	   + "	   , remark = :remark"
    			 	   + "	   , start_time = :startTime"
    			 	   + "	   , end_time   = :endTime"
    			 	   + "	   , last_time  = :lastTime"
    			 	   + "	   , freq		= :freq"
    			 	   + "	   , cycle		= :cycle"
    			 	   + "	   , user_id	= :userId"
    			 	   + "	   , is_use		= :isUse"
    			 	   + "	   , is_alarm	= :isAlarm"
    			 	   + "	   , manager	= :manager"
    			 	   + "	   , place		= :place"
    			 	   + " WHERE period_id  = :periodId" )
	public void updateByPeriodId(Period periodId);
}
