/**************************************************************************
 * PoneyUtilsBase.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 4, 2016
 *
 **************************************************************************/
package com.tactfactory.demact.test.utils.base;


import junit.framework.Assert;
import com.tactfactory.demact.entity.Poney;



import com.jeanlui.demactor.test.utils.TestUtils;
import com.tactfactory.demact.entity.Jockey;
import com.jeanlui.demactor.test.utils.JockeyUtils;

import com.jeanlui.demactor.test.utils.ScoreUtils;

import java.util.ArrayList;

public abstract class PoneyUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Poney generateRandom(android.content.Context ctx){
        Poney poney = new Poney();

        poney.setIdlioEm1(TestUtils.generateRandomInt(0,100) + 1);
        poney.setIomAiome1("iomAiome1_"+TestUtils.generateRandomString(10));
        ArrayList<Jockey> relatedJockgFhjeys1s = new ArrayList<Jockey>();
        relatedJockgFhjeys1s.add(JockeyUtils.generateRandom(ctx));
        poney.setJockgFhjeys1(relatedJockgFhjeys1s);
        poney.setScorvbnBe1(ScoreUtils.generateRandom(ctx));

        return poney;
    }

    public static boolean equals(Poney poney1,
            Poney poney2){
        return equals(poney1, poney2, true);
    }
    
    public static boolean equals(Poney poney1,
            Poney poney2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(poney1);
        Assert.assertNotNull(poney2);
        if (poney1!=null && poney2 !=null){
            Assert.assertEquals(poney1.getIdlioEm1(), poney2.getIdlioEm1());
            Assert.assertEquals(poney1.getIomAiome1(), poney2.getIomAiome1());
            if (poney1.getJockgFhjeys1() != null
                    && poney2.getJockgFhjeys1() != null) {
                Assert.assertEquals(poney1.getJockgFhjeys1().size(),
                    poney2.getJockgFhjeys1().size());
                if (checkRecursiveId) {
                    for (Jockey jockgFhjeys11 : poney1.getJockgFhjeys1()) {
                        boolean found = false;
                        for (Jockey jockgFhjeys12 : poney2.getJockgFhjeys1()) {
                            if (jockgFhjeys11.getFbgDFbdf() == jockgFhjeys12.getFbgDFbdf()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated jockgFhjeys1 (fbgDFbdf = %s) in Poney (idlioEm1 = %s)",
                                        jockgFhjeys11.getFbgDFbdf(),
                                        poney1.getIdlioEm1()),
                                found);
                    }
                }
            }
            if (poney1.getScorvbnBe1() != null
                    && poney2.getScorvbnBe1() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(poney1.getScorvbnBe1().getIdFD1(),
                            poney2.getScorvbnBe1().getIdFD1());
                }
            }
        }

        return ret;
    }
}

