package com.company.app;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * Created by Brett on 2/2/2017.
 */
public class EmployeeItemWriter implements ItemWriter<Employee> {
    @Override
    public void write(List<? extends Employee> employeesList) throws Exception {
        for (Employee employee : employeesList) {
            System.out.println("Name: "
                                + employee.getFirstName() + " "
                                + employee.getLastName() + "; "
                                + "Age: " + employee.getAge() + "; "
                                + "Salary: " + employee.getSalary());
        }
    }
}
