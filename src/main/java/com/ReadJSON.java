package com;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadJSON {
    public static void readAndPrintEmployeeInfo(PrintWriter out) {
        // JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        // Specify the path to your JSON file
        String filePath = "data/Employee.json";

        try (FileReader reader = new FileReader(filePath)) {
            // Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray employeeList = (JSONArray) obj;

            // Apply CSS style to the HTML output
            out.println("<style>");
            out.println(".employee-info { border: 1px solid #ddd; padding: 10px; margin: 10px; }");
            out.println(".employee-header { background-color: #f2f222; padding: 5px; }");
            out.println(".employee-details { margin-left: 20px; }");
            out.println("</style>");

            // Output employee information
            out.println("<div class='employee-info'>");
            out.println("<h1 class='employee-header'>Employee Information</h1>");

            for (int i = 0; i < employeeList.size(); i++) {
                out.println("<div class='employee-details'>");
                out.println("<h3>Employee #" + (i + 1) + "</h3>");
                parseEmployeeObject((JSONObject) employeeList.get(i), out);
                out.println("<hr/>");
                out.println("</div>");
            }

            out.println("</div>");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static JSONArray readExistingEmployees() {
        JSONArray employeeList = new JSONArray();
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("data\\Employee.json")) {
            Object obj = jsonParser.parse(reader);
            if (obj instanceof JSONArray) {
                employeeList = (JSONArray) obj;
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return employeeList;
    }

    public static void parseEmployeeObject(JSONObject employee, PrintWriter out) {
        // Get employee object within the list
        JSONObject employeeObject = (JSONObject) employee;

        // Get employee details
        out.println("<p><strong>First name:</strong> " + employeeObject.get("FirstName") + "</p>");
        out.println("<p><strong>Last name:</strong> " + employeeObject.get("LastName") + "</p>");
        out.println("<p><strong>Employee ID:</strong> " + employeeObject.get("EmployeeID") + "</p>");
        out.println("<form action='edit' method='post'>");
        out.println("<p><strong>Designation:</strong> <input type='text' name='designation' value='" + employeeObject.get("Designation") + "' required></p>");
        
        // Get employee knownLanguages
        JSONArray knownLanguages = (JSONArray) employeeObject.get("KnownLanguages");

        // Iterate through knownLanguages and print LanguageName and ScoreOutof100
        for (int j = 0; j < knownLanguages.size(); j++) {
            out.println("<p class='language-info'><strong>KnownLanguages #" + (j + 1) + "</strong></p>");
            JSONObject language = (JSONObject) knownLanguages.get(j);
            out.println("<p><strong>Language Name:</strong> " + language.get("LanguageName") + "</p>");
            out.println("<p><strong>Score Out of 100:</strong> " + language.get("ScoreOutof100") + "</p>");
        }

        // Add an "Edit" button for each employee
        out.println("<input type='hidden' name='id' value='" + employee.get("EmployeeID") + "'>");
        out.println("<input type='submit' value='Edit'>");
        out.println("</form>");

        // Add an "Delete" button for each employee
        out.println("<form action='delete' method='post'>");
        out.println("<input type='submit' value='Delete'>");
        out.println("<input type='hidden' name='id' value='" + employeeObject.get("EmployeeID") + "'>");
        out.println("</form>");
    }
}
