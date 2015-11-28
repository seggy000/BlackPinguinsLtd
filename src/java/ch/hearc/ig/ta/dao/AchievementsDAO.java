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
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JulmyS
 */
public class AchievementsDAO extends DAO {

    private static final Logger logger = Logger.getLogger(AchievementsDAO.class.getName());

    public AchievementsDAO() {
    }

    public List getAllAchievements() {
        PreparedStatement stmt = null;
        ResultSet achievementsFound = null;

        List<Achievement> listAchievements = new ArrayList<>();

        String query = "SELECT libelle, description FROM achievements";

        try {
            stmt = c.prepareStatement(query);
            achievementsFound = stmt.executeQuery();

            String label;
            String description;
            while (achievementsFound.next()) {
                label = achievementsFound.getString("libelle");
                description = achievementsFound.getString("description");
                Achievement achievement = new Achievement(label, description);
                listAchievements.add(achievement);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            try {
                achievementsFound.close();
                stmt.close();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
        return listAchievements;
    }
    
    public Achievement getAchievementByLabel(final String label) {
        try(PreparedStatement pstmt = c.prepareStatement("SELECT libelle, description "
                                                         + "FROM achievements "
                                                         + "WHERE UPPER(libelle) = UPPER(?)")) {
            pstmt.setString(1, label);
            
            try(ResultSet achievementFound = pstmt.executeQuery()) {
                String description;
                Achievement achievement = null;
                
                if (achievementFound.next()) {
                    description = achievementFound.getString("description");
                    achievement = new Achievement(label, description);
                }
                
                return achievement;
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List getAchievementsByCommercial(String username) {
        try(PreparedStatement pstmt = c.prepareStatement("SELECT a.libelle, a.description, o.date_obtention "
                                                         + "FROM achievements a "
                                                           + "INNER JOIN obtentions o "
                                                             + "ON o.ACH_Numero = a.numero "
                                                           + "INNER JOIN Commerciaux c "
                                                             + "ON o.COMM_Numero = c.numero "
                                                         + "WHERE UPPER(c.username) = UPPER(?) "
                                                         + "ORDER BY a.libelle")) {
            pstmt.setString(1, username);
            
            try(ResultSet achievementsFound = pstmt.executeQuery()) {
                String label;
                String description;
                Date obtentionDate;
                List<Achievement> listAchievements = new ArrayList<>();
                
                while (achievementsFound.next()) {
                    label = achievementsFound.getString("libelle");
                    description = achievementsFound.getString("description");
                    obtentionDate = achievementsFound.getDate("date_obtention");

                    Achievement achievement = new Achievement(label, description, obtentionDate);
                    listAchievements.add(achievement);
                }
                
                return listAchievements;
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List getNotAchievedAchievementsByCommercial(String username) {
        try(PreparedStatement pstmt = c.prepareStatement("SELECT libelle, description "
                                                         + "FROM achievements "
                                                         + "WHERE numero NOT IN (SELECT a.numero "
                                                                                 + "FROM achievements a "
                                                                                   + "INNER JOIN obtentions o "
                                                                                     + "ON o.ACH_Numero = a.numero "
                                                                                   + "INNER JOIN Commerciaux c "
                                                                                     + "ON o.COMM_Numero = c.numero "
                                                                                 + "WHERE UPPER(c.username) = UPPER(?)) "
                                                         + "ORDER BY libelle")) {
            pstmt.setString(1, username);
            
            try(ResultSet achievementsFound = pstmt.executeQuery()) {
                String label;
                String description;
                List<Achievement> listAchievements = new ArrayList<>();
                
                while (achievementsFound.next()) {
                    label = achievementsFound.getString("libelle");
                    description = achievementsFound.getString("description");

                    Achievement achievement = new Achievement(label, description);
                    listAchievements.add(achievement);
                }
                
                return listAchievements;
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public int countAchievementsByCommercial(String username) {
        try(PreparedStatement pstmt = c.prepareStatement("SELECT COUNT(a.numero) nbAchievements "
                                                         + "FROM achievements a "
                                                           + "INNER JOIN obtentions o "
                                                             + "ON o.ACH_Numero = a.numero "
                                                           + "INNER JOIN Commerciaux c "
                                                             + "ON o.COMM_Numero = c.numero "
                                                         + "WHERE UPPER(c.username) = UPPER(?)")) {
            pstmt.setString(1, username);
            
            try(ResultSet achievementsCount = pstmt.executeQuery()) {

                achievementsCount.next();
                return achievementsCount.getInt("nbAchievements");
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    public int countNotAchievedAchievementsByCommercial(String username) {
        try(PreparedStatement pstmt = c.prepareStatement("SELECT COUNT(numero) nbAchievementsNonObtenus "
                                                         + "FROM achievements "
                                                         + "WHERE numero NOT IN (SELECT a.numero "
                                                                                + "FROM achievements a "
                                                                                  + "INNER JOIN obtentions o "
                                                                                    + "ON o.ACH_Numero = a.numero "
                                                                                  + "INNER JOIN Commerciaux c "
                                                                                    + "ON o.COMM_Numero = c.numero "
                                                                                + "WHERE UPPER(c.username) = UPPER(?))")) {
            pstmt.setString(1, username);
            
            try(ResultSet notAchievedAchievementsCount = pstmt.executeQuery()) {

                notAchievedAchievementsCount.next();
                return notAchievedAchievementsCount.getInt("nbAchievementsNonObtenus");
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    public boolean checkUserAchievement(final String username, final String achievementLabel) {
        try (PreparedStatement pstmt = c.prepareStatement("SELECT 1 "
                                                          + "FROM achievements a "
                                                            + "INNER JOIN obtentions o "
                                                              + "ON o.ACH_Numero = a.numero "
                                                            + "INNER JOIN Commerciaux c "
                                                              + "ON o.COMM_Numero = c.numero "
                                                          + "WHERE UPPER(c.username) = UPPER(?) AND UPPER(a.libelle) = UPPER(?)")) {
            pstmt.setString(1, username);
            pstmt.setString(2, achievementLabel);

            try (ResultSet achievementFound = pstmt.executeQuery()) {
                return achievementFound.next();
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
