package de.stetro.matin.dbw.prak1.daos;


import de.stetro.matin.dbw.prak1.entities.employees.Employee;
import de.stetro.matin.dbw.prak1.entities.employees.Employees;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EmployeeDao {
    private Statement statement;
    private Connection connection;

    public EmployeeDao() throws Exception {
        try {
            connection = DatabaseInterface.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate("drop table if exists employee");
            statement.executeUpdate("create table employee (id integer, name string, department id,departmentLeading boolean, departmentId int)");
            statement.close();
        } catch (SQLException e) {
            throw new Exception("Error in creating the product table.");
        }
    }

    public Employee getEmployee(int id) throws Exception {
        try {
            statement = connection.createStatement();
            String sql = "select * from employee where id = " + id;
            ResultSet rs = statement.executeQuery(sql);
            Employee employee = new Employee();
            employee.setId(id);
            if (rs.next()) {
                employee.setName(rs.getString("name"));
                employee.getDepartment().setId(rs.getInt("departmentId"));
                employee.getDepartment().setName(rs.getString("department"));
                employee.getDepartment().setLeading(rs.getBoolean("departmentLeading"));
            }
            statement.close();
            return employee;
        } catch (SQLException e) {
            throw new Exception("Error in get product.");
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
                employee.setDepartment(new Employee.Department());
                employee.getDepartment().setId(rs.getInt("departmentId"));
                employee.getDepartment().setName(rs.getString("department"));
                employee.getDepartment().setLeading(rs.getBoolean("departmentLeading"));
                employees.getEmployee().add(employee);
            }
            statement.close();
            return employees;
        } catch (SQLException e) {
            throw new Exception("Error in get all products.");
        }
    }

    public void createProduct(Employee employee) throws Exception {
        try {
            statement = connection.createStatement();
            String sql = getCreateSqlStatement(employee);
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            throw new Exception("Error in create product.");
        }
    }

    public String getCreateSqlStatement(Employee employee) {
        return "insert into employee values(\n\t" + employee.getId() + ", \n\t'"
                + employee.getName() + "', \n\t'" + employee.getDepartment().getName() + "', \n\t'" + employee.getDepartment().isLeading() + "','" + employee.getDepartment().getId() + "')";
    }
}
