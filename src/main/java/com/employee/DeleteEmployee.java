package com.employee;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.ReadJSON;
import com.WriteJSON;

@WebServlet("/delete")
public class DeleteEmployee extends HttpServlet{
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

                String employeeID = request.getParameter("id");

                // Read and parse JSON data
                JSONArray employeeArray = ReadJSON.readExistingEmployees();
                
                // Iterate through the array to find the employee with the specified ID
                for (Object empObj : employeeArray) {
                    JSONObject employee = (JSONObject) empObj;
                    int currentEmployeeID = ((Long) employee.get("EmployeeID")).intValue();
    
                    if (currentEmployeeID == Integer.parseInt(employeeID)) {
                        // Remove the employee from the array
                        employeeArray.remove(employee);
                        // Write the updated array back to the file
                        WriteJSON.writeEmployeeListToFile(employeeArray);
                        response.sendRedirect("list");
                        break; // Break out of the loop since we found and deleted the employee
                    }
                }
            }
}