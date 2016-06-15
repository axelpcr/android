/**************************************************************************
 * HiddenEntityUtilsBase.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.test.utils.base;


import junit.framework.Assert;
import com.jeanlui.demactor.entity.HiddenEntity;



import com.jeanlui.demactor.test.utils.TestUtils;


public abstract class HiddenEntityUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static HiddenEntity generateRandom(android.content.Context ctx){
        HiddenEntity hiddenEntity = new HiddenEntity();

        hiddenEntity.setId(TestUtils.generateRandomInt(0,100) + 1);
        hiddenEntity.setContent("content_"+TestUtils.generateRandomString(10));

        return hiddenEntity;
    }

    public static boolean equals(HiddenEntity hiddenEntity1,
            HiddenEntity hiddenEntity2){
        return equals(hiddenEntity1, hiddenEntity2, true);
    }
    
    public static boolean equals(HiddenEntity hiddenEntity1,
            HiddenEntity hiddenEntity2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(hiddenEntity1);
        Assert.assertNotNull(hiddenEntity2);
        if (hiddenEntity1!=null && hiddenEntity2 !=null){
            Assert.assertEquals(hiddenEntity1.getId(), hiddenEntity2.getId());
            Assert.assertEquals(hiddenEntity1.getContent(), hiddenEntity2.getContent());
        }

        return ret;
    }
}

