package ch.hearc.ig.ta.services;

import ch.hearc.ig.ta.business.Achievement;
import ch.hearc.ig.ta.business.Commercial;
import ch.hearc.ig.ta.dao.AchievementsDAO;
import ch.hearc.ig.ta.dao.CommerciauxDAO;
import ch.hearc.ig.ta.dao.DAO;
import ch.hearc.ig.ta.dao.ObtentionsDao;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Geoffroy Megert <geoffroy.megert@he-arc.ch>
 */
public class Services {

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

    public static String getLevelName(final String username) {

        getCommercial(username);

        String levelName;
        int level = getLevel(username);

        if (level < 5) {
            levelName = "Débutant";
        } else if (level < 10) {
            levelName = "Initié";
        } else if (level < 15) {
            levelName = "Professionnel";
        } else if (level < 25) {
            levelName = "Connaisseur";
        } else if (level < 50) {
            levelName = "Confirmé";
        } else if (level < 100) {
            levelName = "Expert";
        } else {
            levelName = "Légendaire";
        }

        return levelName;
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

    public static List<Achievement> getUserAchievements(final String username) {
        return achievementsDao.getAchievementsByCommercial(username);
    }

    public static boolean checkUserAchievement(final String username, final String achievementLabel) {
        return achievementsDao.checkUserAchievement(username, achievementLabel);
    }

    public static boolean addAchievement(final String username, final String achievementLabel) {
        int result = obtentionsDao.insert(username, achievementLabel);

        if (result > 0) {
            DAO.commit();
            return true;
        } else {
            DAO.rollback();
            return false;
        }
    }

    public static void getLevelAchievement(final String username) {
        String levelName = getLevelName(username);

        String achievement = "Niveau " + levelName + " atteint !";

        if (!checkUserAchievement(username, achievement)) {
            boolean achievementOK = addAchievement(username, achievement);

            if (!achievementOK) {
                logger.log(Level.SEVERE, "Une erreur s'est produite lors de l'attribution de la récompense \"" + achievement + "\".");
            }
        }

        /*        int level = getLevel(username);
         String achievement;

         switch (level) {
         case 1:
         case 5:
         case 10:
         case 15:
         case 25:
         case 50:
         achievement = "Niveau " + level + " atteint !";

         if (!checkUserAchievement(username, achievement)) {
         boolean achievementOK = addAchievement(username, achievement);

         if (!achievementOK) {
         logger.log(Level.SEVERE, "Une erreur s'est produite lors de l'attribution de la récompense \"" + achievement + "\".");
         }
         }
         break;
         }*/
    }
}
