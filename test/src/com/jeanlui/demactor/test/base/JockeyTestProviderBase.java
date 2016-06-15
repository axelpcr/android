/**************************************************************************
 * JockeyTestProviderBase.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 4, 2016
 *
 **************************************************************************/
package com.tactfactory.demact.test.base;

import android.test.suitebuilder.annotation.SmallTest;

import com.jeanlui.demactor.provider.JockeyProviderAdapter;
import com.jeanlui.demactor.provider.utils.JockeyProviderUtils;
import com.jeanlui.demactor.provider.contract.JockeyContract;

import com.tactfactory.demact.data.JockeySQLiteAdapter;

import com.tactfactory.demact.entity.Jockey;
import com.jeanlui.demactor.entity.Poney;


import java.util.ArrayList;
import com.tactfactory.demact.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** Jockey database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit JockeyTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class JockeyTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected JockeySQLiteAdapter adapter;

    protected Jockey entity;
    protected ContentResolver provider;
    protected JockeyProviderUtils providerUtils;

    protected ArrayList<Jockey> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new JockeySQLiteAdapter(this.ctx);

        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new JockeyProviderUtils(this.getContext());
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
            Jockey jockey = JockeyUtils.generateRandom(this.ctx);

            try {
                ContentValues values = JockeyContract.itemToContentValues(jockey);
                values.remove(JockeyContract.COL_FBGDFBDF);
                result = this.provider.insert(JockeyProviderAdapter.JOCKEY_URI, values);

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
        Jockey result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        JockeyProviderAdapter.JOCKEY_URI
                                + "/" 
                                + this.entity.getFbgDFbdf()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = JockeyContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            JockeyUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<Jockey> result = null;
        try {
            android.database.Cursor c = this.provider.query(JockeyProviderAdapter.JOCKEY_URI, this.adapter.getCols(), null, null, null);
            result = JockeyContract.cursorToItems(c);
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
            Jockey jockey = JockeyUtils.generateRandom(this.ctx);

            try {
                jockey.setFbgDFbdf(this.entity.getFbgDFbdf());
                if (this.entity.getDzerzerBCze() != null) {
                    jockey.getDzerzerBCze().addAll(this.entity.getDzerzerBCze());
                }

                ContentValues values = JockeyContract.itemToContentValues(jockey);
                result = this.provider.update(
                    Uri.parse(JockeyProviderAdapter.JOCKEY_URI
                        + "/"
                        + jockey.getFbgDFbdf()),
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
                Jockey jockey = JockeyUtils.generateRandom(this.ctx);
    
                try {
                    ContentValues values = JockeyContract.itemToContentValues(jockey);
                    values.remove(JockeyContract.COL_FBGDFBDF);
    
                    result = this.provider.update(JockeyProviderAdapter.JOCKEY_URI, values, null, null);
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
                        Uri.parse(JockeyProviderAdapter.JOCKEY_URI
                            + "/" 
                            + this.entity.getFbgDFbdf()),
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
                    result = this.provider.delete(JockeyProviderAdapter.JOCKEY_URI, null, null);
    
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
        Jockey result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            JockeyUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<Jockey> result = null;
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
            Jockey jockey = JockeyUtils.generateRandom(this.ctx);

            jockey.setFbgDFbdf(this.entity.getFbgDFbdf());
            if (this.entity.getDzerzerBCze() != null) {
                for (Poney dzerzerBCze : this.entity.getDzerzerBCze()) {
                    boolean found = false;
                    for (Poney dzerzerBCze2 : jockey.getDzerzerBCze()) {
                        if (dzerzerBCze.getIdlioEm1() == dzerzerBCze2.getIdlioEm1() ) {
                            found = true;
                            break;
                        }
                    }                    
                    if(!found) {
                        jockey.getDzerzerBCze().add(dzerzerBCze);
                    }
                }
            }
            result = this.providerUtils.update(jockey);

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
