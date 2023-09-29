package com.example.AttendEasy.Times;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TimesRepository extends JpaRepository<Times,Long> {

    @Query(nativeQuery = true,value = "SELECT * FROM times WHERE employee_id :=employeeId")
    List<Times> getAllTimes(@Param("employeeId") long employeeId);

//    @Query(nativeQuery = true,value = "SELECT * FROM times WHERE employee_id :=employeeId AND type == \"IN\"")
//    List<Times> getAllCheckIns(@Param("employeeId") long employeeId);
//
//    @Query(nativeQuery = true,value = "SELECT * FROM times WHERE employee_id :=employeeId AND type == \"OUT\"")
//    List<Times> getAllCheckOuts(@Param("employeeId") long employeeId);

//    @Query(nativeQuery = true,value = "SELECT * FROM times WHERE employee_id :=employeeId ORDER BY time DESC LIMIT 1")
//    Optional<Times> getLatestActivity(@Param("employeeId") long employeeId);

    @Query(nativeQuery = true,value = "SELECT * FROM times WHERE employee_id = :employeeId ORDER BY in_time DESC LIMIT 1")
    Optional<Times> getLastCheckIn(@Param("employeeId") long employeeId);

    @Query(nativeQuery = true, value = "WITH LatestTimes AS (\n" +
            "    SELECT\n" +
            "        employee_id,\n" +
            "        MAX(in_Time) AS latest_in_time,\n" +
            "        MAX(out_Time) AS latest_out_time\n" +
            "    FROM times\n" +
            "    WHERE employee_id = :employeeId\n" +
            "    GROUP BY employee_id\n" +
            ")\n" +
            "\n" +
            "SELECT\n" +
            "    lt.employee_id,\n" +
            "    CASE\n" +
            "        WHEN lt.latest_in_time > lt.latest_out_time || lt.latest_out_time IS NULL THEN 'IN'\n" +
            "        ELSE 'OUT'\n" +
            "        END AS latest_activity\n" +
            "FROM LatestTimes lt;")
    Optional<String> getLatestActivity(@Param("employeeId") long employeeId);
}
