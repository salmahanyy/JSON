package com.employee;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.ReadJSON;

@WebServlet("/retrieve")
public class RetrieveEmployees extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        // Read and parse JSON data
        JSONArray employeeArray = ReadJSON.readExistingEmployees();

        // find employees who know the Java language with score higher than 50
        ArrayList<JSONObject> employees = new ArrayList<JSONObject>();
        for (Object empObj : employeeArray) {
            JSONObject employee = (JSONObject) empObj;
            JSONArray skills = (JSONArray) employee.get("KnownLanguages");
            for (Object skillObj : skills) {
                JSONObject skill = (JSONObject) skillObj;
                if (skill.get("LanguageName").equals("Java") && ((Long) skill.get("ScoreOutof100")).intValue() > 50) {
                    employees.add(employee);
                }
            }
        }

        // sort employees by ScoreOutof100 in ascending order
        Collections.sort(employees, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject emp1, JSONObject emp2) {
                JSONArray skills1 = (JSONArray) emp1.get("KnownLanguages");
                JSONArray skills2 = (JSONArray) emp2.get("KnownLanguages");
                int score1 = getJavaScore(skills1);
                int score2 = getJavaScore(skills2);
                return Integer.compare(score1, score2);
            }

            private int getJavaScore(JSONArray skills) {
                for (Object skillObj : skills) {
                    JSONObject skill = (JSONObject) skillObj;
                    if ("Java".equals(skill.get("LanguageName"))
                            && ((Long) skill.get("ScoreOutof100")).intValue() > 50) {
                        return ((Long) skill.get("ScoreOutof100")).intValue();
                    }
                }
                return 0; // Default score if Java is not found or score is not greater than 50
            }
        });

        if (employees.size() == 0) {
            out.println("<html><body>");
            out.println("<h1>No employees found who know Java with score higher than 50</h1>");
            out.println("</body></html>");
            return;
        } else {
            out.println("</body></html>");
            // print the sorted list of employees
            out.println("<html><body>");
            out.println("<h1>Employees who know Java with score higher than 50:</h1>");
            for (JSONObject employee : employees) {
                out.println("<p>" + employee.toJSONString() + "</p>");
            }
            out.println("</body></html>");
        }

    }
}