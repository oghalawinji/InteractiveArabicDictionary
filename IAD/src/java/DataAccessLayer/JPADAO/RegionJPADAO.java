/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import PersistenceLayer.Region;

/**
 *
 * @author Omar
 */
public class RegionJPADAO extends GenericJPADAO<Region> {

    public RegionJPADAO() {
        super(Region.class);
    }

}