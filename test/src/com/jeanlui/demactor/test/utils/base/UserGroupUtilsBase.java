/**************************************************************************
 * UserGroupUtilsBase.java, demactor Android
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
import com.jeanlui.demactor.entity.UserGroup;



import com.jeanlui.demactor.test.utils.TestUtils;


public abstract class UserGroupUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static UserGroup generateRandom(android.content.Context ctx){
        UserGroup userGroup = new UserGroup();

        userGroup.setId(TestUtils.generateRandomInt(0,100) + 1);
        userGroup.setName("name_"+TestUtils.generateRandomString(10));
        userGroup.setWritePermission(TestUtils.generateRandomBool());
        userGroup.setDeletePermission(TestUtils.generateRandomBool());

        return userGroup;
    }

    public static boolean equals(UserGroup userGroup1,
            UserGroup userGroup2){
        return equals(userGroup1, userGroup2, true);
    }
    
    public static boolean equals(UserGroup userGroup1,
            UserGroup userGroup2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(userGroup1);
        Assert.assertNotNull(userGroup2);
        if (userGroup1!=null && userGroup2 !=null){
            Assert.assertEquals(userGroup1.getId(), userGroup2.getId());
            Assert.assertEquals(userGroup1.getName(), userGroup2.getName());
            Assert.assertEquals(userGroup1.isWritePermission(), userGroup2.isWritePermission());
            Assert.assertEquals(userGroup1.isDeletePermission(), userGroup2.isDeletePermission());
        }

        return ret;
    }
}

