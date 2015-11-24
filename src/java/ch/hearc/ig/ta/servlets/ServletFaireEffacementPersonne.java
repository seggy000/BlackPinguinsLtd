package ch.hearc.ig.ta.servlets;

import ch.hearc.ig.ta.dao.PersonneDAO;
import ch.hearc.ig.ta.business.Personne;
import ch.hearc.ig.ta.services.Services;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author termine
 */
public class ServletFaireEffacementPersonne extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        String id = null;

        try {
            if (HtmlHttpUtils.isAuthenticate(request)) {
                id = request.getParameter("id");

                if (id != null) {
                    if (!id.equals("")) {
                        PersonneDAO pdao = new PersonneDAO();
                        pdao.delete(new Personne(Long.parseLong(id), null, null, null, null));

                        String username = (String) request.getSession(false).getAttribute("username");
                        String achievement = "Première suppression";

                        if (!Services.checkUserAchievement(username, achievement)) {
                            boolean achievementOK = Services.addAchievement(username, achievement);

                            if (!achievementOK) {
                                out.println("<p>Une erreur s'est produite lors de l'attribution de la récompense \"" + achievement + "\".</p>");
                            }
                        }
                    }
                }
                request.getRequestDispatcher("/annuairePersonnes.jsp").forward(request, response);
            }
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
