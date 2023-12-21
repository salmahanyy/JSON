package com.employee;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/addNewEmployee")
public class AddNewEmp extends HttpServlet {
        private static final long serialVersionUID = 1L;

        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                        throws ServletException, IOException {
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out = response.getWriter();

                out.println("<html>");
                out.println("<head><title>Add New Employee</title>");
                out.println("<script>");
                out.println("function generateLanguageFields() {");
                out.println("var numLanguages = document.getElementById('numLanguages').value;");
                out.println("var languagesDiv = document.getElementById('languagesDiv');");
                out.println("languagesDiv.innerHTML = '';");

                out.println("for (var i = 0; i < numLanguages; i++) {");
                out.println(
                                "languagesDiv.innerHTML += 'Language Name ' + i + ': <input type=\"text\" name=\"languageName' + i + '\" required style=\"margin-bottom: 5px;\"><br>';");
                out.println(
                                "languagesDiv.innerHTML += 'Score Out of 100 ' + i + ': <input type=\"number\" name=\"scoreOutof100' + i + '\" required style=\"margin-bottom: 5px;\"><br>';");
                out.println("}");

                out.println("}</script>");

                out.println("</head>");
                out.println("<body style=\"font-family: Arial, sans-serif; margin: 20px;\">");
                out.println("<h1 style=\"color: #336699;\">Add New Employee</h1>");
                out.println("<form action='writeJSON' method='post'>");
                out.println("First Name: <input type='text' name='firstname' required style=\"margin-bottom: 10px;\"><br>");
                out.println("Last Name: <input type='text' name='lastname' required style=\"margin-bottom: 10px;\"><br>");
                out.println("Employee ID: <input type='text' name='id' required style=\"margin-bottom: 10px;\"><br>");

                out.println("Designation: <input type='text' name='designation' required style=\"margin-bottom: 10px;\"><br>");
                // numLanguages is used to generate the number of language fields
                out.println(
                                "Number of Languages Known: <input type='number' id='numLanguages' name='num' required oninput='generateLanguageFields()' style=\"margin-bottom: 10px;\"><br>");
                out.println("<div id='languagesDiv'></div>");
                out.println("<input type='submit' value='Add' style=\"background-color: #f2f222; color: black; padding: 10px 20px; border: none; border-radius: 5px;\">");
                out.println("</form>");
                out.println("</body>");
                out.println("</html>");

                out.close();
        }
}
