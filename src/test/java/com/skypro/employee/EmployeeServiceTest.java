package com.skypro.employee;

import com.skypro.employee.exception.InvalidEmployeeRequestException;
import com.skypro.employee.model.Employee;
import com.skypro.employee.record.EmployeeRequest;
import com.skypro.employee.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeeServiceTest {
    private EmployeeService employeeService;

    @BeforeEach
    public void setUp() {
        this.employeeService = new EmployeeService();
        Stream.of(
                new EmployeeRequest("FirstName", "FirstName", 3, 50000),
                new EmployeeRequest("SecondName", "SecondName", 3, 60000),
                new EmployeeRequest("ThirdName", "ThirdName", 5, 48000)
        ).forEach(employeeService::addEmployee);
    }

    @Test
    public void removeEmployee() {
        employeeService.removeEmployee(10);
        Collection<Employee> employees = employeeService.getAllEmployees();
        assertThat(employees).hasSize(2);
    }

    @Test
    public void addEmployee() {
        EmployeeRequest request = new EmployeeRequest(
                "Example", "Example", 3, 50000);
        Employee result = employeeService.addEmployee(request);
        assertEquals(request.getFirstName(), result.getFirstName());
        assertEquals(request.getLastName(), result.getLastName());
        assertEquals(request.getDepartment(), result.getDepartment());
        assertEquals(request.getSalary(), result.getSalary());
        assertThat(employeeService.getAllEmployees()
                .contains(result));
    }

    @Test
    public void listEmployees() {
        Collection<Employee> employees = employeeService.getAllEmployees();
        assertThat(employees).hasSize(3);
        assertThat(employees).first().extracting(Employee::getFirstName)
                .isEqualTo("FirstName");
        assertThat(employees).first().extracting(Employee::getLastName)
                .isEqualTo("FirstName");
        assertThat(employees).first().extracting(Employee::getDepartment)
                .isEqualTo(3);
        assertThat(employees).first().extracting(Employee::getSalary)
                .isEqualTo(50000);
    }

    @Test
    public void SumOfSalaries() {
        int sum = employeeService.getSalarySum();
        assertThat(sum).isEqualTo(158000);
    }

    @Test
    public void employeeWithMaxSalary() {
        Employee employee = employeeService.getEmployeeWithMaxSalary();
        assertThat(employee).isNotNull().extracting(Employee::getFirstName).isEqualTo("SecondName");
    }

    @Test
    public void employeeWithMinSalary() {
        Employee employee = employeeService.getEmployeeWithMinSalary();
        assertThat(employee).isNotNull().extracting(Employee::getFirstName).isEqualTo("ThirdName");
    }


    @Test
    void testAddEmployee2() {
        assertThrows(InvalidEmployeeRequestException.class,
                () -> employeeService.addEmployee(new EmployeeRequest("First Name", "LastName1", 1, 10000)));
    }

    @Test
    void getAverageSalary() {
        double average = employeeService.getAverageSalary();
        assertThat(average).isEqualTo(52666.666666666664);

    }

    @Test
    void getEmployeesWithSalaryMoreThatAverage() {
        assertThat(employeeService.getAverageSalary()).isNotNull();

        List<Employee> list = employeeService.getEmployeesWithSalaryMoreThatAverage();
        assertThat(list).extracting(Employee::getSalary).isNotIn(employeeService.getAverageSalary()); //проверка на зп > средней
        assertThat(list.size() == 1);

        Collection<Employee> employee = employeeService.getAllEmployees();
        employee.removeAll(employee);
        assertThat(employeeService.getAverageSalary() == null);
        assertThat(employee).isEmpty();
    }


    @Test
    void capitalizeFirstLetterIfLowerCase() {
        EmployeeRequest request = new EmployeeRequest(
                "example", "example", 3, 50000);
        Employee result = employeeService.addEmployee(request);
        assertThat(Boolean.parseBoolean(result.getFirstName().toUpperCase()));
        assertThat(Boolean.parseBoolean(result.getLastName().toUpperCase()));
    }

    @Test
    void addEmployeeNotAlphaException() {
        assertThrows(InvalidEmployeeRequestException.class, () -> employeeService.addEmployee
                (new EmployeeRequest("tes234", "trsdf333", 1, 400)));
    }
}

