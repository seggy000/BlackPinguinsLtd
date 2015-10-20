/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;


import MemoryUser.Utilisateurs;
import java.io.IOException;
import java.io.PrintWriter;
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
        String username = null, password = null;
        try {

            HtmlHttpUtils.doHeader("Login Page - Gestion de personnes (CRUD)", out);
            
            username = request.getParameter("username");
            password= request.getParameter("password");
            boolean errorlogin=false;
            if (username != null && password != null) {
                if (!username.equals("") && !password.equals("")) {


                      if(Utilisateurs.verifyUser(username, password)){
                        //CREATION HTTP SESSION
                        //request.getRequestDispatcher("/index.jsp").forward(request, response);
                        HttpSession s= request.getSession(true);
                        s.setAttribute("username", username);
                        response.sendRedirect("index.jsp");
                     }else errorlogin=true;
              }else errorlogin=true;
            }else errorlogin=true;
            
            if(errorlogin){
                out.println("<p>Erreur d'authentification, veuillez préciser username , password");
                out.println("<a href='login.jsp'>reessayer</a>");
                out.println("</body></html>");
            }



        } catch (Exception e) {
            e.printStackTrace();
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
