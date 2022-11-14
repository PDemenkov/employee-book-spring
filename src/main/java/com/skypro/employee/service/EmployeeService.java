package com.skypro.employee.service;

import com.skypro.employee.model.Employee;
import com.skypro.employee.record.EmployeeRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

//здесь хранятся сотрудники
@Service
public class EmployeeService {
    private final Map<Integer, Employee> employees = new HashMap<>();


    public Collection<Employee> getAllEmployees() {
        return this.employees.values();
    }

    public Employee addEmployee(EmployeeRequest employeeRequest) {
        if (employeeRequest.getFirstName() == null || employeeRequest.getLastName() == null) {
            throw new IllegalArgumentException("Employee name should be set");
        }
        Employee employee = new Employee(employeeRequest.getFirstName(), employeeRequest.getLastName(), employeeRequest.getDepartment(), employeeRequest.getSalary());

        this.employees.put(employee.getId(), employee);
        return employee;
    }

    public int getSalarySum() {
        return employees.values().stream()
                .mapToInt(Employee::getSalary)
                .sum();
    }

public Collection<Employee> getSalaryMax() {
    int sal = employees.values().stream()
            .mapToInt(Employee::getSalary)
            .max().getAsInt();
    return employees.values().stream().filter(e->e.getSalary()==sal).toList();
}
//    public Employee getSalaryMax1() {
//        return employees.values().stream().max(Comparator.comparing(Employee::getSalary)).get();
//    }
    public Collection<Employee> getSalaryMin() {
        int sal = employees.values().stream()
                .mapToInt(Employee::getSalary)
                .min().getAsInt();
        return employees.values().stream()
                .filter(e->e.getSalary()==sal).toList();
    }

//    public Employee getSalaryMin1() {
//        return employees.values().stream().min(Comparator.comparing(Employee::getSalary)).get();
//    }

    public Collection<Employee> getEmployeeBiggerThanAverage() {
        double opt = employees.values().stream()
                .mapToInt(Employee::getSalary)
                .average().getAsDouble();
        return employees.values().stream()
                .filter(employee -> employee.getSalary() > opt).collect(Collectors.toList());
    }
}
