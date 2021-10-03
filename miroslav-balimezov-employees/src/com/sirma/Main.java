package com.sirma;

import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Main {

    public static void main(String[] args) throws IOException {

        System.setIn(new FileInputStream("employees.txt"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Map<Long, List<Employee>> employees = new HashMap<>();
        List<String> data = reader.lines().collect(Collectors.toList());

        for (String line : data) {
            String[] employeeLine = line.split(",");

            Employee employee = new Employee(Long.parseLong(employeeLine[0]), Long.parseLong(employeeLine[1]),
                    LocalDate.parse(employeeLine[2]),
                    employeeLine[3].equalsIgnoreCase("NULL") ? LocalDate.now() :
                            LocalDate.parse(employeeLine[3]));
            if (employees.get(Long.parseLong(employeeLine[1])) == null) {
                List<Employee> employeesPerProject = new ArrayList<>();
                employeesPerProject.add(employee);
                employees.put(Long.parseLong(employeeLine[1]), employeesPerProject);
            } else {
                List<Employee> employeesPerProject = employees.get(Long.parseLong(employeeLine[1]));
                employeesPerProject.add(employee);
                employees.put(Long.parseLong(employeeLine[1]), employeesPerProject);
            }
        }

        long maxDaysResult = 0;
        Employee personOneResult = new Employee();
        Employee personTwoResult = new Employee();

        for (Map.Entry<Long, List<Employee>> entry : employees.entrySet()) {

            List<Employee> employeesPerProject = entry.getValue();

            long maxDays = 0;
            Employee personOne = new Employee();
            Employee personTwo = new Employee();

            for (int i = 0; i < employeesPerProject.size() - 1; i++) {
                for (int j = i + 1; j < employeesPerProject.size(); j++) {

                    LocalDate startDateOfFirstEmployee = employeesPerProject.get(i).getStartDate();
                    LocalDate startDateOfSecondEmployee = employeesPerProject.get(j).getStartDate();
                    LocalDate endDateOfFirstEmployee = employeesPerProject.get(i).getEndDate();
                    LocalDate endDateOfSecondEmployee = employeesPerProject.get(j).getEndDate();

                    long commonDays = ChronoUnit.DAYS.between(maxDate(startDateOfFirstEmployee, startDateOfSecondEmployee),
                            minDate(endDateOfFirstEmployee, endDateOfSecondEmployee));

                    if (commonDays > 0 && commonDays > maxDays) {
                        maxDays = commonDays;
                        personOne = employeesPerProject.get(i);
                        personTwo = employeesPerProject.get(j);
                    }
                }
            }

            if (maxDaysResult < maxDays) {
                maxDaysResult = maxDays;
                personOneResult = personOne;
                personTwoResult = personTwo;
            }
        }
        if (maxDaysResult == 0) {
            System.out.println("No employees were found working on the same project at the same time!");

        } else {
            System.out.printf("Common days: %d%n", maxDaysResult);
            System.out.printf("Employee ID: %d, Project ID: %d, Start Date: %s, End Date: %s%n",
                    personOneResult.getEmployeeId(), personOneResult.getProjectId(),
                    personOneResult.getStartDate().toString(), personOneResult.getEndDate().toString());
            System.out.printf("Employee ID: %d, Project ID: %d, Start Date: %s, End Date: %s%n",
                    personTwoResult.getEmployeeId(), personTwoResult.getProjectId(),
                    personTwoResult.getStartDate().toString(), personTwoResult.getEndDate().toString());
        }
    }

    private static LocalDate maxDate(LocalDate firstDate, LocalDate secondDate) {
        return firstDate.compareTo(secondDate) > 0 ? firstDate : secondDate;
    }

    private static LocalDate minDate(LocalDate firstDate, LocalDate secondDate) {
        return firstDate.compareTo(secondDate) < 0 ? firstDate : secondDate;
    }

}
