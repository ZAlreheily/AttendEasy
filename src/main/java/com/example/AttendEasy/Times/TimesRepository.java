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

    @Query(nativeQuery = true,value = "SELECT * FROM times WHERE employee_id :=employeeId AND type == \"IN\"")
    List<Times> getAllCheckIns(@Param("employeeId") long employeeId);

    @Query(nativeQuery = true,value = "SELECT * FROM times WHERE employee_id :=employeeId AND type == \"OUT\"")
    List<Times> getAllCheckOuts(@Param("employeeId") long employeeId);

    @Query(nativeQuery = true,value = "SELECT * FROM times WHERE employee_id :=employeeId ORDER BY time DESC LIMIT 1")
    Optional<Times> getLatestActivity(@Param("employeeId") long employeeId);
}
