/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.ig.ta.dao;

import ch.hearc.ig.ta.business.Commercial;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JulmyS
 */
public class CommerciauxDAO extends DAO {

    private static final Logger logger = Logger.getLogger(CommerciauxDAO.class.getName());

    public CommerciauxDAO() {}
    
    public boolean checkLogin(final String username, final String password) {
        try(PreparedStatement pstmt = c.prepareStatement("SELECT 1 "
                                                         + "FROM commerciaux "
                                                         + "WHERE UPPER(username) = ? AND UPPER(mpd) = ?")) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            try(ResultSet userFound = pstmt.executeQuery()) {
                return userFound.next();
            }
        }catch(SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List getAllCommerciaux() {
        PreparedStatement stmt = null;
        ResultSet commerciauxFound = null;

        List<Commercial> listeCommerciaux = new ArrayList();

        String query = "SELECT nom, prenom ,username, points FROM commerciaux";
        try {

            stmt = c.prepareStatement(query);
            commerciauxFound = stmt.executeQuery();

            String nom;
            String prenom;
            String username;
            int points;
            while (commerciauxFound.next()) {
                nom = commerciauxFound.getString("nom");
                prenom = commerciauxFound.getString("prenom");
                username = commerciauxFound.getString("username");
                points = commerciauxFound.getInt("points");
                Commercial commercial = new Commercial(nom, prenom, username, points);
                listeCommerciaux.add(commercial);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                c.close();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
        return listeCommerciaux;
    }

    public Commercial getCommercialByUsername(String username) {
        PreparedStatement stmt = null;
        ResultSet commercialFound = null;

        Commercial commercial = null;

        String query = "SELECT nom, prenom ,username, points FROM commerciaux  WHERE username =?";
        try {
            stmt = c.prepareStatement(query);
            stmt.setString(1, username);
            commercialFound = stmt.executeQuery();

            if (commercialFound.next()) {
                String nom = commercialFound.getString("nom");
                String prenom = commercialFound.getString("prenom");
                int points = commercialFound.getInt("points");
                commercial = new Commercial(nom, prenom, username, points);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                c.close();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
        return commercial;
    }

    public String returnPasswordByUsername(String username) {
        String password = null;
        PreparedStatement stmt = null;
        ResultSet passwordFound = null;

        String query = "SELECT password FROM commerciaux  WHERE username =?";
        try {
            stmt = c.prepareStatement(query);
            stmt.setString(1, username);
            passwordFound = stmt.executeQuery();

            while (passwordFound.next()) {
                password = passwordFound.getString("password");
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                c.close();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
        return password;
    }
    
    public int updatePoints(final String username, final int points) {
        try(PreparedStatement pstmt = c.prepareStatement("UPDATE commerciaux SET points = ? WHERE username = ?")) {
            pstmt.setInt(1, points);
            pstmt.setString(2, username);
            
            return pstmt.executeUpdate();
        }catch(SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            return -1;
        }
    }
}
