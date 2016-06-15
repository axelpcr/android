/**************************************************************************
 * SimpleEntityUtilsBase.java, demactor Android
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
import com.jeanlui.demactor.entity.SimpleEntity;



import com.jeanlui.demactor.test.utils.TestUtils;


public abstract class SimpleEntityUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static SimpleEntity generateRandom(android.content.Context ctx){
        SimpleEntity simpleEntity = new SimpleEntity();

        simpleEntity.setId(TestUtils.generateRandomInt(0,100) + 1);

        return simpleEntity;
    }

    public static boolean equals(SimpleEntity simpleEntity1,
            SimpleEntity simpleEntity2){
        return equals(simpleEntity1, simpleEntity2, true);
    }
    
    public static boolean equals(SimpleEntity simpleEntity1,
            SimpleEntity simpleEntity2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(simpleEntity1);
        Assert.assertNotNull(simpleEntity2);
        if (simpleEntity1!=null && simpleEntity2 !=null){
            Assert.assertEquals(simpleEntity1.getId(), simpleEntity2.getId());
        }

        return ret;
    }
}

