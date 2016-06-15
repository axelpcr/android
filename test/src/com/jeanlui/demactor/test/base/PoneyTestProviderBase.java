/**************************************************************************
 * PoneyTestProviderBase.java, demactor Android
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

import com.jeanlui.demactor.provider.PoneyProviderAdapter;
import com.jeanlui.demactor.provider.utils.PoneyProviderUtils;
import com.jeanlui.demactor.provider.contract.PoneyContract;

import com.tactfactory.demact.data.PoneySQLiteAdapter;

import com.tactfactory.demact.entity.Poney;
import com.jeanlui.demactor.entity.Jockey;


import java.util.ArrayList;
import com.tactfactory.demact.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** Poney database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit PoneyTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class PoneyTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected PoneySQLiteAdapter adapter;

    protected Poney entity;
    protected ContentResolver provider;
    protected PoneyProviderUtils providerUtils;

    protected ArrayList<Poney> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new PoneySQLiteAdapter(this.ctx);

        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new PoneyProviderUtils(this.getContext());
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
            Poney poney = PoneyUtils.generateRandom(this.ctx);

            try {
                ContentValues values = PoneyContract.itemToContentValues(poney);
                values.remove(PoneyContract.COL_IDLIOEM1);
                result = this.provider.insert(PoneyProviderAdapter.PONEY_URI, values);

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
        Poney result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        PoneyProviderAdapter.PONEY_URI
                                + "/" 
                                + this.entity.getIdlioEm1()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = PoneyContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            PoneyUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<Poney> result = null;
        try {
            android.database.Cursor c = this.provider.query(PoneyProviderAdapter.PONEY_URI, this.adapter.getCols(), null, null, null);
            result = PoneyContract.cursorToItems(c);
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
            Poney poney = PoneyUtils.generateRandom(this.ctx);

            try {
                poney.setIdlioEm1(this.entity.getIdlioEm1());
                if (this.entity.getJockgFhjeys1() != null) {
                    poney.getJockgFhjeys1().addAll(this.entity.getJockgFhjeys1());
                }

                ContentValues values = PoneyContract.itemToContentValues(poney);
                result = this.provider.update(
                    Uri.parse(PoneyProviderAdapter.PONEY_URI
                        + "/"
                        + poney.getIdlioEm1()),
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
                Poney poney = PoneyUtils.generateRandom(this.ctx);
    
                try {
                    ContentValues values = PoneyContract.itemToContentValues(poney);
                    values.remove(PoneyContract.COL_IDLIOEM1);
    
                    result = this.provider.update(PoneyProviderAdapter.PONEY_URI, values, null, null);
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
                        Uri.parse(PoneyProviderAdapter.PONEY_URI
                            + "/" 
                            + this.entity.getIdlioEm1()),
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
                    result = this.provider.delete(PoneyProviderAdapter.PONEY_URI, null, null);
    
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
        Poney result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            PoneyUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<Poney> result = null;
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
            Poney poney = PoneyUtils.generateRandom(this.ctx);

            poney.setIdlioEm1(this.entity.getIdlioEm1());
            if (this.entity.getJockgFhjeys1() != null) {
                for (Jockey jockgFhjeys1 : this.entity.getJockgFhjeys1()) {
                    boolean found = false;
                    for (Jockey jockgFhjeys12 : poney.getJockgFhjeys1()) {
                        if (jockgFhjeys1.getFbgDFbdf() == jockgFhjeys12.getFbgDFbdf() ) {
                            found = true;
                            break;
                        }
                    }                    
                    if(!found) {
                        poney.getJockgFhjeys1().add(jockgFhjeys1);
                    }
                }
            }
            result = this.providerUtils.update(poney);

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
