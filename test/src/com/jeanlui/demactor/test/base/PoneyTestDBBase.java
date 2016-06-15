/**************************************************************************
 * PoneyTestDBBase.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 4, 2016
 *
 **************************************************************************/
package com.tactfactory.demact.test.base;

import java.util.ArrayList;

import android.test.suitebuilder.annotation.SmallTest;

import com.tactfactory.demact.data.PoneySQLiteAdapter;
import com.tactfactory.demact.entity.Poney;


import com.tactfactory.demact.test.utils.*;

import junit.framework.Assert;

/** Poney database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit PoneyTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class PoneyTestDBBase extends TestDBBase {
    protected android.content.Context ctx;

    protected PoneySQLiteAdapter adapter;

    protected Poney entity;
    protected ArrayList<Poney> entities;
    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new PoneySQLiteAdapter(this.ctx);
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
            Poney poney = PoneyUtils.generateRandom(this.ctx);

            result = (int)this.adapter.insert(poney);

            Assert.assertTrue(result >= 0);
        }
    }

    /** Test case Read Entity */
    @SmallTest
    public void testRead() {
        Poney result = null;
        if (this.entity != null) {
            result = this.adapter.getByID(this.entity.getIdlioEm1());

            PoneyUtils.equals(this.entity, result);
        }
    }

    /** Test case Update Entity */
    @SmallTest
    public void testUpdate() {
        int result = -1;
        if (this.entity != null) {
            Poney poney = PoneyUtils.generateRandom(this.ctx);
            poney.setIdlioEm1(this.entity.getIdlioEm1());

            result = (int) this.adapter.update(poney);

            Assert.assertTrue(result >= 0);
        }
    }

    /** Test case Update Entity */
    @SmallTest
    public void testDelete() {
        int result = -1;
        if (this.entity != null) {
            result = (int) this.adapter.remove(this.entity.getIdlioEm1());
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
