package ch.hearc.ig.ta.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Geoffroy Megert <geoffroy.megert@he-arc.ch>
 */
public abstract class DAO {
    
    private static final Logger logger = Logger.getLogger(DAO.class.getName());
    protected static Connection c;

    public DAO() {
        DAO.openConnection();
    }
    
    public static void openConnection() {
        if(c == null) {
            c = DBDataSource.getJDBCConnection();
        }
    }
    
    public static void closeConnection() {
        try {
            c.close();
            c = null;
        }catch(SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
    
    public static void commit() {
        try {
            c.commit();
        }catch(SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
    
    public static void rollback() {
        try {
            c.rollback();
        }catch(SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
}
