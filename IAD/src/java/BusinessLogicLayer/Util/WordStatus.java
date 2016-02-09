/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.Util;

/**
 *
 * @author Fadel
 */
public class WordStatus extends Status
{

    public static final String INSERT_INFO_STATUS = "I";
    public static final String UPDATE_INFO_STATUS = "U";
    public static final String DELETE_INFO_STATUS = "D";
    public static final String TEMP_INFO_STATUS = "T";
    public static final String OLD_VALUES_INFO_STATUS = "O";
    public static final String CONFIRMED_INFO_STATUS = "S";
    public static final String REJECTED_INFO_STATUS = "R";
    public static final String NEED_DELETING = "N";
    public static final int NEED_CHECK_STATUS = 1;
    public static final int NOT_NEED_CHECK_STATUS = 0;
    private String infoStatus;
    private int checkStatus;

    public WordStatus( String infoStatus , int checkStatus )
    {
        this.infoStatus = infoStatus;
        this.checkStatus = checkStatus;
    }

    public int getCheckStatus()
    {
        return checkStatus;
    }

    public void setCheckStatus( int checkStatus )
    {
        this.checkStatus = checkStatus;
    }

    public String getInfoStatus()
    {
        return infoStatus;
    }

    public void setInfoStatus( String infoStatus )
    {
        this.infoStatus = infoStatus;
    }
}
