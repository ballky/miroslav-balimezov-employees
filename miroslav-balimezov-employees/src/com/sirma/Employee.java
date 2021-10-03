package com.sirma;

import java.time.LocalDate;

public class Employee {

    private long employeeId;
    private long projectId;
    private LocalDate startDate;
    private LocalDate endDate;

    public Employee(long employeeId, long projectId, LocalDate startDate, LocalDate endDate) {
        this.employeeId = employeeId;
        this.projectId = projectId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Employee() {
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public long getProjectId() {
        return projectId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

}
