package com.skypro.employee;

import com.skypro.employee.exception.EmployeeNotFoundException;
import com.skypro.employee.model.Employee;
import com.skypro.employee.service.DepartmentService;
import com.skypro.employee.service.EmployeeService;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {
    private final List<Employee> employees = List.of(
            new Employee("FirstName", "FirstName", 3, 5),
            new Employee("SecondName", "SecondName", 3, 6),
            new Employee("ThirdName", "ThirdName", 5, 8)
    );

    @Mock
    EmployeeService employeeService;
    @InjectMocks
    DepartmentService departmentService;

    @BeforeEach
    void setUp() {
        when(employeeService.getAllEmployees())
                .thenReturn(employees);
    }

    @Test
    void getEmployeesByDepartment() {
        Collection<Employee> employeesList = this.departmentService.getDepartmentEmployees(3);
        Assertions.assertThat(employeesList).hasSize(2)
                .contains(employees.get(0), employees.get(1));
    }

    @Test
    void getSumSalaryByDep() {
        int sum = this.departmentService.getSalSumByDep(3);
        assertThat(sum).isEqualTo(11);
    }

    @Test
    void maxSalInDep() {
        int max = this.departmentService.getMaxSalInDep(3);
        assertThat(max).isEqualTo(6);
    }

    @Test
    void minSalInDep() {
        int min = this.departmentService.getMinSalInDep(3);
        assertThat(min).isEqualTo(5);
    }

    @Test
    void groupedEmployees() {
        Map<Integer, List<Employee>> groupedEmployees = this.departmentService.getEmpGroupedByDeps();
        assertThat(groupedEmployees).hasSize(2)
                .containsEntry(3, List.of(employees.get(0), employees.get(1)))
                .containsEntry(5, List.of(employees.get(2)));
    }

    @Test
    void WhenNoEmpThenGroupByReturnEmptyMap() {
        when(employeeService.getAllEmployees()).thenReturn(List.of());

        Map<Integer, List<Employee>> groupedEmployees = this.departmentService.getEmpGroupedByDeps();
    }

    @Test
    void WhenNoEmployeesThenMaxSalaryInDepThrowsException() {
        when(employeeService.getAllEmployees()).thenReturn(List.of());
        assertThatThrownBy(() ->
                departmentService.getMinSalInDep(0)).isInstanceOf(EmployeeNotFoundException.class);
    }

}
