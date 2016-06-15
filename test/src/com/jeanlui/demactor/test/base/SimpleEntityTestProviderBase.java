/**************************************************************************
 * SimpleEntityTestProviderBase.java, demactor Android
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

import com.jeanlui.demactor.provider.SimpleEntityProviderAdapter;
import com.jeanlui.demactor.provider.utils.SimpleEntityProviderUtils;
import com.jeanlui.demactor.provider.contract.SimpleEntityContract;

import com.jeanlui.demactor.data.SimpleEntitySQLiteAdapter;

import com.jeanlui.demactor.entity.SimpleEntity;


import java.util.ArrayList;
import com.jeanlui.demactor.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** SimpleEntity database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit SimpleEntityTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class SimpleEntityTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected SimpleEntitySQLiteAdapter adapter;

    protected SimpleEntity entity;
    protected ContentResolver provider;
    protected SimpleEntityProviderUtils providerUtils;

    protected ArrayList<SimpleEntity> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new SimpleEntitySQLiteAdapter(this.ctx);

        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new SimpleEntityProviderUtils(this.getContext());
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
            SimpleEntity simpleEntity = SimpleEntityUtils.generateRandom(this.ctx);

            try {
                ContentValues values = SimpleEntityContract.itemToContentValues(simpleEntity);
                values.remove(SimpleEntityContract.COL_ID);
                result = this.provider.insert(SimpleEntityProviderAdapter.SIMPLEENTITY_URI, values);

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
        SimpleEntity result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        SimpleEntityProviderAdapter.SIMPLEENTITY_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = SimpleEntityContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            SimpleEntityUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<SimpleEntity> result = null;
        try {
            android.database.Cursor c = this.provider.query(SimpleEntityProviderAdapter.SIMPLEENTITY_URI, this.adapter.getCols(), null, null, null);
            result = SimpleEntityContract.cursorToItems(c);
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
            SimpleEntity simpleEntity = SimpleEntityUtils.generateRandom(this.ctx);

            try {
                simpleEntity.setId(this.entity.getId());

                ContentValues values = SimpleEntityContract.itemToContentValues(simpleEntity);
                result = this.provider.update(
                    Uri.parse(SimpleEntityProviderAdapter.SIMPLEENTITY_URI
                        + "/"
                        + simpleEntity.getId()),
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
                SimpleEntity simpleEntity = SimpleEntityUtils.generateRandom(this.ctx);
    
                try {
                    ContentValues values = SimpleEntityContract.itemToContentValues(simpleEntity);
                    values.remove(SimpleEntityContract.COL_ID);
    
                    result = this.provider.update(SimpleEntityProviderAdapter.SIMPLEENTITY_URI, values, null, null);
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
                        Uri.parse(SimpleEntityProviderAdapter.SIMPLEENTITY_URI
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
                    result = this.provider.delete(SimpleEntityProviderAdapter.SIMPLEENTITY_URI, null, null);
    
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
        SimpleEntity result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            SimpleEntityUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<SimpleEntity> result = null;
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
            SimpleEntity simpleEntity = SimpleEntityUtils.generateRandom(this.ctx);

            simpleEntity.setId(this.entity.getId());
            result = this.providerUtils.update(simpleEntity);

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
