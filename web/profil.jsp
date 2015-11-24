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
%>
<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="utf-8">
        <title>Profil - Portail commecial</title>
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
                                Profil <small><%= Services.getNomCommercial(username) %></small>
                            </h1>
                        </div>
                    </div>
                </div>
                <div class="content bg-white border-b">
                    <div class="row items-push text-uppercase">
                        <div class="col-xs-6 col-sm-4">
                            <div class="font-w700 text-gray-darker">Level</div>
                            <div class="h2 font-w300 text-primary"><%= Services.getLevelName(username) %></div>
                            <div class="text-muted"><small>Niveau suivant dans 123 points</small></div>
                        </div>
                        <div class="col-xs-6 col-sm-4">
                            <div class="font-w700 text-gray-darker">Points</div>
                            <div class="h2 font-w300 text-primary"><%= Services.getLevel(username) %></div>
                            <div class="text-muted"><small>sur 246 points</small></div>
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
                                                    <td class="text-muted text-right" style="width: 70px;">JJ/MM/AAAA</td>
                                                </tr>
                                                <tr>
                                                    <td class="font-w600">Information#2</td>
                                                    <td class="text-muted text-right" style="width: 70px;">Bla bla...</td>
                                                </tr>
                                                <tr>
                                                    <td class="font-w600">Information#3</td>
                                                    <td class="text-muted text-right" style="width: 70px;">Bla bla...</td>
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
                                                    <div class="h1 font-w700"><%= 12 - Services.getAchievementsNumber(username) %></div>
                                                    <div class="h5 font-w300 text-muted">Nombre de badges restants</div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xs-12">
                                        <div class="row">
                                            <% for (Achievement achievement : Services.getUserAchievements(username)) { %>
                                            <div class="col-sm-6 block-content sub-block bg-white">
                                                <div class="row items-push text-center">
                                                    <div class="col-xs-12">
                                                        <div class="h1 font-w300"><%= achievement.getLibelle() %></div>
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
                    BlackPinguinsLtd Project (Geoffroy Megert, Loïc Megert, Thierry Hubmann, Steve Julmy, Thomas Rüegsegger)
                </div>
            </footer>
        </div>

        <!-- Bootstrap core JavaScript-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
    </body>
</html>
