package com.skypro.employee.service;

import com.skypro.employee.exception.EmployeeNotFoundException;
import com.skypro.employee.exception.InvalidEmployeeRequestException;
import com.skypro.employee.model.Employee;
import com.skypro.employee.record.EmployeeRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.DoubleAccumulator;
import java.util.stream.Collectors;

//здесь хранятся сотрудники
@Service
public class EmployeeService {
    private final Map<Integer, Employee> employees = new HashMap<>();


    public Collection<Employee> getAllEmployees() {
        return this.employees.values();
    }

    public Employee addEmployee(EmployeeRequest employeeRequest) {  //покрыть неправильное добавление сотрудника
        if (!StringUtils.isAlpha(employeeRequest.getLastName()) || //возвращает исключение
                !StringUtils.isAlpha(employeeRequest.getFirstName())) {
            throw new InvalidEmployeeRequestException();
        }
        Employee employee = new Employee(
                StringUtils.capitalize(employeeRequest.getFirstName()), // если с имя в нижнем регистре, то капитализация
                StringUtils.capitalize(employeeRequest.getLastName()),
                employeeRequest.getDepartment(),
                employeeRequest.getSalary());

        this.employees.put(employee.getId(), employee);
        return employee;
    }

    public int getSalarySum() {
        return employees.values().stream()
                .mapToInt(Employee::getSalary)
                .sum();
    }

    public Employee getEmployeeWithMaxSalary() {
        return employees.values().stream()
                .max(Comparator.comparingInt(Employee::getSalary))
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public Employee getEmployeeWithMinSalary() {
        return employees.values().stream()
                .min(Comparator.comparingInt(Employee::getSalary))
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> getEmployeesWithSalaryMoreThatAverage() { // еще этот
        Double averageSalary = getAverageSalary();
        if (averageSalary == null) {
            return Collections.emptyList();
        }
        return employees.values().stream().filter(e -> e.getSalary() > averageSalary)
                .collect(Collectors.toList());
    }

    public Double getAverageSalary() { //++
//        employees.values().stream().mapToInt(Employee::getSalary).average(); //возвр Optional
        return employees.values()
                .stream()
                .collect(Collectors.averagingInt(Employee::getSalary));  //всегда возвр знач

    }

    public Employee removeEmployee(int id) {
        return employees.remove(id);
    }
}
