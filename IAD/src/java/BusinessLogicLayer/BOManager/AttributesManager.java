package BusinessLogicLayer.BOManager;

import Util.RawNotFoundException;

/**
 *
 * @author Fadel
 */
public interface AttributesManager<AttributeObject,DataAccessObject>
{

    public AttributeObject get ( Integer id , String mode ) throws RawNotFoundException;

    public AttributeObject get ( DataAccessObject dataAccessObject , String mode ) throws RawNotFoundException;

    public boolean suggestDeleting ( Integer Id ) throws RawNotFoundException;

    public boolean suggestDeleting ( DataAccessObject dataAccessObject ) throws RawNotFoundException;

    public boolean affirmDeleting ( Integer Id ) throws RawNotFoundException;

    public boolean suggestUpdating ( Integer Id , AttributeObject newObj ) throws RawNotFoundException;

    public boolean affirmUpdating ( Integer Id ) throws RawNotFoundException;

    public  int suggestAdding ( AttributeObject attribute );

    public boolean affirmAddinging ( AttributeObject attribute ) throws RawNotFoundException;
}
