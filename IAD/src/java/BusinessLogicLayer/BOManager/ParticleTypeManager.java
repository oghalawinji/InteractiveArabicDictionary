/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.Util.BLUtil;
import DataAccessLayer.JPADAO.ParticletypeJPADAO;
import PersistenceLayer.Particletype;
import Util.RawNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author riad
 */
public class ParticleTypeManager {

    public static List<String> getAll() {
        List<String> results = new ArrayList<String>();
        ParticletypeJPADAO dao = BLUtil.daoFactory.getParticletypeDAO();
        List<Particletype> typeObjs = dao.getAll();
        for (int i = 0; i < typeObjs.size(); i++) {
            String type = typeObjs.get(i).getParticleType();
            results.add(type);
        }
        return results;
    }

    public static void deleteParticleType(Integer particleTypeId) {
        try {
            ParticletypeJPADAO dao = BLUtil.daoFactory.getParticletypeDAO();
            dao.delete(particleTypeId);
        } catch (RawNotFoundException ex) {
            Logger.getLogger(ParticleTypeManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int suggestAdding(String particletype) throws RawNotFoundException {
        Particletype newParticletype = new Particletype(particletype);
        newParticletype.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
        newParticletype.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
        newParticletype.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());
        return BLUtil.daoFactory.getParticletypeDAO().insertWithCheck(newParticletype, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, BOManagerUtil.getAddRestrictions());

    }
}
