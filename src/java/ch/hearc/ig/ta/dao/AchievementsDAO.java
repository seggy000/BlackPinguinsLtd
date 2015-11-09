/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.ig.ta.dao;

import ch.hearc.ig.ta.business.Achievement;
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
public class AchievementsDAO extends DAO {

    private static final Logger logger = Logger.getLogger(AchievementsDAO.class.getName());
    
    public AchievementsDAO() {}

    public List getAllAchievements() {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Achievement> listAchievements = new ArrayList<>();

        String query = "SELECT libelle FROM achievements";

        try {
            stmt = c.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String libelle = rs.getString("libelle");
                Achievement achievement = new Achievement(libelle);
                listAchievements.add(achievement);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
                stmt.close();
                c.close();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
        return listAchievements;
    }

    public List getAchievementsByCommercial(String username) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Achievement> listAchievements = new ArrayList<>();

        String query = "SELECT a.libelle, rca.date_obtention FROM Achivements a INNER JOIN REL_COM_ACH rca on rca.ACH_Numero = a.numero INNER JOIN Commerciaux c on rca.COMM_Numero = c.numero WHERE username = ?";
        try {
            stmt = c.prepareStatement(query);
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String libelle = rs.getString("libelle");
                Achievement achievement = new Achievement(libelle);
                listAchievements.add(achievement);
            }
        }catch(SQLException ex){
            logger.log(Level.SEVERE, null, ex);
        }finally{
            try{
                rs.close();
                stmt.close();
                c.close();
            }catch(SQLException ex){
                logger.log(Level.SEVERE, null, ex);
            }
        }
        return listAchievements;
    }

    public int countAchievementsByCommercial(String username) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        int nbAchievements = 0;

        String query = "SELECT COUNT(a.numero) FROM Achivements a INNER JOIN REL_COM_ACH rca on rca.ACH_Numero = a.numero INNER JOIN Commerciaux c on rca.COMM_Numero = c.numero WHERE username = ?";
        try {
            stmt = c.prepareStatement(query);
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            while (rs.next()) {
                nbAchievements = rs.getInt("a.numero");
            }
        }catch(SQLException ex){
            logger.log(Level.SEVERE, null, ex);
        }finally{
            try{
                rs.close();
                stmt.close();
                c.close();
            }catch(SQLException ex){
                logger.log(Level.SEVERE, null, ex);
            }
        }
        return nbAchievements;
    }
    
}
