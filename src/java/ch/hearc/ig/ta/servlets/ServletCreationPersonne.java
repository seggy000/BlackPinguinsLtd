package ch.hearc.ig.ta.servlets;

import ch.hearc.ig.ta.business.Achievement;
import ch.hearc.ig.ta.business.AlertMessage;
import ch.hearc.ig.ta.business.Personne;
import ch.hearc.ig.ta.dao.PersonneDAO;
import ch.hearc.ig.ta.services.Services;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author termine
 */
public class ServletCreationPersonne extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        String nom = null, prenom = null, adresse = null, ville = null;

        try {
            if (!HtmlHttpUtils.isAuthenticate(request)) {
                request.getRequestDispatcher("login.jsp").forward(request,response);
            }
            
            HttpSession s = request.getSession(true);
            ArrayList<AlertMessage> alertMessages = (ArrayList<AlertMessage>) s.getAttribute("alertMessages");

            nom = request.getParameter("lastname");
            prenom = request.getParameter("firstname");
            adresse = request.getParameter("address");
            ville = request.getParameter("city");

            if (nom != null && prenom != null) {
                if (!nom.equals("") && !prenom.equals("")) {
                    PersonneDAO p = new PersonneDAO();
                    Long errorCode = p.create(new Personne(nom, prenom, adresse, ville));

                    if (errorCode == 0l) {
                        alertMessages.add(new AlertMessage(true, "Une erreur s'est produite lors de la cr&eacute;ation du client. Veuillez ressayer plus tard."));
                        s.setAttribute("alertMessages", alertMessages);
                        request.getRequestDispatcher("/annuairePersonnes.jsp").forward(request, response);
                    } else {
                        alertMessages.add(new AlertMessage(false, "Client cr&eacute;&eacute; avec succ&egrave;s."));
                    }

                    String username = (String) request.getSession(false).getAttribute("username");
                    String achievementName = "Premier client";

                    if (!Services.checkUserAchievement(username, achievementName)) {
                        Achievement achievement = Services.addAchievement(username, achievementName, s);

                        if (achievement == null) {
                            alertMessages.add(new AlertMessage(true, "Une erreur s'est produite lors de l'attribution de la récompense \"" + achievementName + "\"."));
                        } else {
                            ArrayList<Achievement> lastUnlockedAchievements = (ArrayList<Achievement>) s.getAttribute("lastUnlockedAchievements");
                            lastUnlockedAchievements.add(achievement);
                            s.setAttribute("lastUnlockedAchievements", lastUnlockedAchievements);
                        }
                    }

                    boolean addingOK = Services.addPoints(username, 10, s);

                    if (!addingOK) {
                        alertMessages.add(new AlertMessage(true, "Une erreur s'est produite lors de l'ajout des points pour la cr&eacute;ation d'un nouveau client."));
                    }
                } else {
                    alertMessages.add(new AlertMessage(true, "Une erreur s'est produite lors de la cr&eacute;ation du client. Veuillez ressayer plus tard."));
                }
            } else {
                alertMessages.add(new AlertMessage(true, "Une erreur s'est produite lors de la cr&eacute;ation du client. Veuillez ressayer plus tard."));
            }
            
            s.setAttribute("alertMessages", alertMessages);
            request.getRequestDispatcher("/annuairePersonnes.jsp").forward(request, response);
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
