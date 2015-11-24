<%@page import="ch.hearc.ig.ta.servlets.HtmlHttpUtils"%>
<%@page import="ch.hearc.ig.ta.services.Services"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (!HtmlHttpUtils.isAuthenticate(request)) {
        request.getRequestDispatcher("login.jsp").forward(request,response);
    }
    
    HttpSession s = request.getSession(true);
    String username = s.getAttribute("username").toString();
%>
<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="utf-8">
        <title>Nouveau client - Portail commecial</title>
        <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1.0">
        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400italic,600,700%7COpen+Sans:300,400,400italic,600,700">
        <link rel="stylesheet" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/style.css">
    </head>
    <body>
        <div id="page-container">
            <nav id="sidebar">
                <div class="sidebar-content">
                    <div class="side-header bg-white-op">
                        <a href="annuairePersonnes.jsp">
                            <span class="h4 font-w600 text-white">Portail commercial</span>
                        </a>
                    </div>
                    <div class="side-content">
                        <ul>
                            <li class="side-content-header">Gestion des clients</li>
                            <li>
                                <a href="annuairePersonnes.jsp">Annuaire de clients</a>
                            </li>
                            <li>
                                <a class="active" href="creationPersonne.jsp">Nouveau client</a>
                            </li>
                            <li>
                                <a href="recherchePersonne.jsp">Rechercher client</a>
                            </li>
                            <li class="side-content-header">Compte</li>
                            <li>
                                <a href="profil.jsp">Profil</a>
                            </li>
                            <li>
                                <a href="ServletLogout">Se d&eacute;connecter</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
            <header id="header-navbar">
                <ul class="pull-right">
                    <li>
                        Connect&eacute; en tant que <%= Services.getNomCommercial(username) %>
                    </li>
                </ul>
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
                                Nouveau client
                            </h1>
                        </div>
                    </div>
                </div>
                <div class="content">
                    <div class="row">
                        <div class="col-lg-offset-4 col-lg-4">
                            <div class="block">
                                <div class="block-header">
                                    <h3 class="block-title">Formulaire d'ajout de client</h3>
                                </div>
                                <div class="block-content block-content-narrow">
                                    <form class="form-horizontal push-10-t" action="ServletCreationPersonne" method="post">
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <label for="firstname">Pr&eacute;nom <span class="text-danger">*</span></label>
                                                <input class="form-control" type="text" id="firstname" name="firstname" placeholder="Pr&eacute;nom du client..." required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <label for="lastname">Nom <span class="text-danger">*</span></label>
                                                <input class="form-control" type="text" id="lastname" name="lastname" placeholder="Nom du client..." required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <label for="address">Adresse <span class="text-danger">*</span></label>
                                                <input class="form-control" type="text" id="address" name="address" placeholder="Adresse du client..." required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <label for="city">Ville <span class="text-danger">*</span></label>
                                                <input class="form-control" type="text" id="city" name="city" placeholder="Ville du client..." required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <input class="btn btn-default pull-right" type="submit" value="Ajouter">
                                                <a class="btn btn-default pull-left" href="annuairePersonnes.jsp">Annuler</a>
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
