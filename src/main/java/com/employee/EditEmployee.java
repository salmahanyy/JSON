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

@WebServlet("/edit")
public class EditEmployee extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        // Handle the edit action
        String employeeIdToEdit = request.getParameter("id");
        String newDesignation = request.getParameter("designation");

        editEmployeeInJSON(employeeIdToEdit, newDesignation);

        // Redirect to doGet to refresh the search results
        response.sendRedirect("/employee/list");
    }

    private void editEmployeeInJSON(String employeeIdToEdit,
            String newDesignation) {
        try {
            // Read existing employees from the file
            JSONArray employeeList = ReadJSON.readExistingEmployees();

            // Iterate through the employee list
            for (int i = 0; i < employeeList.size(); i++) {
                JSONObject employee = (JSONObject) employeeList.get(i);

                // Retrieve the ID of the current employee
                String currentEmployeeId = String.valueOf(employee.get("EmployeeID"));

                // Check if the current employee's ID matches the ID to edit
                if (currentEmployeeId.equals(employeeIdToEdit)) {
                    // Edit the employee information
                    employee.put("Designation", newDesignation);

                    // Write the updated employee list back to the file
                    WriteJSON.writeEmployeeListToFile(employeeList);
                    break; // Exit the loop after editing
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
