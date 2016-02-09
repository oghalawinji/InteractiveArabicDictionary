/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.Util;

import BusinessLogicLayer.Util.Stemmer.Stemmer;
import DataAccessLayer.DAOFactory;

import DataAccessLayer.JPADAOFactory;

/**
 *
 * @author riad
 */
public class BLUtil {

    public static JPADAOFactory daoFactory = DAOFactory.getJPADAOFactory();
    public static Stemmer stemmer = new Stemmer();// = new Stemmer("pre.txt", "suf.txt");
}
