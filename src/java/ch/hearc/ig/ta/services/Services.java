package ch.hearc.ig.ta.services;

import ch.hearc.ig.ta.business.Achievement;
import ch.hearc.ig.ta.business.Commercial;
import ch.hearc.ig.ta.dao.AchievementsDAO;
import ch.hearc.ig.ta.dao.CommerciauxDAO;
import ch.hearc.ig.ta.dao.DAO;
import ch.hearc.ig.ta.dao.RelComAchDao;
import java.util.List;

/**
 *
 * @author Geoffroy Megert <geoffroy.megert@he-arc.ch>
 */
public class Services {

    private final CommerciauxDAO commerciauxDao = new CommerciauxDAO();
    private final AchievementsDAO achievementsDao = new AchievementsDAO();
    private final RelComAchDao relComAchDao = new RelComAchDao();
    private Commercial commercial;

    private void getCommercial(final String username) {
        commercial = commerciauxDao.getCommercialByUsername(username);
    }
            
    public String getNomCommercial(final String username) {
        if(commercial == null) {
            getCommercial(username);
        }
        
        StringBuilder sb = new StringBuilder();
        
        sb.append(commercial.getPrenom());
        sb.append(" ");
        sb.append(commercial.getNom());
        
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
    
    public List<Achievement> getUserAchievements(final String username) {
        return achievementsDao.getAchievementsByCommercial(username);
    }
    
    public boolean checkUserAchievement(final String username, final String achievementLabel) {
        return achievementsDao.checkUserAchievement(username, achievementLabel);
    }
    
    public boolean addAchievement(final String username, final String achievementLabel) {
        int result = relComAchDao.insert(username, achievementLabel);
        
        if(result > 0) {
            DAO.commit();
            return true;
        }else {
            DAO.rollback();
            return false;
        }
    }
}
