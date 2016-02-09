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
import sarf.util.*;
import sarf.verb.trilateral.augmented.*;

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
public class GerundPattern9 extends TrilateralAugmentedGerund implements IChangedGerundPattern{
    boolean forcedForm1Applying = false;

    public GerundPattern9() {
        super();
    }

    public GerundPattern9(AugmentedTrilateralRoot root, String suffixNo,int patternNo) {
        init(root, suffixNo);
    }


    /**
     * form
     *
     * @return String
     * @todo Implement this
     *   sarf.gerund.trilateral.augmented.TrilateralAugmentedGerund method
     */
    public String form() {
        if (forcedForm1Applying)
            return form1();

        if ((root.getC2() == 'و' || root.getC2() == 'ي') && (root.getC3() != 'و' && root.getC3() != 'ي'))
            return form2();
        return form1();

    }

    public String form1() {
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
            return generateForm();
        }
        return "";
    }

    public String form2() {
        if (suffixNo % 2 == 0)
            return generateForm();
        return "";
    }

    public String generateForm() {
        return "اس"+ArabCharUtil.SKOON+"ت"+ArabCharUtil.KASRA+root.getC1()+ArabCharUtil.SKOON+root.getC2()+ArabCharUtil.FATHA+"ا"+root.getC3()+suffix;
    }


    /**
     * getPattern
     *
     * @return String
     * @todo Implement this
     *   sarf.gerund.trilateral.augmented.TrilateralAugmentedGerund method
     */
    public String getPattern() {
        return "اسْتِفْعَال";
    }

    public void setForcedForm1Applying(boolean forcedForm1Applying) {
        this.forcedForm1Applying = forcedForm1Applying;
    }
}

