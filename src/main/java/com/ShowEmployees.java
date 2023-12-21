package com;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// URL: http://localhost:8080/employee/list
@WebServlet("/list")
public class ShowEmployees extends HttpServlet {
        private static final long serialVersionUID = 1L;

        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                        throws ServletException, IOException {
                response.setContentType("text/html;charset=UTF-8");

                PrintWriter out = response.getWriter();
                out.println("<html>");
                out.println("<head><title>Employee Information</title></head>");
                out.println("<body>");

                ReadJSON.readAndPrintEmployeeInfo(out);

                out.println("<form action='addNewEmployee' method='post' style='margin-top: 20px;'>");
                out.println("<input type='submit' value='Add Employee' style='background-color: #f2f222; color: black; padding: 10px 15px; font-size: 16px; border: none; border-radius: 5px; cursor: pointer;'>");
                out.println("<input type='submit' formaction='search' value='Search for Employee' style='background-color: #f2f222; color: black; padding: 10px 15px; font-size: 16px; border: none; border-radius: 5px; cursor: pointer;'>");
                out.println("<input type='submit' method ='post' formaction='retrieve' value='Retrieve Employees' style='background-color: #f2f222; color: black; padding: 10px 15px; font-size: 16px; border: none; border-radius: 5px; cursor: pointer;'>");
                out.println("</form>");
                out.println("</body>");
                out.println("</html>");
        }
}
