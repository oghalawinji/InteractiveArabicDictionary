/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DictionaryBeans;

import BusinessLogicLayer.BOManager.Util.EntryExistedException;
import BusinessLogicLayer.BusinessObjects.CommonMistakeBO;
import BusinessLogicLayer.BusinessObjects.ExampleBO;
import BusinessLogicLayer.BusinessObjects.LinguisticBenefitBO;
import BusinessLogicLayer.BusinessObjects.MeaningBO;
import Util.RawNotFoundException;
import java.util.List;

/**
 *
 * @author al.hasan.fadel@gmail.com
 */
public abstract class SemanticEntryBean
{

    public abstract List<MeaningBO> addNewMeaning( String discription, String source ) throws RawNotFoundException, EntryExistedException;

    public abstract List<MeaningBO> updateMeaning( String description, String source, int id ) throws RawNotFoundException;

    public abstract List<MeaningBO> deleteMeaning( int id ) throws RawNotFoundException;

    public abstract List<ExampleBO> addNewExample( String example, String source, byte[] sound ) throws RawNotFoundException, EntryExistedException;

    public abstract List<ExampleBO> addNewExample( String example, String source ) throws RawNotFoundException, EntryExistedException;

    public abstract List<ExampleBO> updateExample( String example, String source, byte[] sound, int id ) throws RawNotFoundException;

    public abstract List<ExampleBO> updateExample( String example, String source, int id ) throws RawNotFoundException;

    public abstract List<ExampleBO> deleteExample( int id ) throws RawNotFoundException;

   // public abstract void affirmExampleAdding( int exampleId ) throws RawNotFoundException;

    public abstract List<CommonMistakeBO> addNewCommonMistake( String commonMistake, String source ) throws RawNotFoundException, EntryExistedException;

    public abstract List<CommonMistakeBO> updateCommonMistake( String commonMistake, String source, int id ) throws RawNotFoundException;

    public abstract List<CommonMistakeBO> deleteCommonMistake( int id ) throws RawNotFoundException;

    public abstract List<LinguisticBenefitBO> addNewLinguisticBenefit( String linguisticBenefit, String source ) throws RawNotFoundException, EntryExistedException;

    public abstract List<LinguisticBenefitBO> updateLinguisticBenefit( String linguisticBenefit, String source, int id ) throws RawNotFoundException;

    public abstract List<LinguisticBenefitBO> deleteLinguisticBenefit( int id ) throws RawNotFoundException;
}
