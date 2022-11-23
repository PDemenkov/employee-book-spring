package com.skypro.employee.controller;

import com.skypro.employee.exception.EmployeeNotFoundException;
import com.skypro.employee.model.Employee;
import com.skypro.employee.service.DepartmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/department")
    public Collection<Employee> getDepartmentEmployees(@RequestParam(value = "dep")int dep) {
        return this.departmentService.getDepartmentEmployees(dep);
    }

    @GetMapping("/department/getSalSumByDep")
    public int getSalSumByDep(@RequestParam(value = "dep")int dep) {
        return this.departmentService.getSalSumByDep(dep);
    }

    @GetMapping("/department/getMaxSalInDep")
    public int getMaxSalInDep(@RequestParam(value = "dep")int dep) {
        return this.departmentService.getMaxSalInDep(dep);
    }

    @GetMapping("/department/getMinSalInDep")
    public int getMinSalInDep(@RequestParam(value = "dep")int dep) { //Тест
        return this.departmentService.getMinSalInDep(dep);
    }

    @GetMapping("/department/getEmpGroupedByDeps")
    public Map<Integer, List<Employee>> getEmpGroupedByDeps() {
        return this.departmentService.getEmpGroupedByDeps();
    }

}
