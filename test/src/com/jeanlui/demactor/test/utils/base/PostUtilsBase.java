/**************************************************************************
 * PostUtilsBase.java, demactor Android
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
import com.jeanlui.demactor.entity.Post;



import com.jeanlui.demactor.test.utils.TestUtils;

import com.jeanlui.demactor.test.utils.UserUtils;
import com.jeanlui.demactor.entity.Comment;
import com.jeanlui.demactor.test.utils.CommentUtils;
import com.jeanlui.demactor.entity.Group;
import com.jeanlui.demactor.test.utils.GroupUtils;

import java.util.ArrayList;

public abstract class PostUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Post generateRandom(android.content.Context ctx){
        Post post = new Post();

        post.setId(TestUtils.generateRandomInt(0,100) + 1);
        post.setTitle("title_"+TestUtils.generateRandomString(10));
        post.setContent("content_"+TestUtils.generateRandomString(10));
        post.setOwner(UserUtils.generateRandom(ctx));
        ArrayList<Comment> relatedCommentss = new ArrayList<Comment>();
        relatedCommentss.add(CommentUtils.generateRandom(ctx));
        post.setComments(relatedCommentss);
        ArrayList<Group> relatedGroupss = new ArrayList<Group>();
        relatedGroupss.add(GroupUtils.generateRandom(ctx));
        post.setGroups(relatedGroupss);
        post.setCreatedAt(TestUtils.generateRandomDateTime());
        post.setUpdatedAt(TestUtils.generateRandomDateTime());
        post.setExpiresAt(TestUtils.generateRandomDateTime());

        return post;
    }

    public static boolean equals(Post post1,
            Post post2){
        return equals(post1, post2, true);
    }
    
    public static boolean equals(Post post1,
            Post post2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(post1);
        Assert.assertNotNull(post2);
        if (post1!=null && post2 !=null){
            Assert.assertEquals(post1.getId(), post2.getId());
            Assert.assertEquals(post1.getTitle(), post2.getTitle());
            Assert.assertEquals(post1.getContent(), post2.getContent());
            if (post1.getOwner() != null
                    && post2.getOwner() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(post1.getOwner().getId(),
                            post2.getOwner().getId());
                }
            }
            if (post1.getComments() != null
                    && post2.getComments() != null) {
                Assert.assertEquals(post1.getComments().size(),
                    post2.getComments().size());
                if (checkRecursiveId) {
                    for (Comment comments1 : post1.getComments()) {
                        boolean found = false;
                        for (Comment comments2 : post2.getComments()) {
                            if (comments1.getId() == comments2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated comments (id = %s) in Post (id = %s)",
                                        comments1.getId(),
                                        post1.getId()),
                                found);
                    }
                }
            }
            if (post1.getGroups() != null
                    && post2.getGroups() != null) {
                Assert.assertEquals(post1.getGroups().size(),
                    post2.getGroups().size());
                if (checkRecursiveId) {
                    for (Group groups1 : post1.getGroups()) {
                        boolean found = false;
                        for (Group groups2 : post2.getGroups()) {
                            if (groups1.getId() == groups2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated groups (id = %s) in Post (id = %s)",
                                        groups1.getId(),
                                        post1.getId()),
                                found);
                    }
                }
            }
            Assert.assertEquals(post1.getCreatedAt(), post2.getCreatedAt());
            Assert.assertEquals(post1.getUpdatedAt(), post2.getUpdatedAt());
            Assert.assertEquals(post1.getExpiresAt(), post2.getExpiresAt());
        }

        return ret;
    }
}

