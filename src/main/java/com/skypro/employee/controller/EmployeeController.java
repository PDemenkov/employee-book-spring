package com.skypro.employee.controller;

import com.skypro.employee.model.Employee;
import com.skypro.employee.record.EmployeeRequest;
import com.skypro.employee.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Http методы
 * GET - получение ресурса или набора ресурсов
 * POST - создание ресурса
 * PUT - Модификация ресурса (изменить все поля)
 * PATCH - частичная модификация
 * - DELETE - удаление ресурса
 */
@RestController
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public Collection<Employee> getAllEmployees() {
        return this.employeeService.getAllEmployees();
    }

    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody EmployeeRequest employeeRequest) {
        return this.employeeService.addEmployee(employeeRequest);
    }

    @GetMapping("/employees/salary/sum")
    public int getSalarySum() {
        return this.employeeService.getSalarySum();
    }

    @GetMapping("/employees/salary/max")
    public Collection<Employee> getSalaryMax() {
        return this.employeeService.getSalaryMax();
    }

    @GetMapping("employees/salary/min")
    public Collection<Employee> getSalaryMin() {
        return this.employeeService.getSalaryMin();
    }

    @GetMapping("employees/salary/high-salary")
    public Collection<Employee> getEmployeeBiggerThanAverage(){
        return this.employeeService.getEmployeeBiggerThanAverage();
    };
}
