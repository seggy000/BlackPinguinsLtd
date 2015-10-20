/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.pool.OracleDataSource;

/**
 *
 * @author termine
 */
public class DBDataSource {

    private static OracleDataSource ds = null;

    public static Connection getJDBCConnection() {


        try {
            if (ds == null) {
                ds = new OracleDataSource();

                ds.setDriverType("thin");
                ds.setServerName("ne-ege-leto.ig.he-arc.ch");
                ds.setPortNumber(1521);
                ds.setDatabaseName("ens2"); // sid
                ds.setUser("sb_termine");
                ds.setPassword("sb_termine");
            }
            return ds.getConnection();
            // c = ds.getConnection();ds=new DataSource();
            } catch (SQLException ex) {
            Logger.getLogger(DBDataSource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }


    }
}
