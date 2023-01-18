package com.example.demo.controller;


import com.example.demo.DOA.PostDatabase;
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

@WebServlet(name = "CommentServlet", value = "/CommentServlet")
public class CommentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try(PrintWriter out = response.getWriter();){
            out.println("<html><body>");
            out.println("<h1>" + "Servlet Registration example" + "</h1>");
            out.println("</body></html>");



            HttpSession httpSession = request.getSession();


            //Getting data from the form  post
            String comment = request.getParameter("comment");
            int postId = Integer.parseInt(request.getParameter("postId"));
            User currentUser = (User) httpSession.getAttribute("user");
            int userId = currentUser.getId();

            PostDatabase postDatabase = new PostDatabase(ConnectionManager.getConnection());

            if(postDatabase.makeComment(userId,postId,comment)){
                out.println("File uploaded to this directory");
                httpSession.setAttribute("message", "successful");
                httpSession.setAttribute("user",currentUser);
            }else{
                out.print("500 error");
                httpSession.setAttribute("message", "Error posting comment");
                httpSession.setAttribute("user",currentUser);
            }

            response.sendRedirect("home.jsp");

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
