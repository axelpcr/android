/**************************************************************************
 * UserGroupTestDBBase.java, demactor Android
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

import com.jeanlui.demactor.data.UserGroupSQLiteAdapter;
import com.jeanlui.demactor.entity.UserGroup;


import com.jeanlui.demactor.test.utils.*;

import junit.framework.Assert;

/** UserGroup database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit UserGroupTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class UserGroupTestDBBase extends TestDBBase {
    protected android.content.Context ctx;

    protected UserGroupSQLiteAdapter adapter;

    protected UserGroup entity;
    protected ArrayList<UserGroup> entities;
    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new UserGroupSQLiteAdapter(this.ctx);
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
            UserGroup userGroup = UserGroupUtils.generateRandom(this.ctx);

            result = (int)this.adapter.insert(userGroup);

            Assert.assertTrue(result >= 0);
        }
    }

    /** Test case Read Entity */
    @SmallTest
    public void testRead() {
        UserGroup result = null;
        if (this.entity != null) {
            result = this.adapter.getByID(this.entity.getId());

            UserGroupUtils.equals(this.entity, result);
        }
    }

    /** Test case Update Entity */
    @SmallTest
    public void testUpdate() {
        int result = -1;
        if (this.entity != null) {
            UserGroup userGroup = UserGroupUtils.generateRandom(this.ctx);
            userGroup.setId(this.entity.getId());

            result = (int) this.adapter.update(userGroup);

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
