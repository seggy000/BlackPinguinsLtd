package ch.hearc.ig.ta.dao;

import ch.hearc.ig.ta.business.Personne;
import static ch.hearc.ig.ta.dao.DAO.c;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author termine
 */
public class PersonneDAO extends DAO {

    private static final Logger logger = Logger.getLogger(PersonneDAO.class.getName());

    public PersonneDAO() {
    };
    
    public List<Personne> research() {
        PreparedStatement stmt = null;
        ResultSet peopleFound = null;

        List<Personne> listPersonnes = new ArrayList<>();

        String query = "SELECT numero, prenom, nom, adresse, ville FROM personne ORDER BY prenom";

        try {
            stmt = c.prepareStatement(query);
            peopleFound = stmt.executeQuery();

            while (peopleFound.next()) {
                listPersonnes.add(new Personne(peopleFound.getLong("numero"), peopleFound.getString("nom"), peopleFound.getString("prenom"), peopleFound.getString("adresse"), peopleFound.getString("ville")));
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            try {
                peopleFound.close();
                stmt.close();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
        return listPersonnes;
    }
    
    public List<Personne> research(String search) {
        PreparedStatement stmt = null;
        ResultSet peopleFound = null;

        List<Personne> listPersonnes = new ArrayList<>();

        //String query = "SELECT numero, prenom, nom, adresse, ville FROM personne WHERE INSTR(UPPER(prenom), UPPER('" + search + "')) > 0 OR INSTR(UPPER(nom), UPPER('" + search + "')) > 0 OR INSTR(UPPER(adresse), UPPER('" + search + "')) > 0 OR INSTR(UPPER(ville), UPPER('" + search + "')) > 0";
        String query = "SELECT numero, prenom, nom, adresse, ville FROM personne WHERE UPPER(prenom) like UPPER('%" + search + "%') OR UPPER(nom) like UPPER('%" + search + "%') OR UPPER(adresse) like UPPER('%" + search + "%') OR UPPER(ville) like UPPER('%" + search + "%') ORDER BY prenom";
                
        try {
            stmt = c.prepareStatement(query);
            peopleFound = stmt.executeQuery();

            while (peopleFound.next()) {
                listPersonnes.add(new Personne(peopleFound.getLong("numero"), peopleFound.getString("nom"), peopleFound.getString("prenom"), peopleFound.getString("adresse"), peopleFound.getString("ville")));
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            try {
                peopleFound.close();
                stmt.close();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
        return listPersonnes;
    }

    public Vector<Personne> research(Personne p) {
        Statement stmt = null;
        ResultSet peopleFound = null;
        Vector<Personne> resultList = new Vector();
        
        try {
            String query = null, sn = null, snom = null, sprenom = null, sadr = null, sville = null;
            boolean onedone = false;
            query = "select * from Personne";

            //tester si on a des critères
            if (p.getId() != null) {
                sn = " numero=" + p.getId();
            }
            if (p.getNom() != null) {
                snom = " UPPER(NOM) like UPPER('%" + p.getNom() + "%') ";
            }
            if (p.getPrenom() != null) {
                sprenom = " UPPER(PRENOM) like UPPER('%" + p.getPrenom() + "%') ";
            }
            if (p.getAdresse() != null) {
                sadr = " UPPER(ADRESSE) like UPPER('%" + p.getAdresse() + "%') ";
            }
            if (p.getVille() != null) {
                sville = " UPPER(VILLE) like UPPER('%" + p.getVille() + "%') ";
            }
            //si critères, contruire la clause where
            if (sn != null || snom != null || sprenom != null || sadr != null || sville != null) {
                query = query.concat(" WHERE ");
            }
            //construction de la clause where
            if (sn != null) {
                query = query.concat(sn);
                onedone = true;
            }
            if (snom != null) {
                if (onedone) {
                    query = query.concat(" AND ");
                }
                query = query.concat(snom);
                onedone = true;
            }
            if (sprenom != null) {
                if (onedone) {
                    query = query.concat(" AND ");
                }
                query = query.concat(sprenom);
                onedone = true;
            }
            if (sadr != null) {
                if (onedone) {
                    query = query.concat(" AND ");
                }
                query = query.concat(sadr);
                onedone = true;
            }
            if (sville != null) {
                if (onedone) {
                    query = query.concat(" AND ");
                }
                query = query.concat(sville);
                onedone = true;
            }

            System.out.println(query);
            stmt = c.createStatement(); //create a statement
            peopleFound = stmt.executeQuery(query);

            while (peopleFound.next()) {
                Long n = peopleFound.getLong("NUMERO");
                String nom = peopleFound.getString("NOM");
                String prenom = peopleFound.getString("PRENOM");
                String adresse = peopleFound.getString("ADRESSE");
                String ville = peopleFound.getString("VILLE");
                Personne pers = new Personne();
                pers.setId(n);
                pers.setNom(nom);
                pers.setPrenom(prenom);
                pers.setAdresse(adresse);
                pers.setVille(ville);
                resultList.add(pers);
                System.out.println(n + "\t" + nom + "\t" + prenom + "\t" + adresse + "\t" + ville);
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            try {
                peopleFound.close();
                stmt.close();
                return resultList;
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, null, ex);
                return null;
            }
        }

    }

    public Long create(Personne p) {
        OraclePreparedStatement pstmt = null;
        ResultSet rs = null;
        Long returnNumero = null;
        
        try {
            String query = "insert into Personne(nom,prenom,adresse,ville) values (?,?,?,?) returning numero into ?";
            System.out.println("insertquery ->" + query);

            pstmt = (OraclePreparedStatement) c.prepareStatement(query); //create a statement
            pstmt.setString(1, p.getNom());
            pstmt.setString(2, p.getPrenom());
            pstmt.setString(3, p.getAdresse());
            pstmt.setString(4, p.getVille());
            pstmt.registerReturnParameter(5, OracleTypes.NUMBER);

            int count = pstmt.executeUpdate();
            DAO.commit();

            if (count > 0) {
                rs = pstmt.getReturnResultSet(); //rest is not null and not empty
                while (rs.next()) {
                    returnNumero = rs.getLong(1);
                    System.out.println(returnNumero);

                }
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
                pstmt.close();
                return returnNumero;
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, null, ex);
                return null;
            }
        }
    }

    public Long update(Personne p) {
        int executeUpdate = 0;
        
        if (p.getId() != null) {//update via l'identifiant numero
            Statement stmt = null;
            
            try {
                // String q="update PERSONNE SET nom='a',prenom='a', adresse='a', ville='a' where numero=2";
                String query = null, endquery = null, snom = null, sprenom = null, sadr = null, sville = null;
                boolean onedone = false;
                query = "update PERSONNE SET";
                endquery = " WHERE numero=" + p.getId();

                if (p.getNom() != null) {
                    snom = " NOM='" + p.getNom() + "'";
                }
                if (p.getPrenom() != null) {
                    sprenom = " PRENOM='" + p.getPrenom() + "'";
                }
                if (p.getAdresse() != null) {
                    sadr = " ADRESSE='" + p.getAdresse() + "'";
                }
                if (p.getVille() != null) {
                    sville = " VILLE='" + p.getVille() + "'";
                }

                if (snom != null) {
                    query = query.concat(snom);
                    onedone = true;
                }
                if (sprenom != null) {
                    if (onedone) {
                        query = query.concat(",");
                    }
                    query = query.concat(sprenom);
                    onedone = true;
                }
                if (sadr != null) {
                    if (onedone) {
                        query = query.concat(",");
                    }
                    query = query.concat(sadr);
                    onedone = true;
                }
                if (sville != null) {
                    if (onedone) {
                        query = query.concat(",");
                    }
                    query = query.concat(sville);
                    onedone = true;
                }

                query = query.concat(endquery);

                System.out.println("updatequery ->" + query);

                //create a statement
                stmt = c.createStatement();
                executeUpdate = stmt.executeUpdate(query);
                DAO.commit();
                System.out.println(executeUpdate + " Rows modified");
            } catch (Exception ex) {
                logger.log(Level.SEVERE, null, ex);
            } finally {
                try {
                    stmt.close();
                    return new Long(executeUpdate);
                } catch (SQLException ex) {
                    logger.log(Level.SEVERE, null, ex);
                    return new Long(executeUpdate);
                }
            }
        } else {
            return new Long(executeUpdate);
        }
    }

    public Long delete(Personne p) {
        int executeUpdate = 0;
        
        if (p.getId() != null) {//update via l'identifiant numero
            PreparedStatement pstmt = null;

            try {
                String q = "delete from PERSONNE where numero=?";

                System.out.println("deletequery ->" + q);

                pstmt = c.prepareStatement(q); //create a statement
                //create a statement
                pstmt.setLong(1, p.getId());
                executeUpdate = pstmt.executeUpdate();
                DAO.commit();
                System.out.println(executeUpdate + " Rows modified");
            } catch (Exception ex) {
                logger.log(Level.SEVERE, null, ex);
            } finally {
                try {
                    pstmt.close();
                    return new Long(executeUpdate);
                } catch (SQLException ex) {
                    logger.log(Level.SEVERE, null, ex);
                    return new Long(executeUpdate);
                }
            }
        } else {
            return new Long(executeUpdate);
        }
    }
}
