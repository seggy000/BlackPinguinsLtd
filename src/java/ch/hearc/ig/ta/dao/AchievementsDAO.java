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

    public AchievementsDAO() {
    }

    public List getAllAchievements() {
        PreparedStatement stmt = null;
        ResultSet achievementsFound = null;

        List<Achievement> listAchievements = new ArrayList<>();

        String query = "SELECT libelle FROM achievements";

        try {
            stmt = c.prepareStatement(query);
            achievementsFound = stmt.executeQuery();

            while (achievementsFound.next()) {
                String libelle = achievementsFound.getString("libelle");
                Achievement achievement = new Achievement(libelle);
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

    public List getAchievementsByCommercial(String username) {
        PreparedStatement stmt = null;
        ResultSet achievementsFound = null;

        List<Achievement> listAchievements = new ArrayList<>();

        String query = "SELECT a.libelle, rca.date_obtention FROM achievements a INNER JOIN REL_COM_ACH rca on rca.ACH_Numero = a.numero INNER JOIN Commerciaux c on rca.COMM_Numero = c.numero WHERE UPPER(username) = UPPER(?)";
        try {
            stmt = c.prepareStatement(query);
            stmt.setString(1, username);
            achievementsFound = stmt.executeQuery();

            while (achievementsFound.next()) {
                String libelle = achievementsFound.getString("libelle");
                Achievement achievement = new Achievement(libelle);
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

    public int countAchievementsByCommercial(String username) {
        PreparedStatement stmt = null;
        ResultSet achievementsCount = null;

        int nbAchievements = 0;

        String query = "SELECT COUNT(a.numero) nbAchievements FROM achievements a INNER JOIN REL_COM_ACH rca on rca.ACH_Numero = a.numero INNER JOIN Commerciaux c on rca.COMM_Numero = c.numero WHERE UPPER(username) = UPPER(?)";
        try {
            stmt = c.prepareStatement(query);
            stmt.setString(1, username);
            achievementsCount = stmt.executeQuery();

            while (achievementsCount.next()) {
                nbAchievements = achievementsCount.getInt("nbAchievements");
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            try {
                achievementsCount.close();
                stmt.close();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
        return nbAchievements;
    }

    public boolean checkUserAchievement(final String username, final String achievementLabel) {
        try (PreparedStatement pstmt = c.prepareStatement("SELECT 1 "
                                                          + "FROM achievements a "
                                                            + "INNER JOIN REL_COM_ACH rca "
                                                              + "ON rca.ACH_Numero = a.numero "
                                                            + "INNER JOIN Commerciaux c "
                                                              + "ON rca.COMM_Numero = c.numero "
                                                          + "WHERE UPPER(username) = UPPER(?) AND UPPER(libelle) = UPPER(?)")) {
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
