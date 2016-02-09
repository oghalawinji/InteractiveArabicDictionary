package BusinessLogicLayer.BOManager.Util;

/**
 *
 * @author al.hasan.fadel@gmail.com
 */
public class RecurrenceException extends Exception
{

    private String tableName;
    private int[] rows;

    public RecurrenceException( String message , String tableName , int[] rows )
    {
        super( message );
        this.tableName = tableName;
        this.rows = rows;
    }
}
