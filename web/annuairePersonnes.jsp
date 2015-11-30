<%@page import="ch.hearc.ig.ta.business.AlertMessage"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ch.hearc.ig.ta.business.Achievement"%>
<%@page import="ch.hearc.ig.ta.servlets.HtmlHttpUtils"%>
<%@page import="java.util.List"%>
<%@page import="ch.hearc.ig.ta.dao.PersonneDAO"%>
<%@page import="ch.hearc.ig.ta.business.Personne"%>
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
    
    String achievementName = "Premier coup d'oeil";
    if (!Services.checkUserAchievement(username, achievementName)) {
        Achievement achievement = Services.addAchievement(username, achievementName);

        if (achievement == null) {
            alertMessages.add(new AlertMessage(true, "Une erreur s'est produite lors de l'attribution de la récompense \"" + achievementName + "\"."));
        } else {
            lastUnlockedAchievements.add(achievement);
        }
    }
    
    List<Personne> personnes = null;
    PersonneDAO personneDAO = new PersonneDAO();
    if(request.getParameter("search") != null) {
        personnes = personneDAO.research(request.getParameter("search"));
    } else if (request.getParameter("nom") != null || request.getParameter("prenom") != null || request.getParameter("adresse") != null || request.getParameter("ville") != null) {
        Personne pers = new Personne(request.getParameter("nom"),request.getParameter("prenom"),request.getParameter("adresse"),request.getParameter("ville"));
        personnes = personneDAO.research(pers);
    } else {
        personnes = personneDAO.research();
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
        <title>Annuaire de clients - Portail commecial</title>
        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400italic,600,700%7COpen+Sans:300,400,400italic,600,700">
        <link rel="stylesheet" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/style.css">
        <link rel="stylesheet" href="assets/css/pnotify.custom.min.css">
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
                                <a class="active" href="annuairePersonnes.jsp">Annuaire de clients</a>
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
                <div class="modal fade" id="customer-preview" tabindex="-1" role="dialog" aria-labelledby="myModalLabel2" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title font-w600" id="myModalLabel2">Fiche client</h4>
                            </div>
                            <div class="modal-body remove-padding">
                                <table class="table remove-margin-b font-s13">
                                    <tbody>
                                        <tr>
                                            <td class="font-w600 pad-20-l">Pr&eacute;nom</td>
                                            <td class="text-muted text-right pad-20-r" id="customer-preview-firstname"></td>
                                        </tr>
                                        <tr>
                                            <td class="font-w600 pad-20-l">Nom</td>
                                            <td class="text-muted text-right pad-20-r" id="customer-preview-lastname"></td>
                                        </tr>
                                        <tr>
                                            <td class="font-w600 pad-20-l">Adresse</td>
                                            <td class="text-muted text-right pad-20-r" id="customer-preview-address"></td>
                                        </tr>
                                        <tr>
                                            <td class="font-w600 pad-20-l">Ville</td>
                                            <td class="text-muted text-right pad-20-r" id="customer-preview-city"></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Fermer</button>
                                <a class="btn btn-default btn-delete pull-right modal-confirm-delete" href="#">Supprimer</a>
                                <a class="btn btn-default btn-edit pull-right">Modifier</a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title font-w600" id="myModalLabel">Confirmation de suppression</h4>
                            </div>
                            <div class="modal-body">
                                <p>Vous &ecirc;tes sur le point de supprimer la fiche client de <a id="confirm-delete-customer" href="#" data-toggle="tooltip"></a>, cette action est irr&eacute;versible.</p>
                                <p>Voulez-vous continuer ?</p>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Annuler</button>
                                <a class="btn btn-danger btn-ok pull-right">Supprimer</a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="content bg-gray-lighter">
                    <div class="row">
                        <div class="col-xs-12 page-heading">
                            <h1>
                                <%
                                    if(request.getParameter("search") != null) {
                                %>
                                Annuaire de clients <small>Recherche simple</small>
                                <%
                                    } else if (request.getParameter("nom") != null || request.getParameter("prenom") != null || request.getParameter("adresse") != null || request.getParameter("ville") != null) {
                                %>
                                Annuaire de clients <small>Recherche avanc&eacute;e</small>
                                <%
                                    } else {
                                %>
                                Annuaire de clients
                                <%
                                    }
                                %>
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
                        <div class="col-xs-12">
                            <%        
                                if (request.getParameter("nom") != null || request.getParameter("prenom") != null || request.getParameter("adresse") != null || request.getParameter("ville") != null) {
                            %>
                            <div class="block bg-gray-light">
                                <div class="block-content remove-padding bg-white">
                                    <form class="form-horizontal form-group remove-margin" id="search-form" action="annuairePersonnes.jsp" method="post">
                                        <div class="input-group">
                                            <input class="form-control pad-10-l" name="search" placeholder="<%= (request.getParameter("search") != null) ? request.getParameter("search") : "Rechercher..." %>" type="text">
                                            <span class="input-group-btn">
                                                <button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search"></span></button>
                                            </span>
                                        </div>
                                    </form>
                                </div>
                                <div class="block-footer remove-margin">
                                    <small>Recherche avanc&eacute;e sur&emsp;<%= (!request.getParameter("prenom").isEmpty()) ? "pr&eacute;nom: <span class=\"label label-default\">" + request.getParameter("prenom") + "</span>&emsp;" : "" %><%= (!request.getParameter("nom").isEmpty()) ? "nom: <span class=\"label label-default\">" + request.getParameter("nom") + "</span>&emsp;" : "" %><%= (!request.getParameter("adresse").isEmpty()) ? "adresse: <span class=\"label label-default\">" + request.getParameter("adresse") + "</span>&emsp;" : "" %><%= (!request.getParameter("ville").isEmpty()) ? "ville: <span class=\"label label-default\">" + request.getParameter("ville") + "</span>&emsp;" : "" %></small>
                                    <small>&emsp;|&emsp;&emsp;</small>
                                    <small><span class="badge"><%= personnes.size() %></span> résultat<%= (personnes.size() > 1) ? "s" : "" %></small>
                                    <small>&emsp;&emsp;|&emsp;&emsp;</small>
                                    <small><a href="annuairePersonnes.jsp">Effacer le résultat</a></small>
                                    <br>
                                    <small><a class="text-gray-dark" href="recherchePersonne.jsp">Nouvelle recherche avanc&eacute;e...</a></small>
                                </div>
                            </div>
                            <%
                                } else {
                            %>
                            <div class="block bg-gray-light">
                                <div class="block-content remove-padding bg-white">
                                    <form class="form-horizontal form-group remove-margin" id="search-form" action="annuairePersonnes.jsp" method="post">
                                        <div class="input-group">
                                            <input class="form-control pad-10-l" name="search" placeholder="<%= (request.getParameter("search") != null) ? request.getParameter("search") : "Rechercher..." %>" type="text">
                                            <span class="input-group-btn">
                                                <button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search"></span></button>
                                            </span>
                                        </div>
                                    </form>
                                </div>
                                <div class="block-footer remove-margin">
                                    <%
                                        if (request.getParameter("search") != null) {
                                    %>
                                    <small><span class="badge"><%= personnes.size() %></span> résultat<%= (personnes.size() > 1) ? "s" : "" %></small>
                                    <small>&emsp;&emsp;|&emsp;&emsp;</small>
                                    <small><a href="annuairePersonnes.jsp">Effacer le résultat</a></small>
                                    <br>
                                    <%
                                        }
                                    %>
                                    <a class="text-gray-dark" href="recherchePersonne.jsp"><small>Recherche avanc&eacute;e...</small></a>
                                </div>
                            </div>
                            <%
                                }
                            %>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="block">
                                <div class="block-content">
                                    <div class="pull-t pull-r-l">
                                        <table class="table table-hover remove-margin-b font-s13">
                                            <thead>
                                                <tr>
                                                    <td class="font-w600">Pr&eacute;nom</td>
                                                    <td class="font-w600">Nom</td>
                                                    <td class="font-w600">Adresse</td>
                                                    <td class="font-w600">Ville</td>
                                                    <td class="td-small"></td>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <% 
                                                    for (Personne personne : personnes) { 
                                                %>
                                                <tr>
                                                    <td class="text-primary modal-customer-preview" data-id="<%= personne.getId() %>" data-firstname="<%= personne.getPrenom() %>" data-lastname="<%= personne.getNom() %>" data-address="<%= personne.getAdresse() %>" data-city="<%= personne.getVille() %>"><%= personne.getPrenom() %></td>
                                                    <td class="text-primary modal-customer-preview" data-id="<%= personne.getId() %>" data-firstname="<%= personne.getPrenom() %>" data-lastname="<%= personne.getNom() %>" data-address="<%= personne.getAdresse() %>" data-city="<%= personne.getVille() %>"><%= personne.getNom() %></td>
                                                    <td class="text-muted modal-customer-preview" data-id="<%= personne.getId() %>" data-firstname="<%= personne.getPrenom() %>" data-lastname="<%= personne.getNom() %>" data-address="<%= personne.getAdresse() %>" data-city="<%= personne.getVille() %>"><%= personne.getAdresse() %></td>
                                                    <td class="text-muted modal-customer-preview" data-id="<%= personne.getId() %>" data-firstname="<%= personne.getPrenom() %>" data-lastname="<%= personne.getNom() %>" data-address="<%= personne.getAdresse() %>" data-city="<%= personne.getVille() %>"><%= personne.getVille() %></td>
                                                    <td class="dropdown">
                                                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false" data-toggle="tooltip" data-placement="left" title="G&eacute;rer"><span class="glyphicon glyphicon-cog text-muted pull-right"></span></a>
                                                        <ul class="dropdown-menu">
                                                            <li><a href="modificationPersonne.jsp?id=<%= personne.getId() %>">Modifier</a></li>
                                                            <li><a class="modal-confirm-delete" href="#" data-href="ServletFaireEffacementPersonne?id=<%= personne.getId() %>" data-firstname="<%= personne.getPrenom() %>" data-lastname="<%= personne.getNom() %>" data-address="<%= personne.getAdresse() %>" data-city="<%= personne.getVille() %>">Supprimer</a></li>
                                                        </ul>
                                                    </td>
                                                </tr>
                                                <% 
                                                    } 
                                                    if (personnes.isEmpty()) {
                                                %>
                                                <tr>
                                                    <td class="text-center" colspan="5">Aucun client trouv&eacute;.</td>
                                                </tr>
                                                <%
                                                    }
                                                %>
                                            </tbody>
                                        </table>
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
        <script src="assets/js/pnotify.custom.min.js"></script>

        <script>
            $(document).ready(function(){
                $('[data-toggle="tooltip"]').tooltip();   
            });
            $('.modal-confirm-delete').on('click', function () {
                $('#confirm-delete').modal('show');
                $('.modal-backdrop').appendTo('#main-container');
                $('body').removeClass();
                $('#confirm-delete').find('.btn-ok').attr('href', $(this).data('href'));
                $('#confirm-delete').find('#confirm-delete-customer').text($(this).data('firstname') + ' ' + $(this).data('lastname'));
                $('#confirm-delete').find('#confirm-delete-customer').attr('title', 'Adresse: ' + $(this).data('address') + ', ' + 'Ville: ' + $(this).data('city'));
            });
            $('.modal-customer-preview').on('click', function () {
                $('#customer-preview').modal('show');
                $('.modal-backdrop').appendTo('#main-container');
                $('body').removeClass();
                $('#customer-preview').find('#customer-preview-firstname').text($(this).data('firstname'));
                $('#customer-preview').find('#customer-preview-lastname').text($(this).data('lastname'));
                $('#customer-preview').find('#customer-preview-address').text($(this).data('address'));
                $('#customer-preview').find('#customer-preview-city').text($(this).data('city'));
                $('#customer-preview').find('.btn-edit').attr('href', 'modificationPersonne.jsp?id=' + $(this).data('id'));
                $('#customer-preview').find('.btn-delete').attr('data-href', 'ServletFaireEffacementPersonne?id=' + $(this).data('id'));
                $('#customer-preview').find('.btn-delete').attr('data-firstname', $(this).data('firstname'));
                $('#customer-preview').find('.btn-delete').attr('data-lastname', $(this).data('lastname'));
                $('#customer-preview').find('.btn-delete').attr('data-address', $(this).data('address'));
                $('#customer-preview').find('.btn-delete').attr('data-city', $(this).data('city'));
            });
            $('#search-form').submit(function () {
                $('#waiting-modal').modal('show');
                $('.modal-backdrop').appendTo('#main-container');
                $('body').removeClass();
                $('#waiting-modal').find('.modal-title').append('Recherche en cours...');
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
