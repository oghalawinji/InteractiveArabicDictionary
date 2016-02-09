/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import PersistenceLayer.Video;

/**
 *
 * @author Omar
 */
public class VideoJPADAO extends GenericJPADAO<Video> {

    public VideoJPADAO() {
        super(Video.class);
    }

}