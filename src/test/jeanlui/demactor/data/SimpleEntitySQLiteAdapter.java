/**************************************************************************
 * SimpleEntitySQLiteAdapter.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.data;

import com.jeanlui.demactor.data.base.SimpleEntitySQLiteAdapterBase;


/**
 * SimpleEntity adapter database class. 
 * This class will help you access your database to do any basic operation you
 * need. 
 * Feel free to modify it, override, add more methods etc.
 */
public class SimpleEntitySQLiteAdapter extends SimpleEntitySQLiteAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public SimpleEntitySQLiteAdapter(final android.content.Context ctx) {
        super(ctx);
    }
}
