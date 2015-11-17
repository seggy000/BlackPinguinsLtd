<%-- 
    Document   : annuairePersonnes
    Created on : Nov 16, 2015, 10:26:27 PM
    Author     : Loïc Megert <loic.megert@he-arc.ch>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                        <a href="#">
                            <span class="h4 font-w600 text-white">Portail commercial</span>
                        </a>
                    </div>
                    <div class="side-content">
                        <ul>
                            <li>
                                <a href="#">Accueil</a>
                            </li>
                            <li class="side-content-header">Gestion des clients</li>
                            <li>
                                <a class="active" href="customers.html">Annuaire de clients</a>
                            </li>
                            <li>
                                <a href="newCustomer.html">Nouveau client</a>
                            </li>
                            <li class="side-content-header">Compte</li>
                            <li>
                                <a href="profile.html">Profil</a>
                            </li>
                            <li>
                                <a href="#">Se d&eacute;connecter</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
            <header id="header-navbar">
                <ul class="pull-right">
                    <li>
                        Connect&eacute; en tant que [Pr&eacute;nom Nom]
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
                <div class="alert alert-success alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <span class="glyphicon glyphicon-ok"></span>&emsp;Message de succ&egrave;s.
                </div>
                <div class="alert alert-danger alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <span class="glyphicon glyphicon-remove"></span>&emsp;Message d'&eacute;chec.
                </div>
                <div class="content">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="block">
                                <div class="block-content">
                                    <form class="form-horizontal" action="customers.html" method="post">
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <input class="form-control" type="text" id="search" name="search" placeholder="Chercher..." required>
                                                <input class="btn btn-default pull-right" type="submit" value="Rechercher">
                                            </div>
                                        </div>
                                    </form>
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
                                                <tr>
                                                    <td class="text-primary">Bla bla...</td>
                                                    <td class="text-primary">Bla bla...</td>
                                                    <td class="text-muted">Bla bla...</td>
                                                    <td class="text-muted">Bla bla...</td>
                                                    <td class="dropdown">
                                                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-cog text-muted pull-right"></span></a>
                                                        <ul class="dropdown-menu">
                                                            <li><a href="editCustomer.html">Modifier</a></li>
                                                            <li><a href="#" data-href="/delete.php?id=23" data-toggle="modal" data-target="#confirm-delete">Supprimer</a></li>
                                                        </ul>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="text-primary">Bla bla...</td>
                                                    <td class="text-primary">Bla bla...</td>
                                                    <td class="text-muted">Bla bla...</td>
                                                    <td class="text-muted">Bla bla...</td>
                                                    <td class="dropdown">
                                                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-cog text-muted pull-right"></span></a>
                                                        <ul class="dropdown-menu">
                                                            <li><a href="editCustomer.html">Modifier</a></li>
                                                            <li><a href="#" data-href="/delete.php?id=23" data-toggle="modal" data-target="#confirm-delete">Supprimer</a></li>
                                                        </ul>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="text-primary">Bla bla...</td>
                                                    <td class="text-primary">Bla bla...</td>
                                                    <td class="text-muted">Bla bla...</td>
                                                    <td class="text-muted">Bla bla...</td>
                                                    <td class="dropdown">
                                                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-cog text-muted pull-right"></span></a>
                                                        <ul class="dropdown-menu">
                                                            <li><a href="editCustomer.html">Modifier</a></li>
                                                            <li><a href="#" data-href="/delete.php?id=23" data-toggle="modal" data-target="#confirm-delete">Supprimer</a></li>
                                                        </ul>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="text-primary">Bla bla...</td>
                                                    <td class="text-primary">Bla bla...</td>
                                                    <td class="text-muted">Bla bla...</td>
                                                    <td class="text-muted">Bla bla...</td>
                                                    <td class="dropdown">
                                                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-cog text-muted pull-right"></span></a>
                                                        <ul class="dropdown-menu">
                                                            <li><a href="editCustomer.html">Modifier</a></li>
                                                            <li><a href="#" data-href="/delete.php?id=23" data-toggle="modal" data-target="#confirm-delete">Supprimer</a></li>
                                                        </ul>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="text-primary">Bla bla...</td>
                                                    <td class="text-primary">Bla bla...</td>
                                                    <td class="text-muted">Bla bla...</td>
                                                    <td class="text-muted">Bla bla...</td>
                                                    <td class="dropdown">
                                                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-cog text-muted pull-right"></span></a>
                                                        <ul class="dropdown-menu">
                                                            <li><a href="editCustomer.html">Modifier</a></li>
                                                            <li><a href="#" data-href="/delete.php?id=23" data-toggle="modal" data-target="#confirm-delete">Supprimer</a></li>
                                                        </ul>
                                                    </td>
                                                </tr>
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
            $('#confirm-delete').on('show.bs.modal', function(e) {
                $(this).find('.btn-ok').attr('href', $(e.relatedTarget).data('href'));
            });
        </script>
    </body>
</html>