/**************************************************************************
 * UserUtilsBase.java, demactor Android
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
import com.tactfactory.demact.entity.User;



import com.jeanlui.demactor.test.utils.TestUtils;

import com.jeanlui.demactor.test.utils.ScoreUtils;

import com.jeanlui.demactor.test.utils.JockeyUtils;


public abstract class UserUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static User generateRandom(android.content.Context ctx){
        User user = new User();

        user.setId1HNY(TestUtils.generateRandomInt(0,100) + 1);
        user.setNaFGHme1("naFGHme1_"+TestUtils.generateRandomString(10));
        user.setSurnFGHame1("surnFGHame1_"+TestUtils.generateRandomString(10));
        user.setScoFGHre1(ScoreUtils.generateRandom(ctx));
        user.setJocFGHkey1(JockeyUtils.generateRandom(ctx));

        return user;
    }

    public static boolean equals(User user1,
            User user2){
        return equals(user1, user2, true);
    }
    
    public static boolean equals(User user1,
            User user2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(user1);
        Assert.assertNotNull(user2);
        if (user1!=null && user2 !=null){
            Assert.assertEquals(user1.getId1HNY(), user2.getId1HNY());
            Assert.assertEquals(user1.getNaFGHme1(), user2.getNaFGHme1());
            Assert.assertEquals(user1.getSurnFGHame1(), user2.getSurnFGHame1());
            if (user1.getScoFGHre1() != null
                    && user2.getScoFGHre1() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(user1.getScoFGHre1().getIdFD1(),
                            user2.getScoFGHre1().getIdFD1());
                }
            }
            if (user1.getJocFGHkey1() != null
                    && user2.getJocFGHkey1() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(user1.getJocFGHkey1().getFbgDFbdf(),
                            user2.getJocFGHkey1().getFbgDFbdf());
                }
            }
        }

        return ret;
    }
}

