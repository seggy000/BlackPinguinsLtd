<%-- 
    Document   : login
    Created on : 6 janv. 2010, 14:19:14
    Author     : termine
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login page - gestion des personnes (CRUD) </title>
    </head>
    <body>
        <h1> Login page - gestion des personnes (CRUD)</h1>
      <form method="POST" action="ServletLogin">
         username : <input type="text" name="username"><br>
         password : <input type="password" name="password"><br>

         <input type="submit" value="login">
      </form>
    </body>
</html>
