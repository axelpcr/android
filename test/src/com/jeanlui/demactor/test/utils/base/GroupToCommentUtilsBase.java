/**************************************************************************
 * GroupToCommentUtilsBase.java, demactor Android
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
import com.jeanlui.demactor.entity.GroupToComment;



import com.jeanlui.demactor.test.utils.TestUtils;

import com.jeanlui.demactor.test.utils.GroupUtils;


public abstract class GroupToCommentUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static GroupToComment generateRandom(android.content.Context ctx){
        GroupToComment groupToComment = new GroupToComment();

        groupToComment.setId(TestUtils.generateRandomInt(0,100) + 1);
        groupToComment.setDisplayName("displayName_"+TestUtils.generateRandomString(10));
        groupToComment.setGroup(GroupUtils.generateRandom(ctx));

        return groupToComment;
    }

    public static boolean equals(GroupToComment groupToComment1,
            GroupToComment groupToComment2){
        return equals(groupToComment1, groupToComment2, true);
    }
    
    public static boolean equals(GroupToComment groupToComment1,
            GroupToComment groupToComment2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(groupToComment1);
        Assert.assertNotNull(groupToComment2);
        if (groupToComment1!=null && groupToComment2 !=null){
            Assert.assertEquals(groupToComment1.getId(), groupToComment2.getId());
            Assert.assertEquals(groupToComment1.getDisplayName(), groupToComment2.getDisplayName());
            if (groupToComment1.getGroup() != null
                    && groupToComment2.getGroup() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(groupToComment1.getGroup().getId(),
                            groupToComment2.getGroup().getId());
                }
            }
        }

        return ret;
    }
}

