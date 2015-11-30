<%@page import="ch.hearc.ig.ta.business.AlertMessage"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ch.hearc.ig.ta.business.Achievement"%>
<%@page import="ch.hearc.ig.ta.dao.PersonneDAO"%>
<%@page import="java.util.Vector"%>
<%@page import="ch.hearc.ig.ta.business.Personne"%>
<%@page import="ch.hearc.ig.ta.servlets.HtmlHttpUtils"%>
<%@page import="ch.hearc.ig.ta.services.Services"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (!HtmlHttpUtils.isAuthenticate(request)) {
        request.getRequestDispatcher("login.jsp").forward(request,response);
    }
    
    HttpSession s = request.getSession(true);
    String username = s.getAttribute("username").toString();
    ArrayList<Achievement> lastUnlockedAchievements = (ArrayList<Achievement>) s.getAttribute("lastUnlockedAchievements");
    ArrayList<AlertMessage> alertMessages = (ArrayList<AlertMessage>) s.getAttribute("alertMessages");
    
    Long id = Long.valueOf(request.getParameter("id"));
    Personne personne = null;
    
    if (id != null) {
        if (!id.equals("")) {
            PersonneDAO pdao = new PersonneDAO();

            Vector<Personne> v = pdao.research(new Personne(new Long(id), null, null, null, null));
            personne = v.elementAt(0);
        }
    } else {
        request.getRequestDispatcher("annuairePersonnes.jsp").forward(request,response);
    }
%>
<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1.0">
        <meta name="description" content="Portail commecial, Haute &eacute;cole de gestion Arc, 635-1.1 Technologies actuelles, 3-IG-PT">
        <meta name="author" content="BlackPinguinsLtd Project Team (Geoffroy Megert, Loïc Megert, Thierry Hubmann, Steve Julmy, Thomas Rüegsegger)">
        <link rel="icon" type="image/png" href="assets/img/favicons/favicon.png">
        <!--[if IE]><link rel="shortcut icon" type="image/x-icon" href="assets/img/favicons/favicon.ico"><![endif]-->
        <title>Modifier client - Portail commecial</title>
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
                                <a href="creationPersonne.jsp">Nouveau client</a>
                            </li>
                            <li>
                                <a href="recherchePersonne.jsp">Rechercher client</a>
                            </li>
                            <li class="side-content-header">Compte</li>
                            <li>
                                <a href="profil.jsp">Profil</a>
                            </li>
                            <li>
                                <a href="ServletLogout" id="logout-btn">Se d&eacute;connecter</a>
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
                <div class="modal fade" id="waiting-modal" role="dialog">
                    <div class="modal-dialog modal-sm">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h4 class="modal-title"><span class="glyphicon glyphicon-refresh glyphicon-refresh-animate"></span>&emsp;</h4>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="content bg-gray-lighter">
                    <div class="row">
                        <div class="col-xs-12 page-heading">
                            <h1>
                                Modifier client
                            </h1>
                        </div>
                    </div>
                </div>
                <%
                    for (AlertMessage alertMessage : alertMessages) {
                %>
                <div class="alert <%= (alertMessage.isFailed()) ? "alert-danger" : "alert-success" %> alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <span class="glyphicon <%= (alertMessage.isFailed()) ? "glyphicon-remove" : "glyphicon-ok" %>"></span>&emsp;<%= alertMessage.getMessage() %>
                </div>
                <%
                    }
                    alertMessages.clear();
                    s.setAttribute("alertMessages", alertMessages);
                %>
                <div class="content">
                    <div class="row">
                        <div class="col-lg-offset-4 col-lg-4">
                            <div class="block">
                                <div class="block-header">
                                    <h3 class="block-title">Fiche client</h3>
                                </div>
                                <div class="block-content block-content-narrow">
                                    <form class="form-horizontal push-10-t" id="edit-form" action="ServletFaireMAJPersonne?id=<%= personne.getId() %>" method="post">
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <label for="firstname">Pr&eacute;nom <span class="text-danger">*</span></label>
                                                <input class="form-control" type="text" id="firstname" name="firstname" placeholder="Pr&eacute;nom du client..." value="<%= personne.getPrenom() %>" required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <label for="lastname">Nom <span class="text-danger">*</span></label>
                                                <input class="form-control" type="text" id="lastname" name="lastname" placeholder="Nom du client..." value="<%= personne.getNom() %>" required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <label for="address">Adresse <span class="text-danger">*</span></label>
                                                <input class="form-control" type="text" id="address" name="address" placeholder="Adresse du client..." value="<%= personne.getAdresse() %>" required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <label for="city">Ville <span class="text-danger">*</span></label>
                                                <input class="form-control" type="text" id="city" name="city" placeholder="Ville du client..." value="<%= personne.getVille() %>" required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <input class="btn btn-default pull-right" type="submit" value="Modifier">
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
        
        <script>
            $('#edit-form').submit(function () {
                $('#waiting-modal').modal('show');
                $('.modal-backdrop').appendTo('#main-container');
                $('body').removeClass();
                $('#waiting-modal').find('.modal-title').append('Modification en cours...');
            });
            $('#logout-btn').on('click', function () {
                $('#waiting-modal').modal('show');
                $('.modal-backdrop').appendTo('#main-container');
                $('body').removeClass();
                $('#waiting-modal').find('.modal-title').append('D&eacute;connection en cours...');
            });
        </script>
        <% 
            for (Achievement lastUnlockedAchievement : lastUnlockedAchievements) {
        %>
        <script>
            $(function () {
                new PNotify({
                    title: '<%= lastUnlockedAchievement.getLibelle() %> (D&eacute;bloqu&eacute;)',
                    text: 'F&eacute;licitation! Vous venez de gagner un nouveau badge.<br><small><i><%= lastUnlockedAchievement.getDescription() %></i></small>',
                    delay: 8000,
                    buttons: {
                        closer: false,
                        sticker: false
                    }
                });
            });
        </script>
        <% 
            } 
            lastUnlockedAchievements.clear();
            s.setAttribute("lastUnlockedAchievements", lastUnlockedAchievements);
        %>
    </body>
</html>

