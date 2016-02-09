package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.EntryExistedException;
import Util.RawNotFoundException;

/**
 *
 * @author riad
 */
public interface BusinessObjectManager<BusinessObject , DataAccessObject>
{

    public BusinessObject get ( Integer id , String mode ) throws RawNotFoundException;

    public BusinessObject get ( DataAccessObject dataAccessObject , String mode ) throws RawNotFoundException;

    public boolean suggestDeleting ( Integer Id ) throws RawNotFoundException;

    public boolean suggestDeleting ( DataAccessObject root ) throws RawNotFoundException;

    public boolean affirmDeleting ( Integer Id ) throws RawNotFoundException;

    public boolean suggestUpdating ( Integer Id , BusinessObject newObj ) throws RawNotFoundException;

    public boolean affirmUpdating ( Integer Id ) throws RawNotFoundException;//updated text could change when affirming it??

    public int suggestAdding ( BusinessObject bObj ) throws EntryExistedException;

    public boolean affirmAddinging ( BusinessObject bObj ) throws RawNotFoundException;
}
