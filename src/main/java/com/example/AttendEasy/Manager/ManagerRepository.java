package com.example.AttendEasy.Manager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ManagerRepository extends JpaRepository<Manager,Long> {

}
