/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer;

import BusinessLogicLayer.BOManager.Util.EntryExistedException;
import Util.RawNotFoundException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author riad
 * @param <T>
 */
public interface GenericDAO<T> {

    //select operations:
    T getById(Integer id) throws RawNotFoundException;

    public List<T> getByExample(T exampleInstance, String[] excludeProperty);

    public List<T> getByExample(T exampleInstance, String[] excludeProperty, Map restrictions);

    public List<T> getByExample(T exampleInstance);

    /**
     * **
     * those functions are used to find instance without passing infostatus,
     * checkstatus and suggestion properties value. these properties take thier
     * values depending on thier cases.
     */
    public List<T> getConfirmedInstance(T exampleInstance, Map restrictions);

    public List<T> getAddedInstance(T exampleInstance, Map restrictions);

    public List<T> getDeletedInstance(T exampleInstance, Map restrictions);

    public List<T> getTempInstance(T exampleInstance, Map restrictions);

    public List<T> getUpdatedInstance(T exampleInstance, Map restrictions);

    Integer insert(T newInstance);

    /*
     * we need this functions in future to solve colusion cases such as trying to add
     * an entry which alredy exists but it was suggested as updated entry.
     */
    public int insertWithCheck(T newInstance);

    public int insertWithCheck(T newInstance, String[] excludeProperties);

    public int insertWithCheck(T newInstance, String[] excludeProperties, Map restrictions);

    public int insertWithCheck(T newInstance, String[] excludeProperties, Map restrictions, String message) throws EntryExistedException;

    void update(T transientObject);

    void delete(Integer id) throws RawNotFoundException;

    List<T> getAll();
}
