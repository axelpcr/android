/**************************************************************************
 * ClientTestProviderBase.java, demactor Android
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

import com.jeanlui.demactor.provider.ClientProviderAdapter;
import com.jeanlui.demactor.provider.utils.ClientProviderUtils;
import com.jeanlui.demactor.provider.contract.ClientContract;
import com.jeanlui.demactor.provider.contract.UserContract;

import com.jeanlui.demactor.data.ClientSQLiteAdapter;
import com.jeanlui.demactor.data.UserSQLiteAdapter;
import com.jeanlui.demactor.entity.Client;
import com.jeanlui.demactor.entity.User;


import java.util.ArrayList;
import com.jeanlui.demactor.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** Client database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit ClientTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class ClientTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected ClientSQLiteAdapter adapter;

    protected Client entity;
    protected ContentResolver provider;
    protected ClientProviderUtils providerUtils;

    protected ArrayList<Client> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new ClientSQLiteAdapter(this.ctx);

        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new ClientProviderUtils(this.getContext());
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
            Client client = ClientUtils.generateRandom(this.ctx);

            try {
                ContentValues values = ClientContract.itemToContentValues(client);
                values.remove(UserContract.COL_ID);
                result = this.provider.insert(ClientProviderAdapter.CLIENT_URI, values);

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
        Client result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        ClientProviderAdapter.CLIENT_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = ClientContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            ClientUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<Client> result = null;
        try {
            android.database.Cursor c = this.provider.query(ClientProviderAdapter.CLIENT_URI, this.adapter.getCols(), null, null, null);
            result = ClientContract.cursorToItems(c);
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
            Client client = ClientUtils.generateRandom(this.ctx);

            try {
                client.setId(this.entity.getId());
                if (this.entity.getFriends() != null) {
                    client.getFriends().addAll(this.entity.getFriends());
                }

                ContentValues values = ClientContract.itemToContentValues(client);
                result = this.provider.update(
                    Uri.parse(ClientProviderAdapter.CLIENT_URI
                        + "/"
                        + client.getId()),
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
                Client client = ClientUtils.generateRandom(this.ctx);
    
                try {
                    ContentValues values = ClientContract.itemToContentValues(client);
                    values.remove(UserContract.COL_ID);
                    values.remove(UserContract.COL_LOGIN);
    
                    result = this.provider.update(ClientProviderAdapter.CLIENT_URI, values, null, null);
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
                        Uri.parse(ClientProviderAdapter.CLIENT_URI
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
                    result = this.provider.delete(ClientProviderAdapter.CLIENT_URI, null, null);
    
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
        Client result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            ClientUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<Client> result = null;
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
            Client client = ClientUtils.generateRandom(this.ctx);

            client.setId(this.entity.getId());
            if (this.entity.getFriends() != null) {
                for (User friends : this.entity.getFriends()) {
                    boolean found = false;
                    for (User friends2 : client.getFriends()) {
                        if (friends.getId() == friends2.getId() ) {
                            found = true;
                            break;
                        }
                    }                    
                    if(!found) {
                        client.getFriends().add(friends);
                    }
                }
            }
            result = this.providerUtils.update(client);

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
