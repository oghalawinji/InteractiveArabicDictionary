/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.Util;

/**
 *
 * @author al.hasan.fadel@gmail.com
 */
public class SuggestionStatus extends Status
{
    //Affirm Status:
    public static final String NOT_CHECKED_YET = "C";
    public static final String AFFIRMED_INFO_STATUS = "A";
    public static final String REJECTED_INFO_STATUS = "R";
    public static final String AFFIRMED_AFTER_ALTERING_INFO_STATUS = "L";
    public static final String NOT_NEED_CHECK = "E"; // indicate that the entry was added by the expert, so it doesn't need check.
    //Info status:
    public static final String INSERT_INFO_STATUS = "I";
    public static final String UPDATE_INFO_STATUS = "U";
    public static final String DELETE_INFO_STATUS = "D";
}
