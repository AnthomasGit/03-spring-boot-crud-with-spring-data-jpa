package com.Thomas.Project2.demo.dao;

import com.Thomas.Project2.demo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//@RepositoryRestResource(path="members")
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    //that's it, CRUD methods included!!
}
