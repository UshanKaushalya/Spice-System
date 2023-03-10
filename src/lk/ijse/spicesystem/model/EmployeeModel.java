package lk.ijse.spicesystem.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.spicesystem.to.Employee;
import lk.ijse.spicesystem.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeModel {

    public static String nextEmployeeId() throws SQLException, ClassNotFoundException {

        String sql = "SELECT EmpID FROM Employee ORDER BY EmpID DESC LIMIT 1";

        ResultSet result = CrudUtil.execute(sql);

        String latestId = null;

        if(result.next()){
            latestId = result.getString("EmpID");
        }

        String[] SUs = latestId.split("EMP");

        for (String a:SUs) {
            latestId = a;
        }

        int idNum = Integer.parseInt(latestId);

        latestId = "EMP" + (idNum+1);

        return latestId;

    }

    public static boolean addEmployeeDetail(Employee employee) throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO Employee VALUES (?, ?, ?, ?, ?, ?, ?)";

        return CrudUtil.execute(sql, employee.getEmpId(), employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getSalaryPerDay(),
                employee.getAddress(), employee.getJobRole());


    }

    public static ObservableList getAllEmpId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT EmpID FROM Employee";
        ResultSet result = CrudUtil.execute(sql);

        ObservableList<String> list = FXCollections.observableArrayList();

        while(result.next()){
            list.add(result.getString("EmpID"));
        }

        return list;
    }

    public static Employee searchEmployee(String id) throws SQLException, ClassNotFoundException {

        String sql = "SELECT * FROM Employee WHERE EmpID = ?";
        ResultSet result = CrudUtil.execute(sql, id);

        if(result.next()){
            return new Employee(
                    result.getString("EmpID"),
                    result.getString("FirstName"),
                    result.getString("LastName"),
                    result.getString("Email"),
                    Double.valueOf(result.getString("SalaryPerDay")),
                    result.getString("Address"),
                    result.getString("JobRole")
            );

        }

        return null;
    }

    public static boolean updateEmployee(Employee employee) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE Employee SET FirstName = ?, LastName = ?, Email = ?, SalaryPerDay = ?, Address = ?, JobRole = ? WHERE EmpID = ?";

        return CrudUtil.execute(sql, employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getSalaryPerDay(), employee.getAddress(), employee.getJobRole(), employee.getEmpId());

    }

    public static boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException {

        String sql = "DELETE FROM Employee WHERE EmpID = ?";

        return CrudUtil.execute(sql, id);

    }


}
