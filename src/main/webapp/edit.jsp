<%@ page import="com.example.demo.model.User" %>
<%@ page import="com.example.demo.DOA.PostDatabase" %>
<%@ page import="com.example.demo.model.Post" %>
<%@ page import="com.example.demo.utilities.ConnectionManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Facebook Post</title>

    <meta charset="utf-8" />
    <meta
            name="viewport"
            content="width=device-width, initial-scale=1, shrink-to-fit=no"
    />
    <title>timeline</title>
    <!-- JQuery-->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <!-- Bootstrap CSS -->
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6"
            crossorigin="anonymous"
    />
    <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf"
            crossorigin="anonymous"
    ></script>
</head>
<body>
<%
    User user = (User) session.getAttribute("user");
    if(user == null){
        session.setAttribute("Registration Error", "!!!Please Login first");
        response.sendRedirect("index.jsp");
    }
    String query = request.getQueryString();
    int postId = Integer.parseInt(query.substring(query.indexOf("=")+1));
    PostDatabase postDatabase = new PostDatabase(ConnectionManager.getConnection());
    Post post = postDatabase.getPostById(postId);
    System.out.println(post);
%>

<nav style="background: #3b5998" class="navbar navbar-expand-lg">
    <div class="container-fluid">
        <a class="navbar-brand" href="#" style="color:#fff;"><h1>Facebook</h1></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" style="color: #fff;" href="/home.jsp"><h3>Home</h3></a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<%--    <%User user = (User) session.getAttribute("user");%>--%>
<%--        <%--%>
<%--            if(user != null){%>--%>
<%--        <h4 style="color: #fff">Welcome <%= user.getSurname() %></h4>--%>
<%--    <%}--%>
<%--    %>--%>
<section style="margin: 80px auto; width: 60%">
    <form action="/UpdateServlet" method="POST">
        <div class="mb-3">
            <label for="title" class="form-label">Post Title</label>
            <input type="text" class="form-control" id="title" value="<%=post.getTitle()%>" name="title" aria-describedby="emailHelp">
            <div class="form-text">Edit title</div>
        </div>
        <div class="mb-3">
            <label for="body" class="form-label">Post Body</label>
            <input type="text" class="form-control" id="body" name="body" value="<%=post.getBody()%>" aria-describedby="emailHelp">
            <div class="form-text">Edit body</div>
        </div>
        <label for="body" class="form-label">Post id</label>
        <input id="input" type="text" name="postId"/>
        <button type="submit" class="btn btn-primary"
        <%if(!post.getEmail().equals(user.getEmail()))%> disabled
        >Submit</button>
    </form>
</section>
<script>
    window.onload = ()=> {
        const params = new URLSearchParams(window.location.search);
        document.getElementById("input").value =  params.get("post");
    }
</script>
</body>
</html>