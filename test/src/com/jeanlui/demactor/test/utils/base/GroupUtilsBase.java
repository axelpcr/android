/**************************************************************************
 * GroupUtilsBase.java, demactor Android
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
import com.jeanlui.demactor.entity.Group;



import com.jeanlui.demactor.test.utils.TestUtils;
import com.jeanlui.demactor.entity.GroupToComment;
import com.jeanlui.demactor.test.utils.GroupToCommentUtils;

import java.util.ArrayList;

public abstract class GroupUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Group generateRandom(android.content.Context ctx){
        Group group = new Group();

        group.setId(TestUtils.generateRandomInt(0,100) + 1);
        group.setName("name_"+TestUtils.generateRandomString(10));
        ArrayList<GroupToComment> relatedCommentss = new ArrayList<GroupToComment>();
        relatedCommentss.add(GroupToCommentUtils.generateRandom(ctx));
        group.setComments(relatedCommentss);

        return group;
    }

    public static boolean equals(Group group1,
            Group group2){
        return equals(group1, group2, true);
    }
    
    public static boolean equals(Group group1,
            Group group2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(group1);
        Assert.assertNotNull(group2);
        if (group1!=null && group2 !=null){
            Assert.assertEquals(group1.getId(), group2.getId());
            Assert.assertEquals(group1.getName(), group2.getName());
            if (group1.getComments() != null
                    && group2.getComments() != null) {
                Assert.assertEquals(group1.getComments().size(),
                    group2.getComments().size());
                if (checkRecursiveId) {
                    for (GroupToComment comments1 : group1.getComments()) {
                        boolean found = false;
                        for (GroupToComment comments2 : group2.getComments()) {
                            if (comments1.getId() == comments2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated comments (id = %s) in Group (id = %s)",
                                        comments1.getId(),
                                        group1.getId()),
                                found);
                    }
                }
            }
        }

        return ret;
    }
}

