package servlets;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import DAO.PersonneDAO;
import Model.Personne;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author termine
 */
public class ServletListePersonne extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String nom = null, prenom = null, adresse = null, ville = null;
        try {
            HtmlHttpUtils.doHeader("liste des personnes", out);

            if (HtmlHttpUtils.isAuthenticate(request)) {
                nom = request.getParameter("nom");
                prenom = request.getParameter("prenom");
                adresse = request.getParameter("adresse");
                ville = request.getParameter("ville");
                //ATTENTION EXECUTION DE CROSS SITE SCRIPTING
                out.println("recherche de "+ nom + " "+ prenom + " "+ adresse +" " + ville+"<br>");


                PersonneDAO pdao = new PersonneDAO();
                Vector<Personne> v = pdao.research(new Personne(nom, prenom, adresse, ville));
                out.println("<table>");
                for (int i = 0; i < v.size(); i++) {
                    Personne p = v.elementAt(i);
                    out.println("<tr><td>" + p.getId() + " : " + p.getNom() + " , " + p.getPrenom() + " , " + p.getAdresse() + " , " + p.getVille() + "</td><td><a href='ServletMAJPersonne?id=" + p.getId() + "'>edition</a></td><td><a href='ServletEffacerPersonne?id=" + p.getId() + "'>supprimer</a></td></tr>");
                }
                out.println("</table>");
            }
            HtmlHttpUtils.doFooter(out);
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
