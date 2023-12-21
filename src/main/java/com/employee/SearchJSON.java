package com.employee;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ReadJSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@WebServlet("/searchJSON")
public class SearchJSON extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        // Retrieve search parameters from the request
        String searchType = request.getParameter("searchType");
        String searchValue = request.getParameter("searchValue");
        System.out.println("Search type: " + searchType);
        System.out.println("Search value: " + searchValue);

        // Read and parse JSON data
        JSONArray employeeArray = ReadJSON.readExistingEmployees();

        int matchCount = 0;
        // JSONArray jsonArray = new JSONArray();

        // Iterate through the array to find matching records
        for (Object empObj : employeeArray) {
            JSONObject employee = (JSONObject) empObj;

            if (searchType.equals("EmployeeID")) {
                // If searchType is EmployeeID, parse fieldValue as an integer
                int fieldValue = ((Long) employee.get(searchType)).intValue();
                int searchIntValue = Integer.parseInt(searchValue);

                if (fieldValue == searchIntValue) {
                    matchCount++;
                    // jsonArray.add(employee);
                    out.println("<html><body>");
                    out.println("<h1>Match found:</h1>");
                    out.println("<p>" + employee.toJSONString() + "</p>");
                    out.println("</body></html>");
                }
            } else {
                // For other search types, compare as strings
                String fieldValue = (String) employee.get(searchType);
                if (fieldValue.equals(searchValue)) {
                    matchCount++;
                    // jsonArray.add(employee);
                    out.println("<html><body>");
                    out.println("<h1>Match found:</h1>");
                    out.println("<p>" + employee.toJSONString() + "</p>");
                    out.println("</body></html>");
                }
            }
        }

        // Gson gson = new GsonBuilder().setPrettyPrinting().create();
        // String formattedJson = gson.toJson(jsonArray);
        // out.println(formattedJson);

        if (matchCount == 0) {
            // If no match is found
            out.println("<html><body>");
            out.println("<h1>No match found for the specified criteria.</h1>");
            out.println("</body></html>");
        }
    }
}
