package com.Thomas.Project2.demo.rest;

import com.Thomas.Project2.demo.entity.Employee;
import com.Thomas.Project2.demo.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private EmployeeService employeeService;
    private ObjectMapper objectMapper;

    //constructor injections
    @Autowired
    public EmployeeRestController(EmployeeService employeeService, ObjectMapper objectMapper) {

        this.employeeService = employeeService;
        this.objectMapper = objectMapper;
    }

    //expose "/employee" and return a list of employees
    @GetMapping("/employees")
    public List<Employee> employeeList() {
        return employeeService.findAll();
    }

    //expose "/employee/{employeeid}" to return employee by id
    @GetMapping("/employees/{employeeid}")
    public Employee getEmployee(@PathVariable("employeeid") Integer employeeid) {
        Employee theEmployee = employeeService.findById(employeeid);

        if (theEmployee == null) {
            throw new RuntimeException("Employee with id: " + employeeid + " not found");
        }
        return theEmployee;
    }

    //@Post create new employee
    @PostMapping("employees")
    //Request JSON to turn into object
    public Employee save(@RequestBody Employee employee) {
        //set id to "0" so that it saves and not update
        employee.setId(0);
        Employee dbEmployee = employee;
        return employeeService.save(dbEmployee);
    }

    //add mapping @Put - update existing employee
    @PutMapping("/employees")
    public Employee update(@RequestBody Employee employee) {
        Employee dbEmployee = employeeService.save(employee);
        return dbEmployee;
    }

    //add mapping for @Patch employees/{EmployeeId} - update existing employee fields
    @PatchMapping("employees/{EmployeeId}")
    public Employee patchEmployee(@PathVariable("EmployeeId") int employeeId, @RequestBody Map<String, Object> patchPayload) {
        Employee tempEmployee = employeeService.findById(employeeId);

        //throw exception if null
        if (tempEmployee == null) {
            throw new RuntimeException("Employee with id: " + employeeId + " not found");
        }

        //throw exception if request body updates contains id
        if (patchPayload.containsKey("id")) {
            throw new RuntimeException("Employee id not allowed in request body: " + patchPayload.get("id"));
        }

        Employee patchedEmployee = apply(patchPayload, tempEmployee);

        Employee dbEmployee = employeeService.save(patchedEmployee);

        return dbEmployee;
    }

    private Employee apply(Map<String, Object> patchPayload, Employee employee) {

        //convert employee object to a JSON object node
        ObjectNode employeeNode = objectMapper.convertValue(employee, ObjectNode.class);

        //convert patchPayload to object node
        ObjectNode patchNode = objectMapper.convertValue(patchPayload, ObjectNode.class);

        //merge patch node updates into the employee node
        employeeNode.setAll(patchNode);

        return objectMapper.convertValue(employeeNode, Employee.class);
    }

    //add mapping @Delete - deletes employee by Id
    @DeleteMapping("/employees/{employeeId}")
    public String delete(@PathVariable("employeeId") int employeeId) {
        //find employee
        Employee employee = employeeService.findById(employeeId);
        //throw exception if null
        if (employee == null) {
            throw new RuntimeException("Employee with id: " + employeeId + " not found");
        }
        employeeService.deleteById(employeeId);

        return "Employee with id: " + employeeId + " deleted";
    }



}
