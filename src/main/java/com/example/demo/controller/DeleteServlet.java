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

@WebServlet(name = "DeleteServlet", value = "/DeleteServlet")
public class DeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try(PrintWriter out = response.getWriter()) {
           response.setContentType("text/plain");
           response.setCharacterEncoding("UTF-8");
            int postId = Integer.parseInt(request.getParameter("postId"));
           //Do something here
            HttpSession session = request.getSession();
          User user =  (User)session.getAttribute("user");
//            int userId = user.getId();
//            System.out.println("Post Id is here "+postId);

            PostDatabase postDatabase = new PostDatabase(ConnectionManager.getConnection());

            if(postDatabase.deletePost(user.getId(), postId)){
               // out.println("Post Deleted Succesfully");
                response.getWriter().write("Post deleted succesfully");
            }else{

                response.getWriter().write("Access denied deleting this post");
            }

           // response.sendRedirect("home.jsp");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
