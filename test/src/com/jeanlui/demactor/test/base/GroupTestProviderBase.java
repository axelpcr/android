/**************************************************************************
 * GroupTestProviderBase.java, demactor Android
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

import com.jeanlui.demactor.provider.GroupProviderAdapter;
import com.jeanlui.demactor.provider.utils.GroupProviderUtils;
import com.jeanlui.demactor.provider.contract.GroupContract;

import com.jeanlui.demactor.data.GroupSQLiteAdapter;

import com.jeanlui.demactor.entity.Group;
import com.jeanlui.demactor.entity.GroupToComment;


import java.util.ArrayList;
import com.jeanlui.demactor.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** Group database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit GroupTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class GroupTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected GroupSQLiteAdapter adapter;

    protected Group entity;
    protected ContentResolver provider;
    protected GroupProviderUtils providerUtils;

    protected ArrayList<Group> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new GroupSQLiteAdapter(this.ctx);

        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new GroupProviderUtils(this.getContext());
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
            Group group = GroupUtils.generateRandom(this.ctx);

            try {
                ContentValues values = GroupContract.itemToContentValues(group);
                values.remove(GroupContract.COL_ID);
                result = this.provider.insert(GroupProviderAdapter.GROUP_URI, values);

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
        Group result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        GroupProviderAdapter.GROUP_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = GroupContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            GroupUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<Group> result = null;
        try {
            android.database.Cursor c = this.provider.query(GroupProviderAdapter.GROUP_URI, this.adapter.getCols(), null, null, null);
            result = GroupContract.cursorToItems(c);
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
            Group group = GroupUtils.generateRandom(this.ctx);

            try {
                group.setId(this.entity.getId());
                if (this.entity.getComments() != null) {
                    group.getComments().addAll(this.entity.getComments());
                }

                ContentValues values = GroupContract.itemToContentValues(group);
                result = this.provider.update(
                    Uri.parse(GroupProviderAdapter.GROUP_URI
                        + "/"
                        + group.getId()),
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
                Group group = GroupUtils.generateRandom(this.ctx);
    
                try {
                    ContentValues values = GroupContract.itemToContentValues(group);
                    values.remove(GroupContract.COL_ID);
    
                    result = this.provider.update(GroupProviderAdapter.GROUP_URI, values, null, null);
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
                        Uri.parse(GroupProviderAdapter.GROUP_URI
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
                    result = this.provider.delete(GroupProviderAdapter.GROUP_URI, null, null);
    
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
        Group result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            GroupUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<Group> result = null;
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
            Group group = GroupUtils.generateRandom(this.ctx);

            group.setId(this.entity.getId());
            if (this.entity.getComments() != null) {
                for (GroupToComment comments : this.entity.getComments()) {
                    boolean found = false;
                    for (GroupToComment comments2 : group.getComments()) {
                        if (comments.getId() == comments2.getId() ) {
                            found = true;
                            break;
                        }
                    }                    
                    if(!found) {
                        group.getComments().add(comments);
                    }
                }
            }
            result = this.providerUtils.update(group);

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
