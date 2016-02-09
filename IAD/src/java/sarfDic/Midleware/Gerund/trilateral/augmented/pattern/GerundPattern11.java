/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sarfDic.Midleware.Gerund.trilateral.augmented.pattern;

/**
 *
 * @author Gowzancha
 */

import sarf.gerund.trilateral.augmented.*;
import sarf.verb.trilateral.augmented.*;
import sarf.util.*;
import sarf.gerund.trilateral.*;

/**
 * <p>Title: Sarf Program</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: ALEXO</p>
 *
 * @author Haytham Mohtasseb Billah
 * @version 1.0
 */
public class GerundPattern11 extends TrilateralAugmentedGerund {
    public GerundPattern11() {
        super();
    }

    public GerundPattern11(AugmentedTrilateralRoot root, String suffixNo,int patternNo) {
        super(root, suffixNo);
    }

    /**
     * form
     *
     * @return String
     * @todo Implement this
     *   sarf.gerund.trilateral.augmented.TrilateralAugmentedGerund method
     */
    public String form() {
        switch (suffixNo) {
        case 1:
        case 3:
        case 6:
        case 7:
        case 9:
        case 12:
        case 13:
        case 15:
        case 18:
            return "ا" + root.getC1() + ArabCharUtil.SKOON + root.getC2() + ArabCharUtil.KASRA + "و" + ArabCharUtil.SHADDA + ArabCharUtil.FATHA + "ا" + root.getC3() + suffix;
        }
        return "";

    }

    /**
     * getPattern
     *
     * @return String
     * @todo Implement this
     *   sarf.gerund.trilateral.augmented.TrilateralAugmentedGerund method
     */
    public String getPattern() {
        return "افْعِوَّال";
    }

}
