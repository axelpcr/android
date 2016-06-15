/**************************************************************************
 * GroupToCommentTestDBBase.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.test.base;

import java.util.ArrayList;

import android.test.suitebuilder.annotation.SmallTest;

import com.jeanlui.demactor.data.GroupToCommentSQLiteAdapter;
import com.jeanlui.demactor.entity.GroupToComment;


import com.jeanlui.demactor.test.utils.*;

import junit.framework.Assert;

/** GroupToComment database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit GroupToCommentTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class GroupToCommentTestDBBase extends TestDBBase {
    protected android.content.Context ctx;

    protected GroupToCommentSQLiteAdapter adapter;

    protected GroupToComment entity;
    protected ArrayList<GroupToComment> entities;
    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new GroupToCommentSQLiteAdapter(this.ctx);
        this.adapter.open();

    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        this.adapter.close();

        super.tearDown();
    }

    /** Test case Create Entity */
    @SmallTest
    public void testCreate() {
        int result = -1;
        if (this.entity != null) {
            GroupToComment groupToComment = GroupToCommentUtils.generateRandom(this.ctx);

            result = (int)this.adapter.insert(groupToComment);

            Assert.assertTrue(result >= 0);
        }
    }

    /** Test case Read Entity */
    @SmallTest
    public void testRead() {
        GroupToComment result = null;
        if (this.entity != null) {
            result = this.adapter.getByID(this.entity.getId());

            GroupToCommentUtils.equals(this.entity, result);
        }
    }

    /** Test case Update Entity */
    @SmallTest
    public void testUpdate() {
        int result = -1;
        if (this.entity != null) {
            GroupToComment groupToComment = GroupToCommentUtils.generateRandom(this.ctx);
            groupToComment.setId(this.entity.getId());

            result = (int) this.adapter.update(groupToComment);

            Assert.assertTrue(result >= 0);
        }
    }

    /** Test case Update Entity */
    @SmallTest
    public void testDelete() {
        int result = -1;
        if (this.entity != null) {
            result = (int) this.adapter.remove(this.entity.getId());
            Assert.assertTrue(result >= 0);
        }
    }
    
    /** Test the get all method. */
    @SmallTest
    public void testAll() {
        int result = this.adapter.getAll().size();
        int expectedSize = this.nbEntities;
        Assert.assertEquals(expectedSize, result);
    }
}
