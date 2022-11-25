package com.skypro.employee.service;

import com.skypro.employee.exception.EmployeeNotFoundException;
import com.skypro.employee.model.Employee;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DepartmentService {
    private final  EmployeeService employeeService;

    public DepartmentService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public Collection<Employee> getDepartmentEmployees(int dep) { //Тест
        return getEmpByDepStream(dep)
                .collect(Collectors.toList());
    }

    public int getSalSumByDep (int dep) { //Тест
        return getEmpByDepStream(dep)
                .mapToInt(Employee::getSalary).sum();
    }

    public int getMaxSalInDep(int dep) { //Тест
        return getEmpByDepStream(dep)
                .mapToInt(Employee::getSalary)
                .max().orElseThrow(EmployeeNotFoundException::new);
    }

    public int getMinSalInDep(int dep) { //Тест
        return getEmpByDepStream(dep)
                .mapToInt(Employee::getSalary)
                .min().orElseThrow(EmployeeNotFoundException::new);
    }

    public Map<Integer, List<Employee>> getEmpGroupedByDeps() {
        return  employeeService.getAllEmployees().stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
    }

    private Stream<Employee> getEmpByDepStream(int dep) {
        return employeeService.getAllEmployees().stream()
                .filter(e -> e.getDepartment() == dep);
    }
}