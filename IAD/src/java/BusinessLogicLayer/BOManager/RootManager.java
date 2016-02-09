/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.BOManager.Util.EntryExistedException;
import BusinessLogicLayer.SystemStateManager;
import BusinessLogicLayer.Util.BLUtil;
import DataAccessLayer.JPADAO.RootJPADAO;
import PersistenceLayer.Root;
import Util.RawNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riad
 */
public class RootManager {

    public List<String> getAllRoots() {
        List<String> results = new ArrayList<String>();
        RootJPADAO dao = BLUtil.daoFactory.getRootDAO();
        List<Root> rootObjs = dao.getAll();
        for (int i = 0; i < rootObjs.size(); i++) {
            String root = rootObjs.get(i).getRoot();
            results.add(root);
        }
        return results;
    }

    public String get(Integer id, String mode) throws RawNotFoundException {
        RootJPADAO dao = BLUtil.daoFactory.getRootDAO();
        Root root = dao.getById(id);
        return get(root, mode);
    }

    public String get(Root root, String mode) throws RawNotFoundException {
        String infoStatus = root.getInfoStatus();
        int checkStatus = root.getChechStatus();

        if (!SystemStateManager.available(mode, infoStatus, checkStatus)) {
            throw new RawNotFoundException("According to your privilige, Root does not found!!");
        }

        String rootStr = root.getRoot();
        return rootStr;
    }

    public boolean suggestDeleting(Integer Id) throws RawNotFoundException {
        RootJPADAO dao = BLUtil.daoFactory.getRootDAO();
        Root updatedRoot = dao.getById(Id);
        return suggestDeleting(updatedRoot);
    }

    public boolean suggestDeleting(Root root) throws RawNotFoundException {
        RootJPADAO dao = BLUtil.daoFactory.getRootDAO();
        root.setInfoStatus("D");
        root.setChechStatus(1);
        dao.update(root);
        return true;
    }

    public boolean affirmDeleting(Integer Id) throws RawNotFoundException {
        try {
            RootJPADAO dao = BLUtil.daoFactory.getRootDAO();
            dao.delete(Id);
            return true;
        } catch (RawNotFoundException ex) {
            throw new RawNotFoundException("Root does not found!!");
        }
    }

    public boolean suggestUpdating(Integer Id, String newObj) throws RawNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean affirmUpdating(Integer Id) throws RawNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static int suggestAdding(String root) throws RawNotFoundException {
        RootJPADAO dao = BLUtil.daoFactory.getRootDAO();
        Root newRoot = new Root(root);
        newRoot.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
        newRoot.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
        newRoot.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());
        return BLUtil.daoFactory.getRootDAO().insertWithCheck(newRoot, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, BOManagerUtil.getAddRestrictions());
    }

    public boolean affirmAddinging(String bObj) throws RawNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static void main(String[] args) {
        try {
            boolean res = new RootManager().affirmDeleting(2);
            System.out.println("res=" + res);
        } catch (RawNotFoundException ex) {
            System.out.println("error");
        }
    }

    public int suggestAdding(Root newRoot) {
        RootJPADAO dao = BLUtil.daoFactory.getRootDAO();
        if (newRoot.getChechStatus() != BOManagerUtil.ADDING_STATUS.getCheckStatus()) {
            newRoot.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
        }
        if (newRoot.getInfoStatus().compareTo(BOManagerUtil.ADDING_STATUS.getInfoStatus()) != 0) {
            newRoot.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
        }

        List<Root> existed = (dao.getByExample(newRoot));
        if (existed.size() == 0) {
            return dao.insert(newRoot);
        } else {
            return existed.get(0).getIdentity();
        }
    }
}
