/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PersistenceLayer;

import org.querybyexample.jpa.Identifiable;

/**
 *
 * @author Omar
 */
public class JPAEntity implements Identifiable<Integer> {

    public static JPAEntity create() {
        return new JPAEntity();
    }

    public JPAEntity() {
    }

    public Integer getIdentity() {
        return null;
    }

    public void setIdentity(Integer id) {

    }

    @Override
    public Integer getId() {
        return this.getIdentity();
    }

    @Override
    public void setId(Integer id) {
        this.setIdentity(id);
    }

    @Override
    public boolean isIdSet() {
        return this.getIdentity() != null;
    }

    public void execludeGeneralProperties() {
    }

}
