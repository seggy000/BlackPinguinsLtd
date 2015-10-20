/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Personne;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author termine
 */
public class PersonneDAO {
    
    public PersonneDAO(){};

    public Vector<Personne>  research(Personne p) {
        Connection conn = DBDataSource.getJDBCConnection();
        Statement stmt = null;
        ResultSet rs = null;
        Vector<Personne> resultList=new Vector();
        try {
            String query=null,sn=null,snom=null,sprenom=null,sadr=null,sville=null;
            boolean onedone=false;
            query= "select * from Personne";
       
          //tester si on a des critères
           if(p.getId()!=null)sn=" numero="+p.getId();
           if(p.getNom()!=null) snom=" NOM like '%" +p.getNom()+"%' ";
           if(p.getPrenom()!=null) sprenom=" PRENOM like '%" +p.getPrenom()+"%' ";
           if(p.getAdresse()!=null) sadr=" ADRESSE like '%" +p.getAdresse()+"%' ";
           if(p.getVille()!=null) sville=" VILLE like '%" +p.getVille()+"%' ";
           //si critères, contruire la clause where
           if (sn!=null || snom!=null || sprenom!=null || sadr!=null || sville!=null) query=query.concat(" WHERE ");
           //construction de la clause where
           if(sn!=null){query=query.concat(sn);onedone=true;}
           if(snom!=null){if (onedone)query=query.concat(" AND "); query=query.concat(snom); onedone=true;}
           if(sprenom!=null){if (onedone)query=query.concat(" AND "); query=query.concat(sprenom); onedone=true;}
           if(sadr!=null){if (onedone)query=query.concat(" AND "); query=query.concat(sadr); onedone=true;}
           if(sville!=null){if (onedone)query=query.concat(" AND "); query=query.concat(sville); onedone=true;}

           System.out.println(query);
           stmt = conn.createStatement(); //create a statement
           rs = stmt.executeQuery(query);

            while (rs.next()) {
                Long n = rs.getLong("NUMERO");
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                String adresse = rs.getString("ADRESSE");
                String ville = rs.getString("VILLE");
                Personne pers=new Personne();
                pers.setId(n);pers.setNom(nom);pers.setPrenom(prenom);pers.setAdresse(adresse);pers.setVille(ville);
                resultList.add(pers);
                System.out.println(n + "\t" + nom + "\t" + prenom + "\t" + adresse + "\t" + ville);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
                 return resultList;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
       
    }

    public Long create(Personne p) {

        Connection conn = DBDataSource.getJDBCConnection();
        OraclePreparedStatement pstmt = null;
        ResultSet rs = null;
        Long returnNumero = null;
        try {

            String query = "insert into Personne(nom,prenom,adresse,ville) values (?,?,?,?) returning numero into ?";
            System.out.println("insertquery ->" + query);

            pstmt = (OraclePreparedStatement) conn.prepareStatement(query); //create a statement
            pstmt.setString(1, p.getNom());
            pstmt.setString(2, p.getPrenom());
            pstmt.setString(3, p.getAdresse());
            pstmt.setString(4, p.getVille());
            pstmt.registerReturnParameter(5, OracleTypes.NUMBER);

            int count = pstmt.executeUpdate();
            conn.commit();

            if (count > 0) {
               rs = pstmt.getReturnResultSet(); //rest is not null and not empty
                while (rs.next()) {
                    returnNumero = rs.getLong(1);
                    System.out.println(returnNumero);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
                return returnNumero;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

   public  Long update(Personne p) {
        int executeUpdate=0;
       if(p.getId()!=null){//update via l'identifiant numero

        Connection conn = DBDataSource.getJDBCConnection();
        Statement stmt = null;
        try {
           // String q="update PERSONNE SET nom='a',prenom='a', adresse='a', ville='a' where numero=2";
            String query = null, endquery = null, snom = null, sprenom = null, sadr = null, sville = null;
            boolean onedone = false;
            query = "update PERSONNE SET";
            endquery = " WHERE numero=" + p.getId();

            if(p.getNom()!=null) snom=" NOM='" +p.getNom()+"'";
            if(p.getPrenom()!=null) sprenom=" PRENOM='" +p.getPrenom()+"'";
            if(p.getAdresse()!=null) sadr=" ADRESSE='" +p.getAdresse()+"'";
            if(p.getVille()!=null) sville=" VILLE='" +p.getVille()+"'";

            if(snom!=null){ query=query.concat(snom); onedone=true;}
            if(sprenom!=null){if (onedone)query=query.concat(","); query=query.concat(sprenom); onedone=true;}
            if(sadr!=null){if (onedone)query=query.concat(","); query=query.concat(sadr); onedone=true;}
            if(sville!=null){if (onedone)query=query.concat(","); query=query.concat(sville); onedone=true;}

            query=query.concat(endquery);
                   
            System.out.println("updatequery ->" + query);

          
                //create a statement
            stmt=conn.createStatement();
            executeUpdate = stmt.executeUpdate(query);
            conn.commit();
            System.out.println( executeUpdate + " Rows modified" ) ;
 

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                conn.close();
                return new Long(executeUpdate);
            } catch (SQLException e) {
                e.printStackTrace();
                return new Long (executeUpdate);
            }
        }
       }else return new Long (executeUpdate);
    }

    public Long delete(Personne p) {

       int executeUpdate=0;
       if(p.getId()!=null){//update via l'identifiant numero

        Connection conn = DBDataSource.getJDBCConnection();
        PreparedStatement pstmt = null;

        try {
            String q="delete from PERSONNE where numero=?";
     
            System.out.println("deletequery ->" + q);

            pstmt = conn.prepareStatement(q); //create a statement
                //create a statement
            pstmt.setLong(1,p.getId());
            executeUpdate = pstmt.executeUpdate();
            conn.commit();
            System.out.println( executeUpdate + " Rows modified" ) ;


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
                conn.close();
                return new Long(executeUpdate);
            } catch (SQLException e) {
                e.printStackTrace();
                return new Long (executeUpdate);
            }
        }
       }else return new Long (executeUpdate);
    }
}
