package com;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@WebServlet("/writeJSON")
public class WriteJSON extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String id = request.getParameter("id");
        String designation = request.getParameter("designation");
        int num = Integer.parseInt(request.getParameter("num"));
        String[] languageName = new String[num];
        int[] scoreOutof100 = new int[num];

        // Validate uniqueness of employee IDs
        if (!isEmployeeIdUnique(id)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Employee ID must be unique");
            return;
        }

        for (int i = 0; i < num; i++) {
            System.out.println("languageName" + i + " = " + request.getParameter("languageName" + i));
            languageName[i] = request.getParameter("languageName" + i);
            scoreOutof100[i] = Integer.parseInt(request.getParameter("scoreOutof100" + i));
        }

        JSONObject employeeDetails = new JSONObject();
        // parse id as int
        int intid = Integer.parseInt(id);
        employeeDetails.put("FirstName", firstName);
        employeeDetails.put("LastName", lastName);
        employeeDetails.put("EmployeeID", intid);
        employeeDetails.put("Designation", designation);
        JSONArray knownLanguages = new JSONArray();
        for (int i = 0; i < num; i++) {
            JSONObject languageDetails = new JSONObject();
            languageDetails.put("LanguageName", languageName[i]);
            languageDetails.put("ScoreOutof100", scoreOutof100[i]);
            knownLanguages.add(languageDetails);
        }

        employeeDetails.put("KnownLanguages", knownLanguages);

        // Read existing employees from the file
        JSONArray employeeList = ReadJSON.readExistingEmployees();

        // Add the new employee to the list
        employeeList.add(employeeDetails);

        // Write the updated employee list back to the file
        writeEmployeeListToFile(employeeList);

        // after writing data to the file, redirect the request to ShowEmployees
        response.sendRedirect("list");
    }

    public static void writeEmployeeListToFile(JSONArray employeeList) {
        try (FileWriter file = new FileWriter("data\\Employee.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String formattedJson = gson.toJson(employeeList);
            file.write(formattedJson);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isEmployeeIdUnique(String id) {
        JSONArray employeeList = ReadJSON.readExistingEmployees();
        for (Object obj : employeeList) {
            JSONObject employee = (JSONObject) obj;
            long existingId = (long) employee.get("EmployeeID");
            if (existingId == Long.parseLong(id)) {
                return false; // Not unique
            }
        }
        return true; // Unique
    }
}
