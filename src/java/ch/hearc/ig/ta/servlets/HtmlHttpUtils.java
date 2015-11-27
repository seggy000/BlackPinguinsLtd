/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.ig.ta.servlets;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author termine
 */
public class HtmlHttpUtils {

    public static void doHeader(String titre, PrintWriter out) {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>" + titre + "</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>" + titre + "</h1><br>");

    }

    public static void doFooter(PrintWriter out) {
        out.println("<div><a href='annuairePersonnes.jsp'>Retour à l'annuaire</a></div>");
        out.println("</body>");
        out.println("</html>");
    }

    public static boolean isAuthenticate(HttpServletRequest request) {
        return request.getSession(false).getAttribute("username") != null;
    }

}
