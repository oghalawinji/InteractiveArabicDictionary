/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DictionaryBeans;

import BusinessLogicLayer.BOManager.DifficultyDegreeManager;
import BusinessLogicLayer.BOManager.EpochManager;
import BusinessLogicLayer.BOManager.GenderManager;
import BusinessLogicLayer.BOManager.NumberManager;
import BusinessLogicLayer.BOManager.OriginManager;
import BusinessLogicLayer.BOManager.ParticleTypeManager;
import BusinessLogicLayer.BOManager.PatternManager;
import BusinessLogicLayer.BOManager.RegionManager;
import BusinessLogicLayer.BOManager.SemanticScopeBOManager;
import BusinessLogicLayer.BOManager.SourceBOManager;
import BusinessLogicLayer.BOManager.SpecializationManager;
import BusinessLogicLayer.BOManager.SpreadingDegreeManager;
import BusinessLogicLayer.BOManager.TransitivityCasesManager;
import BusinessLogicLayer.BOManager.TypeManager;
import java.util.List;

/**
 *
 * @author al.hasan.fadel@gmail.com
 */
public class DictionaryListsBean
{

    public List<String> getAllPatterns()
    {
        return PatternManager.getAll();
    }

    public List<String> getAllVerbsPatterns()
    {
        return PatternManager.getAllVerbPatterns();
    }

    public List<String> getAllNounsPatterns()
    {
        return PatternManager.getAllNounPatterns();
    }
    public List<String> getAllTransitivityCases()
    {
        return TransitivityCasesManager.getAll();
    }

    public List<String> getAllRegions()
    {
        return RegionManager.getAll();
    }

    public List<String> getAllSpreadings()
    {
        return SpreadingDegreeManager.getAll();
    }

    public List<String> getAllDifficultyDegrees()
    {
        return DifficultyDegreeManager.getAll();
    }

    public List<String> getAllEpochs()
    {
        return EpochManager.getAll();
    }

    public List<String> getAllSpecializationss()
    {
        return SpecializationManager.getAll();
    }

    public List<String> getAllSemanticScops()
    {
        return SemanticScopeBOManager.getAll();
    }

    public List<String> getAllOrigins()
    {
        return OriginManager.getAll();
    }

    public List<String> getAllTypes()
    {
        return TypeManager.getAll();
    }

    public List<String> getAllGenders()
    {
        return GenderManager.getAll();
    }

    public List<String> getAllSources()
    {
        return SourceBOManager.getAll();
    }

    public List<String> getAllNumbers()
    {
        return NumberManager.getAll();
    }

    public List<String> getAllParticleTypes()
    {
        return ParticleTypeManager.getAll();
    }
}
