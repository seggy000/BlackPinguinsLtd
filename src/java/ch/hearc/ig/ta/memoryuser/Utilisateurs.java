/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.ig.ta.memoryuser;

import ch.hearc.ig.ta.dao.CommerciauxDAO;

/**
 *
 * @author francill
 */
public class Utilisateurs {

//    public static Hashtable<String, String> users = null;

    public static boolean checkPassword(String user, String pw) {
        if (user != null) {
            return new CommerciauxDAO().checkLogin(user, pw);
        } else {
            return false;
        }
    }
//
//    public static boolean verifyUser(String user, String pw) {
//        if (users == null) {
//            users = new Hashtable<String, String>();
//            users.put("termine", "ptermine");
//            users.put("daucourt", "pdaucourt");
//            users.put("francillon", "pfrancillon");
//            users.put("dupont", "pdupont");
//            users.put("t", "t");
//        }
//        System.out.println(user + " " + pw + " " + users.get(user));
//        if (users.get(user) != null) {
//            if (users.get(user).equals(pw)) {
//                return true;
//            } else {
//                return false;
//            }
//        } else {
//            return false;
//        }
//
//    }
}
