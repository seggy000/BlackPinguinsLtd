package ch.hearc.ig.ta.servlets;

import ch.hearc.ig.ta.business.Achievement;
import ch.hearc.ig.ta.business.AlertMessage;
import ch.hearc.ig.ta.dao.DAO;
import ch.hearc.ig.ta.memoryuser.Utilisateurs;
import ch.hearc.ig.ta.services.Services;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author termine
 */
public class ServletLogin extends HttpServlet {

    private static final Logger logger = Logger.getLogger(ServletLogin.class.getName());

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
        String username = null, password = null;

        try {
            username = request.getParameter("username");
            password = request.getParameter("password");
            boolean errorlogin = false;

            if (username != null && password != null) {
                if (!username.equals("") && !password.equals("")) {
                    
                    DAO.openConnection();
                    if (Utilisateurs.checkPassword(username, password)) {
                        //CREATION HTTP SESSION
                        //request.getRequestDispatcher("/index.jsp").forward(request, response);
                        HttpSession s = request.getSession(true);
                        s.setAttribute("username", username);
                        s.setAttribute("date_connexion", new Date());
                        s.setAttribute("navigateur", request.getHeader("User-Agent"));
                        s.setAttribute("lastUnlockedAchievements", new ArrayList<Achievement>());
                        s.setAttribute("alertMessages", new ArrayList<AlertMessage>());
                        
                        String achievementName = "Première connexion";
                        if (!Services.checkUserAchievement(username, achievementName)) {
                            Achievement achievement = Services.addAchievement(username, achievementName, s);

                            if (achievement == null) {
                                ArrayList<AlertMessage> alertMessages = (ArrayList<AlertMessage>) s.getAttribute("alertMessages");
                                alertMessages.add(new AlertMessage(true, "Une erreur s'est produite lors de l'attribution de la récompense \"" + achievementName + "\"."));
                                s.setAttribute("alertMessages", alertMessages);
                            } else {
                                ArrayList<Achievement> lastUnlockedAchievements = (ArrayList<Achievement>) s.getAttribute("lastUnlockedAchievements");
                                lastUnlockedAchievements.add(achievement);
                                s.setAttribute("lastUnlockedAchievements", lastUnlockedAchievements);
                            }
                        }

                        response.sendRedirect("annuairePersonnes.jsp");
                    } else {
                        errorlogin = true;
                    }
                } else {
                    errorlogin = true;
                }
            } else {
                errorlogin = true;
            }

            if (errorlogin) {
                response.sendRedirect("login.jsp?failed=1");
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
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
