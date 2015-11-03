/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.ig.ta.dao;

import ch.hearc.ig.ta.business.Commercial;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JulmyS
 */
public class CommerciauxDAO {

    Connection c;

    public CommerciauxDAO() {
        c = DBDataSource.getJDBCConnection();
    }

    public List getAllCommerciaux() {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Commercial> listeCommerciaux = new ArrayList();

        String query = "SELECT nom, prenom ,username FROM commerciaux";
        try {

            stmt = c.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String username = rs.getString("username");
                Commercial commercial = new Commercial(nom, prenom, username);
                listeCommerciaux.add(commercial);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                stmt.close();
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listeCommerciaux;
    }

    public Commercial getCommercialByUsername(String username) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        Commercial commercial = null;

        String query = "SELECT nom, prenom ,username FROM commerciaux  WHERE username =?";
        try {
            stmt = c.prepareStatement(query);
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                commercial = new Commercial(nom, prenom, username);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                stmt.close();
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return commercial;
    }

    public String returnPasswordByUsername(String username) {
        String password = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String query = "SELECT password FROM commerciaux  WHERE username =?";
        try {
            stmt = c.prepareStatement(query);
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            while (rs.next()) {
                password = rs.getString("password");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                stmt.close();
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return password;
    }
}
