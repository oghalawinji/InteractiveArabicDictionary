package config;

import java.io.File;

 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Gowzancha
 */
public class config {
    public static String dbpath= "sarfDB";
    public static String smtpServer="195.60.236.130";
    public static String dic_mail_add = "fadel.alhassan@hiast.edu.sy";

    public static void main(String[] arg)
    {
        System.out.println( dbpath );
       File[] f =  File.listRoots ();
    }
}
