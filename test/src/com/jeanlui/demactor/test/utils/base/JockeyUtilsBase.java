/**************************************************************************
 * JockeyUtilsBase.java, demactor Android
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
import com.tactfactory.demact.entity.Jockey;



import com.jeanlui.demactor.test.utils.TestUtils;
import com.tactfactory.demact.entity.Poney;
import com.jeanlui.demactor.test.utils.PoneyUtils;

import com.jeanlui.demactor.test.utils.UserUtils;

import java.util.ArrayList;

public abstract class JockeyUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Jockey generateRandom(android.content.Context ctx){
        Jockey jockey = new Jockey();

        jockey.setFbgDFbdf(TestUtils.generateRandomInt(0,100) + 1);
        jockey.setDfdfgdDDfgdfg("dfdfgdDDfgdfg_"+TestUtils.generateRandomString(10));
        jockey.setDfgdfgdfgdfFg("dfgdfgdfgdfFg_"+TestUtils.generateRandomString(10));
        ArrayList<Poney> relatedDzerzerBCzes = new ArrayList<Poney>();
        relatedDzerzerBCzes.add(PoneyUtils.generateRandom(ctx));
        jockey.setDzerzerBCze(relatedDzerzerBCzes);
        jockey.setIuytrezBa(UserUtils.generateRandom(ctx));

        return jockey;
    }

    public static boolean equals(Jockey jockey1,
            Jockey jockey2){
        return equals(jockey1, jockey2, true);
    }
    
    public static boolean equals(Jockey jockey1,
            Jockey jockey2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(jockey1);
        Assert.assertNotNull(jockey2);
        if (jockey1!=null && jockey2 !=null){
            Assert.assertEquals(jockey1.getFbgDFbdf(), jockey2.getFbgDFbdf());
            Assert.assertEquals(jockey1.getDfdfgdDDfgdfg(), jockey2.getDfdfgdDDfgdfg());
            Assert.assertEquals(jockey1.getDfgdfgdfgdfFg(), jockey2.getDfgdfgdfgdfFg());
            if (jockey1.getDzerzerBCze() != null
                    && jockey2.getDzerzerBCze() != null) {
                Assert.assertEquals(jockey1.getDzerzerBCze().size(),
                    jockey2.getDzerzerBCze().size());
                if (checkRecursiveId) {
                    for (Poney dzerzerBCze1 : jockey1.getDzerzerBCze()) {
                        boolean found = false;
                        for (Poney dzerzerBCze2 : jockey2.getDzerzerBCze()) {
                            if (dzerzerBCze1.getIdlioEm1() == dzerzerBCze2.getIdlioEm1()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated dzerzerBCze (idlioEm1 = %s) in Jockey (fbgDFbdf = %s)",
                                        dzerzerBCze1.getIdlioEm1(),
                                        jockey1.getFbgDFbdf()),
                                found);
                    }
                }
            }
            if (jockey1.getIuytrezBa() != null
                    && jockey2.getIuytrezBa() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(jockey1.getIuytrezBa().getId1HNY(),
                            jockey2.getIuytrezBa().getId1HNY());
                }
            }
        }

        return ret;
    }
}

