package com.hs.hscontrolinformation.dao;

import com.hs.hscontrolinformation.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeDao extends JpaRepository<Employee, Long> {
}
