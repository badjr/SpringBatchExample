package com.company.app;

import org.springframework.batch.item.ItemProcessor;

/**
 * Created by Brett on 2/2/2017.
 */
public class EmployeeItemProcessor implements ItemProcessor<Employee, Employee> {
    @Override
    public Employee process(Employee employee) throws Exception {
        employee.setFirstName(employee.getFirstName().toUpperCase());
        employee.setLastName(employee.getLastName().toUpperCase());
        return employee;
    }
}
