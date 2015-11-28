package ch.hearc.ig.ta.services;

import ch.hearc.ig.ta.business.Achievement;
import ch.hearc.ig.ta.business.Commercial;
import ch.hearc.ig.ta.dao.AchievementsDAO;
import ch.hearc.ig.ta.dao.CommerciauxDAO;
import ch.hearc.ig.ta.dao.DAO;
import ch.hearc.ig.ta.dao.ObtentionsDao;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Geoffroy Megert <geoffroy.megert@he-arc.ch>
 */
public abstract class Services {

    private static final Logger logger = Logger.getLogger(Services.class.getName());

    private static final CommerciauxDAO commerciauxDao = new CommerciauxDAO();
    private static final AchievementsDAO achievementsDao = new AchievementsDAO();
    private static final ObtentionsDao obtentionsDao = new ObtentionsDao();
    private static Commercial commercial;

    private static void getCommercial(final String username) {
        commercial = commerciauxDao.getCommercialByUsername(username);
    }

    public static String getNomCommercial(final String username) {
        getCommercial(username);

        StringBuilder sb = new StringBuilder();

        sb.append(commercial.getPrenom());
        sb.append(" ");
        sb.append(commercial.getNom());

        return sb.toString();
    }

    public static int getLevel(final String username) {
        getCommercial(username);

        return commercial.getLevel();
    }

    private static String getLevelInformation(final String username, final String requiredInformation) {
        getCommercial(username);
        
        final int MULTIPLIER = 100;
        String levelName;
        int levelPoints;
        int levelPointsGap;
        int level = getLevel(username);
        int points = getPoints(username);
        
        if (level < 5) {
            levelName = "Débutant";
            levelPoints = 5 * MULTIPLIER;
            levelPointsGap = levelPoints - points;
        } else if (level < 10) {
            levelName = "Initié";
            levelPoints = 10 * MULTIPLIER;
            levelPointsGap = levelPoints - points;
        } else if (level < 15) {
            levelName = "Professionnel";
            levelPoints = 15 * MULTIPLIER;
            levelPointsGap = levelPoints - points;
        } else if (level < 25) {
            levelName = "Connaisseur";
            levelPoints = 25 * MULTIPLIER;
            levelPointsGap = levelPoints - points;
        } else if (level < 50) {
            levelName = "Confirmé";
            levelPoints = 50 * MULTIPLIER;
            levelPointsGap = levelPoints - points;
        } else if (level < 100) {
            levelName = "Expert";
            levelPoints = 100 * MULTIPLIER;
            levelPointsGap = levelPoints - points;
        } else {
            levelName = "Légendaire";
            levelPoints = Integer.MAX_VALUE;
            levelPointsGap = levelPoints - points;
        }
        
        switch(requiredInformation) {
            case "levelName":
                return levelName;
            case "levelPoints":
                return String.valueOf(levelPoints);
            case "levelPointsGap":
                return String.valueOf(levelPointsGap);
            default:
                return "Cette information est inexistante";
        }
    }
    
    public static String getLevelName(final String username) {
        return getLevelInformation(username, "levelName");
    }
    
    public static int getLevelPoints(final String username) {
        return Integer.valueOf(getLevelInformation(username, "levelPoints"));
    }
    
    public static int getLevelPointsGap(final String username) {
        return Integer.valueOf(getLevelInformation(username, "levelPointsGap"));
    }
    
    public static int getPoints(final String username) {
        getCommercial(username);
        
        return commercial.getPoints();
    }
    
    public static boolean addPoints(final String username, final int points) {
        getCommercial(username);

        int result = commerciauxDao.updatePoints(username, points);

        if (result > 0) {
            commercial.setPoints(commercial.getPoints() + points);
            getLevelAchievement(username);
            DAO.commit();
            return true;
        } else {
            DAO.rollback();
            return false;
        }
    }

   
    public static int getAchievementsNumber(final String username) {
        return achievementsDao.countAchievementsByCommercial(username);
    }
    
    public static int getNotAchievedAchievementsNumber(final String username) {
        return achievementsDao.countNotAchievedAchievementsByCommercial(username);
    }

    public static List<Achievement> getUserAchievements(final String username) {
        List<Achievement> achievements = new LinkedList<>();
        
        achievements.addAll(achievementsDao.getAchievementsByCommercial(username));
        achievements.addAll(achievementsDao.getNotAchievedAchievementsByCommercial(username));
        
        return achievements;
    }

    public static boolean checkUserAchievement(final String username, final String achievementLabel) {
        return achievementsDao.checkUserAchievement(username, achievementLabel);
    }

    public static Achievement addAchievement(final String username, final String achievementLabel) {
        int result = obtentionsDao.insert(username, achievementLabel);
        
        if (result > 0) {
            getLevelAchievement(username);
            DAO.commit();
            
            Achievement achievement = achievementsDao.getAchievementByLabel(achievementLabel);
            
            if(achievement != null) {
                return achievement;
            }
        } else {
            DAO.rollback();
        }
        
        return null;
    }

    public static void getLevelAchievement(final String username) {
        String levelName = getLevelName(username);

        String achievement = "Niveau " + levelName + " atteint !";

        if (!checkUserAchievement(username, achievement)) {
            if (addAchievement(username, achievement) == null) {
                logger.log(Level.SEVERE, "Une erreur s'est produite lors de l'attribution de la récompense \"" + achievement + "\".");
            }
        }
    }
}
