package com.Thomas.Project2.demo.dao;

import com.Thomas.Project2.demo.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDAOJpaImpl implements EmployeeDAO {
    //define field for entity manager
    private EntityManager em;

    //set up constructor injection
    @Autowired
    public EmployeeDAOJpaImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Employee> findAll() {
        //create a query
        TypedQuery<Employee> query = em.createQuery("from Employee", Employee.class);

        //execute query and get result list
        List<Employee> theEmployees = query.getResultList();

        //return the results
        return theEmployees;
    }

    @Override
    public Employee findById(int id) {
        Employee theEmployee = em.find(Employee.class, id);
        return theEmployee;
    }

    @Override
    public Employee save(Employee employee) {
        Employee theEmployee = em.merge(employee);
        return theEmployee;
    }

    @Override
    public void deleteById(int id) {
        Employee theEmployee = em.find(Employee.class, id);
        em.remove(theEmployee);

    }

}
