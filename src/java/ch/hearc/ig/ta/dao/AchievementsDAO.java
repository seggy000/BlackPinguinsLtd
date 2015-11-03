/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.ig.ta.dao;

import ch.hearc.ig.ta.business.Achievement;
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
public class AchievementsDAO {

    Connection c;

    public AchievementsDAO() {
        c = DBDataSource.getJDBCConnection();
    }

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
            ex.printStackTrace();
        } finally {
            try {
                stmt.close();
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listAchievements;
    }

    public List getAchievementsByCommercial(String username) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Achievement> listAchievements = new ArrayList<>();

        String query = "SELECT a.libelle, rca.date_obtention FROM Achivements a INNER JOIN REL_COM_ACH rca on rca.ACH_Numero = numero INNER JOIN Commerciaux c on rca.COMM_Numero = c.numero WHERE username = ?";
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
            ex.printStackTrace();
        }finally{
            try{
                stmt.close();
                c.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        return listAchievements;
    }

}
