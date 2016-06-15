/**************************************************************************
 * HiddenEntityTestProviderBase.java, demactor Android
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

import com.jeanlui.demactor.provider.HiddenEntityProviderAdapter;
import com.jeanlui.demactor.provider.utils.HiddenEntityProviderUtils;
import com.jeanlui.demactor.provider.contract.HiddenEntityContract;

import com.jeanlui.demactor.data.HiddenEntitySQLiteAdapter;

import com.jeanlui.demactor.entity.HiddenEntity;


import java.util.ArrayList;
import com.jeanlui.demactor.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** HiddenEntity database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit HiddenEntityTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class HiddenEntityTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected HiddenEntitySQLiteAdapter adapter;

    protected HiddenEntity entity;
    protected ContentResolver provider;
    protected HiddenEntityProviderUtils providerUtils;

    protected ArrayList<HiddenEntity> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new HiddenEntitySQLiteAdapter(this.ctx);

        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new HiddenEntityProviderUtils(this.getContext());
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
            HiddenEntity hiddenEntity = HiddenEntityUtils.generateRandom(this.ctx);

            try {
                ContentValues values = HiddenEntityContract.itemToContentValues(hiddenEntity);
                values.remove(HiddenEntityContract.COL_ID);
                result = this.provider.insert(HiddenEntityProviderAdapter.HIDDENENTITY_URI, values);

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
        HiddenEntity result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        HiddenEntityProviderAdapter.HIDDENENTITY_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = HiddenEntityContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            HiddenEntityUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<HiddenEntity> result = null;
        try {
            android.database.Cursor c = this.provider.query(HiddenEntityProviderAdapter.HIDDENENTITY_URI, this.adapter.getCols(), null, null, null);
            result = HiddenEntityContract.cursorToItems(c);
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
            HiddenEntity hiddenEntity = HiddenEntityUtils.generateRandom(this.ctx);

            try {
                hiddenEntity.setId(this.entity.getId());

                ContentValues values = HiddenEntityContract.itemToContentValues(hiddenEntity);
                result = this.provider.update(
                    Uri.parse(HiddenEntityProviderAdapter.HIDDENENTITY_URI
                        + "/"
                        + hiddenEntity.getId()),
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
                HiddenEntity hiddenEntity = HiddenEntityUtils.generateRandom(this.ctx);
    
                try {
                    ContentValues values = HiddenEntityContract.itemToContentValues(hiddenEntity);
                    values.remove(HiddenEntityContract.COL_ID);
    
                    result = this.provider.update(HiddenEntityProviderAdapter.HIDDENENTITY_URI, values, null, null);
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
                        Uri.parse(HiddenEntityProviderAdapter.HIDDENENTITY_URI
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
                    result = this.provider.delete(HiddenEntityProviderAdapter.HIDDENENTITY_URI, null, null);
    
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
        HiddenEntity result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            HiddenEntityUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<HiddenEntity> result = null;
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
            HiddenEntity hiddenEntity = HiddenEntityUtils.generateRandom(this.ctx);

            hiddenEntity.setId(this.entity.getId());
            result = this.providerUtils.update(hiddenEntity);

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
