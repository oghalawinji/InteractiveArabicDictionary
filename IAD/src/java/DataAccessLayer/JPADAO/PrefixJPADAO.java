/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import PersistenceLayer.Prefix;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Omar
 */
public class PrefixJPADAO extends GenericJPADAO<Prefix> {

    public PrefixJPADAO() {
        super(Prefix.class);
    }

}
