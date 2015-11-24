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
%>
<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="utf-8">
        <title>Annuaire de clients - Portail commecial</title>
        <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1.0">
        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400italic,600,700%7COpen+Sans:300,400,400italic,600,700">
        <link rel="stylesheet" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/style.css">
    </head>
    <body>
        <div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title font-w600" id="myModalLabel">Confirmation de suppression</h4>
                    </div>
                    <div class="modal-body">
                        <p>Vous &ecirc;tes sur le point de supprimer une fiche client, cette action est irr&eacute;versible.</p>
                        <p>Voulez-vous continuer ?</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Annuler</button>
                        <a class="btn btn-danger btn-ok pull-right">Supprimer</a>
                    </div>
                </div>
            </div>
        </div>
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
                                Annuaire de clients
                            </h1>
                        </div>
                    </div>
                </div>
                <%
                    if (request.getParameter("failed") != null && request.getParameter("failed").equals("0")) {
                        out.println("<div class=\"alert alert-success alert-dismissible\" role=\"alert\">");
                        out.println("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>");
                        out.println("<span class=\"glyphicon glyphicon-ok\"></span>&emsp;" + request.getParameter("msg"));
                        out.println("</div>");
                    } else if (request.getParameter("failed") != null && request.getParameter("failed").equals("1")) {
                        out.println("<div class=\"alert alert-danger alert-dismissible\" role=\"alert\">");
                        out.println("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>");
                        out.println("<span class=\"glyphicon glyphicon-remove\"></span>&emsp;" + request.getParameter("msg"));
                        out.println("</div>");
                    }
                %>
                <div class="content">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="block bg-gray-light">
                                <div class="block-content remove-padding bg-white">
                                    <form class="form-horizontal form-group remove-margin" action="Servlet" method="post">
                                        <div class="input-group">
                                            <input class="form-control pad-10-l" name="search" placeholder="Rechercher..." type="text">
                                            <span class="input-group-btn">
                                                <button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search"></span></button>
                                            </span>
                                        </div>
                                    </form>
                                </div>
                                <div class="block-footer remove-margin">
                                    <a class="text-gray-dark" href="recherchePersonne.jsp"><small>Recherche avanc&eacute;e...</small></a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="block">
                                <div class="block-content">
                                    <div class="pull-t pull-r-l">
                                        <table class="table remove-margin-b font-s13">
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
                                                    PersonneDAO personneDAO = new PersonneDAO();
                                                    List<Personne> personnes = personneDAO.research();
                                                    for (Personne personne : personnes) { 
                                                %>
                                                <tr>
                                                    <td class="text-primary"><%= personne.getPrenom() %></td>
                                                    <td class="text-primary"><%= personne.getNom() %></td>
                                                    <td class="text-muted"><%= personne.getAdresse() %></td>
                                                    <td class="text-muted"><%= personne.getVille() %></td>
                                                    <td class="dropdown">
                                                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-cog text-muted pull-right"></span></a>
                                                        <ul class="dropdown-menu">
                                                            <li><a href="modificationPersonne.jsp?id=<%= personne.getId() %>">Modifier</a></li>
                                                            <li><a href="#" data-href="ServletFaireEffacementPersonne?id=<%= personne.getId() %>" data-toggle="modal" data-target="#confirm-delete">Supprimer</a></li>
                                                        </ul>
                                                    </td>
                                                </tr>
                                                <% 
                                                    } 
                                                    if (personnes == null) {
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

        <script>
            $('#confirm-delete').on('show.bs.modal', function (e) {
                $(this).find('.btn-ok').attr('href', $(e.relatedTarget).data('href'));
            });
        </script>
    </body>
</html>
