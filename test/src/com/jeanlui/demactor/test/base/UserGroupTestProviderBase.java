/**************************************************************************
 * UserGroupTestProviderBase.java, demactor Android
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

import com.jeanlui.demactor.provider.UserGroupProviderAdapter;
import com.jeanlui.demactor.provider.utils.UserGroupProviderUtils;
import com.jeanlui.demactor.provider.contract.UserGroupContract;

import com.jeanlui.demactor.data.UserGroupSQLiteAdapter;

import com.jeanlui.demactor.entity.UserGroup;


import java.util.ArrayList;
import com.jeanlui.demactor.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** UserGroup database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit UserGroupTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class UserGroupTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected UserGroupSQLiteAdapter adapter;

    protected UserGroup entity;
    protected ContentResolver provider;
    protected UserGroupProviderUtils providerUtils;

    protected ArrayList<UserGroup> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new UserGroupSQLiteAdapter(this.ctx);

        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new UserGroupProviderUtils(this.getContext());
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
            UserGroup userGroup = UserGroupUtils.generateRandom(this.ctx);

            try {
                ContentValues values = UserGroupContract.itemToContentValues(userGroup);
                values.remove(UserGroupContract.COL_ID);
                result = this.provider.insert(UserGroupProviderAdapter.USERGROUP_URI, values);

            } catch (Exception e) {
                e.printStackTrace();
            }

            Assert.assertNotNull(result);
            Assert.assertTrue(Integer.parseInt(result.getPathSegments().get(1)) > 0);        
            
        }
    }

    /** Test case Read Entity */
    @SmallTest
    public void testRead() {
        UserGroup result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        UserGroupProviderAdapter.USERGROUP_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = UserGroupContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            UserGroupUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<UserGroup> result = null;
        try {
            android.database.Cursor c = this.provider.query(UserGroupProviderAdapter.USERGROUP_URI, this.adapter.getCols(), null, null, null);
            result = UserGroupContract.cursorToItems(c);
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
            UserGroup userGroup = UserGroupUtils.generateRandom(this.ctx);

            try {
                userGroup.setId(this.entity.getId());

                ContentValues values = UserGroupContract.itemToContentValues(userGroup);
                result = this.provider.update(
                    Uri.parse(UserGroupProviderAdapter.USERGROUP_URI
                        + "/"
                        + userGroup.getId()),
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
                UserGroup userGroup = UserGroupUtils.generateRandom(this.ctx);
    
                try {
                    ContentValues values = UserGroupContract.itemToContentValues(userGroup);
                    values.remove(UserGroupContract.COL_ID);
    
                    result = this.provider.update(UserGroupProviderAdapter.USERGROUP_URI, values, null, null);
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
                        Uri.parse(UserGroupProviderAdapter.USERGROUP_URI
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
                    result = this.provider.delete(UserGroupProviderAdapter.USERGROUP_URI, null, null);
    
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
        UserGroup result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            UserGroupUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<UserGroup> result = null;
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
            UserGroup userGroup = UserGroupUtils.generateRandom(this.ctx);

            userGroup.setId(this.entity.getId());
            result = this.providerUtils.update(userGroup);

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
