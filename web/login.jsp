<%@page import="ch.hearc.ig.ta.servlets.HtmlHttpUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (HtmlHttpUtils.isAuthenticate(request)) {
        request.getRequestDispatcher("annuairePersonnes.jsp").forward(request,response);
    }
%>
<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="utf-8">
        <title>Page de connexion - Portail commecial</title>
        <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1.0">
        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400italic,600,700%7COpen+Sans:300,400,400italic,600,700">
        <link rel="stylesheet" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/style.css">
    </head>
    <body>
        <div id="page-container">
            <nav id="sidebar">
                <nav id="sidebar">
                <div class="sidebar-content">
                    <div class="side-header bg-white-op">
                        <a href="annuairePersonnes.jsp">
                            <span class="h4 font-w600 text-white">Portail commercial</span>
                        </a>
                    </div>
                    <div class="side-content">
                        <ul>
                            <li class="side-content-header">Compte</li>
                            <li>
                                <a class="active" href="login.jsp">Se connecter</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
            </nav>
            <header id="header-navbar">
                <!--<ul class="pull-right">
                    <li>
                    </li>
                </ul>-->
                <!--<ul class="pull-left">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </ul>-->
            </header>
            <main id="main-container">
                <div class="content bg-gray-lighter">
                    <div class="row">
                        <div class="col-xs-12 page-heading">
                            <h1>
                                Connexion
                            </h1>
                        </div>
                    </div>
                </div>
                <div class="content">
                    <div class="row">
                        <div class="col-lg-offset-4 col-lg-4">
                            <div class="block">
                                <div class="block-header">
                                    <h3 class="block-title"><span class="glyphicon glyphicon-lock"></span>&emsp;Login</h3>
                                </div>
                                <%
                                    if (request.getParameter("failed") != null && request.getParameter("failed").equals("1")) {
                                        out.println("<div class=\"alert alert-danger alert-dismissible\" role=\"alert\">");
                                        out.println("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>");
                                        out.println("<span class=\"glyphicon glyphicon-remove\"></span>&emsp;Nom d'utilisateur et/ou Mot de passe incorrecte/s.");
                                        out.println("</div>");
                                    }
                                %>
                                <div class="block-content block-content-narrow">
                                    <form class="form-horizontal" action="ServletLogin" method="post">
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <label for="username">Nom d'utilisateur</label>
                                                <input class="form-control" type="text" id="username" name="username" placeholder="Nom d'utilisateur..." required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <label for="password">Mot de passe</label>
                                                <input class="form-control" type="password" id="password" name="password" placeholder="Mot de passe..." required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <input class="btn btn-default pull-right" data-toggle="modal" data-target="#modal-connexion" type="submit" value="Se connecter">
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
            <footer id="page-footer" class="font-s12 bg-gray-lighter">
                <div class="pull-right">
                    Haute &eacute;cole de gestion Arc, 635-1.1 Technologies actuelles, 3-IG-PT
                </div>
                <div class="pull-left">
                    BlackPinguinsLtd Project (Geoffroy Megert, Loïc Megert, Thierry Hubmann, Steve Julmy, Thomas Rüegsegger)
                </div>
            </footer>
        </div>
        
        <!-- Bootstrap core JavaScript-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
    </body>
</html>
