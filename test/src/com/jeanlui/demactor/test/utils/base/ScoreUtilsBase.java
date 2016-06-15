/**************************************************************************
 * ScoreUtilsBase.java, demactor Android
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
import com.tactfactory.demact.entity.Score;



import com.jeanlui.demactor.test.utils.TestUtils;
import com.tactfactory.demact.entity.Poney;
import com.jeanlui.demactor.test.utils.PoneyUtils;
import com.tactfactory.demact.entity.User;
import com.jeanlui.demactor.test.utils.UserUtils;

import java.util.ArrayList;

public abstract class ScoreUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Score generateRandom(android.content.Context ctx){
        Score score = new Score();

        score.setIdFD1(TestUtils.generateRandomInt(0,100) + 1);
        score.setMoneFGHFGy1(TestUtils.generateRandomInt(0,100));
        ArrayList<Poney> relatedPonRTYeys1s = new ArrayList<Poney>();
        relatedPonRTYeys1s.add(PoneyUtils.generateRandom(ctx));
        score.setPonRTYeys1(relatedPonRTYeys1s);
        ArrayList<User> relatedUseGHHNrs1s = new ArrayList<User>();
        relatedUseGHHNrs1s.add(UserUtils.generateRandom(ctx));
        score.setUseGHHNrs1(relatedUseGHHNrs1s);

        return score;
    }

    public static boolean equals(Score score1,
            Score score2){
        return equals(score1, score2, true);
    }
    
    public static boolean equals(Score score1,
            Score score2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(score1);
        Assert.assertNotNull(score2);
        if (score1!=null && score2 !=null){
            Assert.assertEquals(score1.getIdFD1(), score2.getIdFD1());
            Assert.assertEquals(score1.getMoneFGHFGy1(), score2.getMoneFGHFGy1());
            if (score1.getPonRTYeys1() != null
                    && score2.getPonRTYeys1() != null) {
                Assert.assertEquals(score1.getPonRTYeys1().size(),
                    score2.getPonRTYeys1().size());
                if (checkRecursiveId) {
                    for (Poney ponRTYeys11 : score1.getPonRTYeys1()) {
                        boolean found = false;
                        for (Poney ponRTYeys12 : score2.getPonRTYeys1()) {
                            if (ponRTYeys11.getIdlioEm1() == ponRTYeys12.getIdlioEm1()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated ponRTYeys1 (idlioEm1 = %s) in Score (idFD1 = %s)",
                                        ponRTYeys11.getIdlioEm1(),
                                        score1.getIdFD1()),
                                found);
                    }
                }
            }
            if (score1.getUseGHHNrs1() != null
                    && score2.getUseGHHNrs1() != null) {
                Assert.assertEquals(score1.getUseGHHNrs1().size(),
                    score2.getUseGHHNrs1().size());
                if (checkRecursiveId) {
                    for (User useGHHNrs11 : score1.getUseGHHNrs1()) {
                        boolean found = false;
                        for (User useGHHNrs12 : score2.getUseGHHNrs1()) {
                            if (useGHHNrs11.getId1HNY() == useGHHNrs12.getId1HNY()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated useGHHNrs1 (id1HNY = %s) in Score (idFD1 = %s)",
                                        useGHHNrs11.getId1HNY(),
                                        score1.getIdFD1()),
                                found);
                    }
                }
            }
        }

        return ret;
    }
}

