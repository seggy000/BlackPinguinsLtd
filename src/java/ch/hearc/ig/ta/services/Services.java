package ch.hearc.ig.ta.services;

import ch.hearc.ig.ta.business.Commercial;
import ch.hearc.ig.ta.dao.AchievementsDAO;
import ch.hearc.ig.ta.dao.CommerciauxDAO;
import ch.hearc.ig.ta.dao.DAO;

/**
 *
 * @author Geoffroy Megert <geoffroy.megert@he-arc.ch>
 */
public class Services {

    private final CommerciauxDAO commerciauxDao = new CommerciauxDAO();
    private final AchievementsDAO achievementsDao = new AchievementsDAO();
    private Commercial commercial;

    private void getCommercial(final String username) {
        commercial = commerciauxDao.getCommercialByUsername(username);
    }
            
    public String getNomCommercial(final String username) {
        if(commercial == null) {
            getCommercial(username);
        }
        
        StringBuilder sb = new StringBuilder();
        
        sb.append(commercial.getNom());
        sb.append(" ");
        sb.append(commercial.getPrenom());
        
        return sb.toString();
    }
    
    public int getBadgeNumber(final String username) {
        return achievementsDao.countAchievementsByCommercial(username);
    }
    
    public int getLevel(final String username) {
        if(commercial == null) {
            getCommercial(username);
        }
        
        return commercial.getLevel();
    }
    
    public boolean addPoints(final String username, final int points) {
        if(commercial == null) {
            getCommercial(username);
        }
        
        int result = commerciauxDao.updatePoints(username, points);
        
        if(result > 0) {
            commercial.setPoints(commercial.getPoints() + points);
            DAO.commit();
            return true;
        }else {
            DAO.rollback();
            return false;
        }
    }
}
