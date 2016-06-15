/**************************************************************************
 * GroupToCommentTestProviderBase.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.test.base;

import android.test.suitebuilder.annotation.SmallTest;

import com.jeanlui.demactor.provider.GroupToCommentProviderAdapter;
import com.jeanlui.demactor.provider.utils.GroupToCommentProviderUtils;
import com.jeanlui.demactor.provider.contract.GroupToCommentContract;

import com.jeanlui.demactor.data.GroupToCommentSQLiteAdapter;

import com.jeanlui.demactor.entity.GroupToComment;


import java.util.ArrayList;
import com.jeanlui.demactor.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** GroupToComment database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit GroupToCommentTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class GroupToCommentTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected GroupToCommentSQLiteAdapter adapter;

    protected GroupToComment entity;
    protected ContentResolver provider;
    protected GroupToCommentProviderUtils providerUtils;

    protected ArrayList<GroupToComment> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new GroupToCommentSQLiteAdapter(this.ctx);

        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new GroupToCommentProviderUtils(this.getContext());
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /********** Direct Provider calls. *******/

    /** Test case Create Entity */
    @SmallTest
    public void testCreate() {
        Uri result = null;
        if (this.entity != null) {
            GroupToComment groupToComment = GroupToCommentUtils.generateRandom(this.ctx);

            try {
                ContentValues values = GroupToCommentContract.itemToContentValues(groupToComment, 0);
                result = this.provider.insert(GroupToCommentProviderAdapter.GROUPTOCOMMENT_URI, values);

            } catch (Exception e) {
                e.printStackTrace();
            }

            Assert.assertNotNull(result);
            Assert.assertTrue(Integer.parseInt(result.getPathSegments().get(1)) == groupToComment.getId());
            
        }
    }

    /** Test case Read Entity */
    @SmallTest
    public void testRead() {
        GroupToComment result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        GroupToCommentProviderAdapter.GROUPTOCOMMENT_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = GroupToCommentContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            GroupToCommentUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<GroupToComment> result = null;
        try {
            android.database.Cursor c = this.provider.query(GroupToCommentProviderAdapter.GROUPTOCOMMENT_URI, this.adapter.getCols(), null, null, null);
            result = GroupToCommentContract.cursorToItems(c);
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(result);
        if (result != null) {
            Assert.assertEquals(result.size(), this.nbEntities);
        }
    }

    /** Test case Update Entity */
    @SmallTest
    public void testUpdate() {
        int result = -1;
        if (this.entity != null) {
            GroupToComment groupToComment = GroupToCommentUtils.generateRandom(this.ctx);

            try {
                groupToComment.setId(this.entity.getId());

                ContentValues values = GroupToCommentContract.itemToContentValues(groupToComment, 0);
                result = this.provider.update(
                    Uri.parse(GroupToCommentProviderAdapter.GROUPTOCOMMENT_URI
                        + "/"
                        + groupToComment.getId()),
                    values,
                    null,
                    null);

            } catch (Exception e) {
                e.printStackTrace();
            }

            Assert.assertTrue(result > 0);
        }
    }

    /** Test case UpdateAll Entity */
    @SmallTest
    public void testUpdateAll() {
        int result = -1;
        if (this.entities != null) {
            if (this.entities.size() > 0) {
                GroupToComment groupToComment = GroupToCommentUtils.generateRandom(this.ctx);
    
                try {
                    ContentValues values = GroupToCommentContract.itemToContentValues(groupToComment, 0);
                    values.remove(GroupToCommentContract.COL_ID);
    
                    result = this.provider.update(GroupToCommentProviderAdapter.GROUPTOCOMMENT_URI, values, null, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
    
                Assert.assertEquals(result, this.nbEntities);
            }
        }
    }

    /** Test case Delete Entity */
    @SmallTest
    public void testDelete() {
        int result = -1;
        if (this.entity != null) {
            try {
                result = this.provider.delete(
                        Uri.parse(GroupToCommentProviderAdapter.GROUPTOCOMMENT_URI
                            + "/" 
                            + this.entity.getId()),
                        null,
                        null);

            } catch (Exception e) {
                e.printStackTrace();
            }
            Assert.assertTrue(result >= 0);
        }

    }

    /** Test case DeleteAll Entity */
    @SmallTest
    public void testDeleteAll() {
        int result = -1;
        if (this.entities != null) {
            if (this.entities.size() > 0) {
    
                try {
                    result = this.provider.delete(GroupToCommentProviderAdapter.GROUPTOCOMMENT_URI, null, null);
    
                } catch (Exception e) {
                    e.printStackTrace();
                }
    
                Assert.assertEquals(result, this.nbEntities);
            }
        }
    }

    /****** Provider Utils calls ********/

    /** Test case Read Entity by provider utils. */
    @SmallTest
    public void testUtilsRead() {
        GroupToComment result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            GroupToCommentUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<GroupToComment> result = null;
        result = this.providerUtils.queryAll();

        Assert.assertNotNull(result);
        if (result != null) {
            Assert.assertEquals(result.size(), this.nbEntities);
        }
    }

    /** Test case Update Entity by provider utils. */
    @SmallTest
    public void testUtilsUpdate() {
        int result = -1;
        if (this.entity != null) {
            GroupToComment groupToComment = GroupToCommentUtils.generateRandom(this.ctx);

            groupToComment.setId(this.entity.getId());
            result = this.providerUtils.update(groupToComment);

            Assert.assertTrue(result > 0);
        }
    }


    /** Test case Delete Entity by provider utils. */
    @SmallTest
    public void testUtilsDelete() {
        int result = -1;
        if (this.entity != null) {
            result = this.providerUtils.delete(this.entity);
            Assert.assertTrue(result >= 0);
        }

    }
}
