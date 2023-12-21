package com.employee;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/search")
public class Search extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head><title>Add New Employee</title>");

        out.println("</head>");
        out.println("<body style=\"font-family: Arial, sans-serif; margin: 20px;\">");
        out.println("<h1 style=\"color: #336699;\">Add New Employee</h1>");
        out.println("<form action='searchJSON' method='post'>");
        // EmployeeID or Designation
        out.println("Search by: <select name='searchType' style=\"margin-bottom: 10px;\">");
        out.println("<option value='EmployeeID'>Employee ID</option>");
        out.println("<option value='Designation'>Designation</option>");
        out.println("</select><br>");
        out.println("Search Value: <input type='text' name='searchValue' required style=\"margin-bottom: 10px;\"><br>");

        out.println(
                "<input type='submit' value='Search' style=\"background-color: #f2f222; color: black; padding: 10px 20px; border: none; border-radius: 5px;\">");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");

        out.close();
    }
}
