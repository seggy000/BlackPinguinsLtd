package ch.hearc.ig.ta.dao;

import static ch.hearc.ig.ta.dao.DAO.c;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Geoffroy Megert <geoffroy.megert@he-arc.ch>
 */
public class ObtentionsDao extends DAO {
    
    private static final Logger logger = Logger.getLogger(ObtentionsDao.class.getName());
    
    public int insert(final String username, final String achievementLabel) {
        try(PreparedStatement pstmt = c.prepareStatement("INSERT INTO obtentions(date_obtention, comm_numero, ach_numero) "
                                                         + "VALUES (sysdate, (SELECT numero FROM commerciaux WHERE UPPER(username) = UPPER(?)), "
                                                                + "(SELECT numero FROM achievements WHERE UPPER(libelle) = UPPER(?)))")) {
            pstmt.setString(1, username);
            pstmt.setString(2, achievementLabel);
            
            return pstmt.executeUpdate();
        }catch(SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            return -1;
        }
    }
}
