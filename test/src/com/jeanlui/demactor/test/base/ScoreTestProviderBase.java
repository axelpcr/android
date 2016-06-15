/**************************************************************************
 * ScoreTestProviderBase.java, demactor Android
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

import com.jeanlui.demactor.provider.ScoreProviderAdapter;
import com.jeanlui.demactor.provider.utils.ScoreProviderUtils;
import com.jeanlui.demactor.provider.contract.ScoreContract;

import com.tactfactory.demact.data.ScoreSQLiteAdapter;

import com.tactfactory.demact.entity.Score;
import com.jeanlui.demactor.entity.Poney;
import com.jeanlui.demactor.entity.User;


import java.util.ArrayList;
import com.tactfactory.demact.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** Score database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit ScoreTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class ScoreTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected ScoreSQLiteAdapter adapter;

    protected Score entity;
    protected ContentResolver provider;
    protected ScoreProviderUtils providerUtils;

    protected ArrayList<Score> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new ScoreSQLiteAdapter(this.ctx);

        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new ScoreProviderUtils(this.getContext());
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
            Score score = ScoreUtils.generateRandom(this.ctx);

            try {
                ContentValues values = ScoreContract.itemToContentValues(score);
                values.remove(ScoreContract.COL_IDFD1);
                result = this.provider.insert(ScoreProviderAdapter.SCORE_URI, values);

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
        Score result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        ScoreProviderAdapter.SCORE_URI
                                + "/" 
                                + this.entity.getIdFD1()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = ScoreContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            ScoreUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<Score> result = null;
        try {
            android.database.Cursor c = this.provider.query(ScoreProviderAdapter.SCORE_URI, this.adapter.getCols(), null, null, null);
            result = ScoreContract.cursorToItems(c);
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
            Score score = ScoreUtils.generateRandom(this.ctx);

            try {
                score.setIdFD1(this.entity.getIdFD1());
                if (this.entity.getPonRTYeys1() != null) {
                    score.getPonRTYeys1().addAll(this.entity.getPonRTYeys1());
                }
                if (this.entity.getUseGHHNrs1() != null) {
                    score.getUseGHHNrs1().addAll(this.entity.getUseGHHNrs1());
                }

                ContentValues values = ScoreContract.itemToContentValues(score);
                result = this.provider.update(
                    Uri.parse(ScoreProviderAdapter.SCORE_URI
                        + "/"
                        + score.getIdFD1()),
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
                Score score = ScoreUtils.generateRandom(this.ctx);
    
                try {
                    ContentValues values = ScoreContract.itemToContentValues(score);
                    values.remove(ScoreContract.COL_IDFD1);
    
                    result = this.provider.update(ScoreProviderAdapter.SCORE_URI, values, null, null);
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
                        Uri.parse(ScoreProviderAdapter.SCORE_URI
                            + "/" 
                            + this.entity.getIdFD1()),
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
                    result = this.provider.delete(ScoreProviderAdapter.SCORE_URI, null, null);
    
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
        Score result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            ScoreUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<Score> result = null;
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
            Score score = ScoreUtils.generateRandom(this.ctx);

            score.setIdFD1(this.entity.getIdFD1());
            if (this.entity.getPonRTYeys1() != null) {
                for (Poney ponRTYeys1 : this.entity.getPonRTYeys1()) {
                    boolean found = false;
                    for (Poney ponRTYeys12 : score.getPonRTYeys1()) {
                        if (ponRTYeys1.getIdlioEm1() == ponRTYeys12.getIdlioEm1() ) {
                            found = true;
                            break;
                        }
                    }                    
                    if(!found) {
                        score.getPonRTYeys1().add(ponRTYeys1);
                    }
                }
            }
            if (this.entity.getUseGHHNrs1() != null) {
                for (User useGHHNrs1 : this.entity.getUseGHHNrs1()) {
                    boolean found = false;
                    for (User useGHHNrs12 : score.getUseGHHNrs1()) {
                        if (useGHHNrs1.getId1HNY() == useGHHNrs12.getId1HNY() ) {
                            found = true;
                            break;
                        }
                    }                    
                    if(!found) {
                        score.getUseGHHNrs1().add(useGHHNrs1);
                    }
                }
            }
            result = this.providerUtils.update(score);

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
