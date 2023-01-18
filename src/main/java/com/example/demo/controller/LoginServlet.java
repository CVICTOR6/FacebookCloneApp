package com.example.demo.controller;

import com.example.demo.DOA.UserDatabase;
import com.example.demo.model.User;
import com.example.demo.utilities.ConnectionManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + "WELCOME TO FACEBOOK CLONE" + "</h1>");
        out.println("</body></html>");
        HttpSession httpSession = request.getSession();
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDatabase userData = new UserDatabase(ConnectionManager.getConnection());
        User user;
        user = userData.login(email, password);

        if(user != null){
            httpSession.setAttribute("user", user);
            response.sendRedirect("home.jsp");
        }else{
            httpSession.setAttribute("regError", "Enter Correct Password or Email");
            response.sendRedirect("index.jsp");
        }
    }
}
