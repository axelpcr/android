/**************************************************************************
 * CommentUtilsBase.java, demactor Android
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
import com.jeanlui.demactor.entity.Comment;



import com.jeanlui.demactor.test.utils.TestUtils;

import com.jeanlui.demactor.test.utils.UserUtils;

import com.jeanlui.demactor.test.utils.PostUtils;
import com.jeanlui.demactor.entity.GroupToComment;
import com.jeanlui.demactor.test.utils.GroupToCommentUtils;

import java.util.ArrayList;

public abstract class CommentUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Comment generateRandom(android.content.Context ctx){
        Comment comment = new Comment();

        comment.setId(TestUtils.generateRandomInt(0,100) + 1);
        comment.setContent("content_"+TestUtils.generateRandomString(10));
        comment.setOwner(UserUtils.generateRandom(ctx));
        comment.setPost(PostUtils.generateRandom(ctx));
        comment.setCreatedAt(TestUtils.generateRandomDateTime());
        comment.setValidate(TestUtils.generateRandomBool());
        ArrayList<GroupToComment> relatedGroupss = new ArrayList<GroupToComment>();
        relatedGroupss.add(GroupToCommentUtils.generateRandom(ctx));
        comment.setGroups(relatedGroupss);

        return comment;
    }

    public static boolean equals(Comment comment1,
            Comment comment2){
        return equals(comment1, comment2, true);
    }
    
    public static boolean equals(Comment comment1,
            Comment comment2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(comment1);
        Assert.assertNotNull(comment2);
        if (comment1!=null && comment2 !=null){
            Assert.assertEquals(comment1.getId(), comment2.getId());
            Assert.assertEquals(comment1.getContent(), comment2.getContent());
            if (comment1.getOwner() != null
                    && comment2.getOwner() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(comment1.getOwner().getId(),
                            comment2.getOwner().getId());
                }
            }
            if (comment1.getPost() != null
                    && comment2.getPost() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(comment1.getPost().getId(),
                            comment2.getPost().getId());
                }
            }
            Assert.assertEquals(comment1.getCreatedAt(), comment2.getCreatedAt());
            Assert.assertEquals(comment1.isValidate(), comment2.isValidate());
            if (comment1.getGroups() != null
                    && comment2.getGroups() != null) {
                Assert.assertEquals(comment1.getGroups().size(),
                    comment2.getGroups().size());
                if (checkRecursiveId) {
                    for (GroupToComment groups1 : comment1.getGroups()) {
                        boolean found = false;
                        for (GroupToComment groups2 : comment2.getGroups()) {
                            if (groups1.getId() == groups2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated groups (id = %s) in Comment (id = %s)",
                                        groups1.getId(),
                                        comment1.getId()),
                                found);
                    }
                }
            }
        }

        return ret;
    }
}

