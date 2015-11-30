<%@page import="ch.hearc.ig.ta.business.AlertMessage"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ch.hearc.ig.ta.servlets.HtmlHttpUtils"%>
<%@page import="ch.hearc.ig.ta.business.Achievement"%>
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
        <title>Profil - Portail commecial</title>
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
                                <a class="active" href="profil.jsp">Profil</a>
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
                                Profil <small><%= Services.getNomCommercial(username) %></small>
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
                <div class="content bg-white border-b">
                    <div class="row items-push text-uppercase">
                        <div class="col-xs-6 col-sm-4">
                            <div class="font-w700 text-gray-darker">Level</div>
                            <div class="h2 font-w300 text-primary"><%= Services.getLevelName(username) %></div>
                            <div class="text-muted"><small>Niveau suivant dans <%= Services.getLevelPointsGap(username) %> points</small></div>
                        </div>
                        <div class="col-xs-6 col-sm-4">
                            <div class="font-w700 text-gray-darker">Points</div>
                            <div class="h2 font-w300 text-primary"><%= Services.getPoints(username) %></div>
                            <div class="text-muted"><small>sur <%= Services.getLevelPoints(username) %> points</small></div>
                        </div>
                    </div>
                </div>
                <div class="content">
                    <div class="row">
                        <div class="col-lg-4">
                            <div class="block">
                                <div class="block-header">
                                    <h3 class="block-title">Informations sur le compte</h3>
                                </div>
                                <div class="block-content bg-gray-lighter">
                                    <div class="row items-push">
                                        <div class="col-xs-12 h1 font-w300 text-left"><%= Services.getNomCommercial(username) %></div>
                                    </div>
                                </div>
                                <div class="block-content">
                                    <div class="pull-t pull-r-l">
                                        <table class="table remove-margin-b font-s13">
                                            <tbody>
                                                <tr>
                                                    <td class="font-w600">Inscrit(e) depuis</td>
                                                    <td class="text-muted text-right" style="width: 70px;">23/10/2015</td>
                                                </tr>
                                                <tr>
                                                    <td class="font-w600">Adresse</td>
                                                    <td class="text-muted text-right" style="width: 70px;">Holzstrasse 1<br>6436 Muotathal</td>
                                                </tr>
                                                <tr>
                                                    <td class="font-w600">E-mail</td>
                                                    <td class="text-muted text-right" style="width: 70px;">colibri36@hotmail.com</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-8">
                            <div class="content-grid">
                                <div class="row">
                                    <div class="col-xs-12 block">
                                        <div class="block-header">
                                            <h3 class="block-title text-center">Badges</h3>
                                        </div>
                                        <div class="block-content">
                                            <div class="row items-push text-center">
                                                <div class="col-xs-6">
                                                    <div class="h1 font-w700"><%= Services.getAchievementsNumber(username) %></div>
                                                    <div class="h5 font-w300 text-muted">Nombre de badges gagn&eacute;s</div>
                                                </div>
                                                <div class="col-xs-6">
                                                    <div class="h1 font-w700"><%= 12 - Services.getNotAchievedAchievementsNumber(username) %></div>
                                                    <div class="h5 font-w300 text-muted">Nombre de badges restants</div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xs-12">
                                        <div class="row">
                                            <% for (Achievement achievement : Services.getUserAchievements(username)) { %>
                                            <div style="height: 160px;" class="col-sm-6 block-content sub-block <%= (achievement.isAchieved() ? "bg-white" : "bg-muted") %>">
                                                <div class="row items-push text-center">
                                                    <div class="col-xs-12">
                                                        <div class="h1 font-w300 <%= (achievement.isAchieved() ? "text-primary" : "") %>"><%= achievement.getLibelle() %></div>
                                                        <div class="h5 font-w300 text-muted text-uppercase"><%= achievement.getDescription() %></div>
                                                        <div><small class="h6 font-w300 font-s12 text-muted"><%= (achievement.getObtentionDate() != null) ? achievement.getObtentionDate() : "" %></small></div>
                                                    </div>
                                                </div>
                                            </div>
                                            <% } %>
                                        </div>
                                    </div>
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
                    BlackPinguinsLtd Project (Thierry Hubmann, Steve Julmy, Thomas Rüegsegger, Geoffroy Megert, Loïc Megert)
                </div>
            </footer>
        </div>

        <!-- Bootstrap core JavaScript-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
        
        <script>
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
