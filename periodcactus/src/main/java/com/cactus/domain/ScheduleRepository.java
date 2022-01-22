package com.cactus.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "select * from schedule x where x.user_id = :userId")
	List<Schedule> findByUserId(Long userId);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "delete from schedule where schd_id = :scheduleId")
	void deleteSchedule(Long scheduleId);

	@Modifying
	@Transactional
	@Query(nativeQuery = true,
			value = "SELECT x.schd_time"
					+ "  FROM schedule x"
					+ " INNER JOIN period y"
					+ "    ON x.period_id = y.period_id"
					+ " WHERE x.schd_time > date(now())"
					+ "   AND y.user_id = :userId"
					+ "   AND x.schd_time < :date" )
	List<Date> findByDate(@Param("userId") Long userId, @Param("date") String date);

	@Transactional
	@Query(nativeQuery = true,
			value = "SELECT x.schd_id as schdId, " +
					"		x.schd_time as schdTime," +
					"		x.is_exe as isExe," +
					"		y.name as periodName," +
					"		y.remark as periodRemark," +
					"		y.user_id as userId," +
					"		y.reg_user_id as regUserId," +
					"		x.period_id as periodId," +
					"		y.manager as manager," +
					"		y.place as place"
					+ "  FROM schedule x"
					+ " INNER JOIN period y"
					+ "    ON x.period_id = y.period_id"
					+ " WHERE x.schd_time >= date(now())"
					+ "   AND y.user_id = :userId" +
					"     ORDER BY x.schd_time " +
					"	LIMIT 1" )
	Map<String, Object> getFirstScheduleInFuture(@Param("userId") Long userId);

	@Transactional
	@Query(nativeQuery = true,
			value = "SELECT x.schd_id as schdId, " +
					"		x.schd_time as schdTime," +
					"		x.is_exe as isExe," +
					"		y.name as periodName," +
					"		y.remark as periodRemark," +
					"		y.user_id as userId," +
					"		y.reg_user_id as regUserId," +
					"		y.manager as manager," +
					"		y.period_id as periodId," +
					"		y.period_type as periodType," +
					"		y.name as name," +
					"		y.remark as remark," +
					"		y.start_Time as startTime," +
					"		y.end_Time as endTime," +
					"		y.last_Time as lastTime," +
					"		y.freq as freq," +
					"		y.cycle as cycle," +
					"		y.is_use as isUse," +
					"		y.is_alarm as isAlarm," +
					"		y.place as place"
					+ "  FROM schedule x"
					+ " INNER JOIN period y"
					+ "    ON x.period_id = y.period_id"
					+ " WHERE DATE_FORMAT(DATE_ADD(x.schd_time, INTERVAL 9 HOUR), '%Y%m%d')  = :schdDate "
					+ "   AND y.user_id = :userId" +
					"     ORDER BY x.schd_time asc" )
	List<Map<String, Object>> getScheduleOfDayAndUserId(@Param("schdDate") String schdDate, @Param("userId") Long userId);

	@Transactional
	@Query(nativeQuery = true,
			value = "SELECT count(*) as count"
					+ "  FROM schedule x"
					+ " INNER JOIN period y"
					+ "    ON x.period_id = y.period_id"
					+ " WHERE DATE_FORMAT(x.schd_time, '%Y%m%d')  = DATE_FORMAT(:schdDate, '%Y%m%d')"
					+ "   AND y.user_id = :userId" )
	Map<String, Object> getCountScheduleInDaysByUserId(@Param("schdDate") String schdDate, @Param("userId") Long userId);

	@Transactional
	@Query(nativeQuery = true,
			value = "SELECT x.schd_id as schdId, " +
					"		x.period_id as periodId," +
					"		x.schd_time as schdTime," +
					"		x.is_exe as isExe," +
					"		y.name as title," +
					"		y.remark as contents," +
					"		y.user_id as userId"
					+ "  FROM schedule x"
					+ " INNER JOIN period y"
					+ "    ON x.period_id = y.period_id"
					+ " WHERE x.schd_time <= DATE_ADD(DATE_ADD(NOW(), INTERVAL 9 HOUR), INTERVAL -30 MINUTE) "
					+ "   AND x.is_exe = 'N'" )
	List<Map<String, Object>> findMessageTarget();

	@Modifying
	@Transactional
	@Query(nativeQuery = true,
			value = "UPDATE schedule SET is_exe = 'Y'"
					+ " WHERE schd_id = :schdId " )
	void updateIsExe(@Param("schdId") Long schdId);

	@Transactional
	@Query(nativeQuery = true,
			value = "SELECT x.schd_id as schdId, " +
					"		x.schd_time as schdTime," +
					"		x.is_exe as isExe," +
					"		y.name as periodName," +
					"		y.remark as periodRemark," +
					"		y.user_id as userId," +
					"		y.reg_user_id as regUserId," +
					"		y.manager as manager," +
					"		y.period_id as periodId," +
					"		y.period_type as periodType," +
					"		y.name as name," +
					"		y.remark as remark," +
					"		y.start_Time as startTime," +
					"		y.end_Time as endTime," +
					"		y.last_Time as lastTime," +
					"		y.freq as freq," +
					"		y.cycle as cycle," +
					"		y.is_use as isUse," +
					"		y.is_alarm as isAlarm," +
					"		y.place as place"
					+ "  FROM schedule x"
					+ " INNER JOIN period y"
					+ "    ON x.period_id = y.period_id"
					+ " WHERE DATE_FORMAT(DATE_ADD(x.schd_time, INTERVAL 9 HOUR), '%Y%m%d')  >= :fromDate "
					+ "   AND DATE_FORMAT(DATE_ADD(x.schd_time, INTERVAL 9 HOUR), '%Y%m%d')  <= :toDate "
					+ "   AND y.user_id = :userId" +
					"     ORDER BY x.schd_time asc" )
	List<Map<String, Object>> getScheduleOfFromToAndUserId(String fromDate, String toDate, Long userId);

	@Transactional
	@Query(nativeQuery = true,
			value = "SELECT x.schd_id as schdId, " +
					"		x.schd_time as schdTime," +
					"		x.is_exe as isExe," +
					"		y.name as periodName," +
					"		y.remark as periodRemark," +
					"		y.user_id as userId," +
					"		y.reg_user_id as regUserId," +
					"		y.manager as manager," +
					"		y.period_id as periodId," +
					"		y.period_type as periodType," +
					"		y.name as name," +
					"		y.remark as remark," +
					"		y.start_Time as startTime," +
					"		y.end_Time as endTime," +
					"		y.last_Time as lastTime," +
					"		y.freq as freq," +
					"		y.cycle as cycle," +
					"		y.is_use as isUse," +
					"		y.is_alarm as isAlarm," +
					"		y.place as place"
					+ "  FROM schedule x"
					+ " INNER JOIN period y"
					+ "    ON x.period_id = y.period_id"
					+ " WHERE DATE_FORMAT(DATE_ADD(x.schd_time, INTERVAL 9 HOUR), '%Y%m%d')  >= :fromDate "
					+ "   AND DATE_FORMAT(DATE_ADD(x.schd_time, INTERVAL 9 HOUR), '%Y%m%d')  <= :toDate "
					+ "   AND y.user_id = :userId" +
					"     ORDER BY x.period_id asc" )
	List<Map<String, Object>> getScheduleOfFromToAndUserIdOrderByPeriod(String fromDate, String toDate, Long userId);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "delete from schedule where period_id = :periodId")
    void deleteScheduleByPeriodId(Long periodId);

	@Transactional
	@Query(nativeQuery = true,
			value = "SELECT x.schd_id as schdId, " +
					"		x.schd_time as schdTime," +
					"		x.is_exe as isExe," +
					"		y.name as periodName," +
					"		y.remark as periodRemark," +
					"		y.user_id as userId," +
					"		y.reg_user_id as regUserId," +
					"		y.manager as manager," +
					"		y.period_id as periodId," +
					"		y.period_type as periodType," +
					"		y.name as name," +
					"		y.remark as remark," +
					"		y.start_Time as startTime," +
					"		y.end_Time as endTime," +
					"		y.last_Time as lastTime," +
					"		y.freq as freq," +
					"		y.cycle as cycle," +
					"		y.is_use as isUse," +
					"		y.is_alarm as isAlarm," +
					"		y.place as place"
					+ "  FROM schedule x"
					+ " INNER JOIN period y"
					+ "    ON x.period_id = y.period_id"
					+ " WHERE DATE_FORMAT(DATE_ADD(x.schd_time, INTERVAL 9 HOUR), '%Y%m%d')  >= :fromDate "
					+ "   AND DATE_FORMAT(DATE_ADD(x.schd_time, INTERVAL 9 HOUR), '%Y%m%d')  <= :toDate "
					+ "   AND y.period_id = :periodId" +
					"     ORDER BY x.schd_time asc" )
    List<Map<String, Object>> getScheduleOfFromToAndPeriodId(String fromDate, String toDate, Long periodId);
}
