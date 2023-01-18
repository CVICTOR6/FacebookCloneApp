package com.example.demo.controller;


import com.example.demo.DOA.UserDatabase;
import com.example.demo.model.User;
import com.example.demo.utilities.ConnectionManager;
import com.example.demo.utilities.Encryption;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "RegisterServlet", value = "/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        try(PrintWriter out = response.getWriter()){
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RegisterServlet</title>");
            out.println("<body>");
            out.println("</body>");
            out.println("</html>");

            HttpSession httpSession = request.getSession();
            //fetch data from the registration page
            String fname = request.getParameter("fname");
            String lname = request.getParameter("lname");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String dob = request.getParameter("dob");
            String gender = request.getParameter("gender");


//            if (fname.length() < 3) {
//                httpSession.setAttribute("Registration Error", " firstname cannot be less than 3 character long");
//                response.sendRedirect("index.jsp");
//                return;
//            }
//            if (lname.length() < 3) {
//                httpSession.setAttribute("Registration Error", "surname cannot be less than 3 character long");
//                response.sendRedirect("index.jsp");
//                return;
//            }
//            if(password.length() < 7){
//                httpSession.setAttribute("Registration Error", "password cannot be less than 7 character long");
//                response.sendRedirect("index.jsp");
//                return;
//            }

            String encryptedPass = Encryption.encryptPassword(password);

            User user = new User(fname,lname,encryptedPass,email,dob,gender);
            UserDatabase userDatabase = new UserDatabase(ConnectionManager.getConnection());


            if (!userDatabase.registerUser(user)) {
                String errorMessage = "User already exist!";
                httpSession.setAttribute("Registration Error", errorMessage);
            }else{
                httpSession.setAttribute("Registration Error", "Successfully registered!");
            }

            response.sendRedirect("index.jsp");

        }
        catch (Exception exception){
            exception.printStackTrace();
        }
    }
}
