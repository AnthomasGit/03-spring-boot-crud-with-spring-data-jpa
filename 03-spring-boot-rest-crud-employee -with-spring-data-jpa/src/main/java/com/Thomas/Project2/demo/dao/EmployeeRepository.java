package com.Thomas.Project2.demo.dao;

import com.Thomas.Project2.demo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    //that's it, CRUD methods included!!
}
