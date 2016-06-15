/**************************************************************************
 * ViewComponentTestProviderBase.java, demactor Android
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

import com.jeanlui.demactor.provider.ViewComponentProviderAdapter;
import com.jeanlui.demactor.provider.utils.ViewComponentProviderUtils;
import com.jeanlui.demactor.provider.contract.ViewComponentContract;

import com.jeanlui.demactor.data.ViewComponentSQLiteAdapter;

import com.jeanlui.demactor.entity.ViewComponent;


import java.util.ArrayList;
import com.jeanlui.demactor.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** ViewComponent database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit ViewComponentTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class ViewComponentTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected ViewComponentSQLiteAdapter adapter;

    protected ViewComponent entity;
    protected ContentResolver provider;
    protected ViewComponentProviderUtils providerUtils;

    protected ArrayList<ViewComponent> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new ViewComponentSQLiteAdapter(this.ctx);

        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new ViewComponentProviderUtils(this.getContext());
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
            ViewComponent viewComponent = ViewComponentUtils.generateRandom(this.ctx);

            try {
                ContentValues values = ViewComponentContract.itemToContentValues(viewComponent);
                values.remove(ViewComponentContract.COL_ID);
                result = this.provider.insert(ViewComponentProviderAdapter.VIEWCOMPONENT_URI, values);

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
        ViewComponent result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        ViewComponentProviderAdapter.VIEWCOMPONENT_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = ViewComponentContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            ViewComponentUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<ViewComponent> result = null;
        try {
            android.database.Cursor c = this.provider.query(ViewComponentProviderAdapter.VIEWCOMPONENT_URI, this.adapter.getCols(), null, null, null);
            result = ViewComponentContract.cursorToItems(c);
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
            ViewComponent viewComponent = ViewComponentUtils.generateRandom(this.ctx);

            try {
                viewComponent.setId(this.entity.getId());

                ContentValues values = ViewComponentContract.itemToContentValues(viewComponent);
                result = this.provider.update(
                    Uri.parse(ViewComponentProviderAdapter.VIEWCOMPONENT_URI
                        + "/"
                        + viewComponent.getId()),
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
                ViewComponent viewComponent = ViewComponentUtils.generateRandom(this.ctx);
    
                try {
                    ContentValues values = ViewComponentContract.itemToContentValues(viewComponent);
                    values.remove(ViewComponentContract.COL_ID);
                    values.remove(ViewComponentContract.COL_LOGIN);
                    values.remove(ViewComponentContract.COL_EMAIL);
    
                    result = this.provider.update(ViewComponentProviderAdapter.VIEWCOMPONENT_URI, values, null, null);
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
                        Uri.parse(ViewComponentProviderAdapter.VIEWCOMPONENT_URI
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
                    result = this.provider.delete(ViewComponentProviderAdapter.VIEWCOMPONENT_URI, null, null);
    
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
        ViewComponent result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            ViewComponentUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<ViewComponent> result = null;
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
            ViewComponent viewComponent = ViewComponentUtils.generateRandom(this.ctx);

            viewComponent.setId(this.entity.getId());
            result = this.providerUtils.update(viewComponent);

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
