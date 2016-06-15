/**************************************************************************
 * HiddenEntitySQLiteAdapter.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.data;

import com.jeanlui.demactor.data.base.HiddenEntitySQLiteAdapterBase;


/**
 * HiddenEntity adapter database class. 
 * This class will help you access your database to do any basic operation you
 * need. 
 * Feel free to modify it, override, add more methods etc.
 */
public class HiddenEntitySQLiteAdapter extends HiddenEntitySQLiteAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public HiddenEntitySQLiteAdapter(final android.content.Context ctx) {
        super(ctx);
    }
}
