package com.Thomas.Project2.demo.service;

import com.Thomas.Project2.demo.dao.EmployeeDAO;
import com.Thomas.Project2.demo.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
//annotate service bean
@Service
public class EmployeeServiceImpl implements EmployeeService {

    //define EmployeeDAO
    private EmployeeDAO employeeDAO;

    //inject EmployeeDAO in constructor
    @Autowired
    EmployeeServiceImpl(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Override
    public List<Employee> findAll() {
        return  employeeDAO.findAll();
    }

    @Override
    public Employee findById(int id) {
        return employeeDAO.findById(id);
    }

    @Transactional
    @Override
    public Employee save(Employee employee) {
        return employeeDAO.save(employee);
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        employeeDAO.deleteById(id);

    }
}
