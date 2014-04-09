package de.stetro.matin.dbw.prak1.daos;


import de.stetro.matin.dbw.prak1.entities.employees.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * EmployeeDao - does databaseaccess for employee object
 *
 * @author stetro
 */
public class EmployeeDao {
    private Statement statement;
    private Connection connection;

    public EmployeeDao() throws Exception {
        try {
            connection = DatabaseInterface.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate("drop table if exists employee");
            statement.executeUpdate("create table employee (id integer, name string, department string, departmentLeading boolean, departmentId int, payment float, paymentCurrent string, hometown string)");
            statement.close();
        } catch (SQLException e) {
            throw new Exception("Error in creating the product table.");
        }
    }

    public Employees getAllEmployees() throws Exception {
        try {
            statement = connection.createStatement();
            String sql = "select * from employee";
            ResultSet rs = statement.executeQuery(sql);
            Employees employees = new Employees();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt("id"));
                employee.setName(rs.getString("name"));
                employee.setDepartment(new Department());
                employee.getDepartment().setId(rs.getInt("departmentId"));
                employee.getDepartment().setValue(rs.getString("department"));
                employee.getDepartment().setLeading(rs.getBoolean("departmentLeading"));
                employee.setPayment(new Price());
                employee.getPayment().setCurrent(Currenttype.fromValue(rs.getString("paymentCurrent")));
                employee.getPayment().setValue(rs.getFloat("payment"));
                employees.getEmployee().add(employee);
            }
            statement.close();
            return employees;
        } catch (SQLException e) {
            throw new Exception("Error in get all employees.");
        }
    }

    public void createEmployee(Employee employee) throws Exception {
        try {
            statement = connection.createStatement();
            String sql = getCreateSqlStatement(employee);
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            throw new Exception("Error in create employee.");
        }
    }

    public String getCreateSqlStatement(Employee employee) {
        Department department = employee.getDepartment();
        Price payment = employee.getPayment();
        return "insert into employee values(\n\t" +
                employee.getId() + ", \n" +
                "\t'" + employee.getName() + "', \n" +
                "\t'" + department.getValue() + "', \n" +
                "\t" + (department.isLeading() ? 1 : 0) + ", \n" +
                "\t" + department.getId() + ",\n" +
                "\t " + payment.getValue() + ", \n" +
                "\t'" + payment.getCurrent().value() + "', \n" +
                "\t'" + employee.getHometown() + "')";
    }
}
