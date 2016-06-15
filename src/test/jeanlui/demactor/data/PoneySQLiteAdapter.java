/**************************************************************************
 * PoneySQLiteAdapter.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 4, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.data;

import com.jeanlui.demactor.data.base.PoneySQLiteAdapterBase;


/**
 * Poney adapter database class. 
 * This class will help you access your database to do any basic operation you
 * need. 
 * Feel free to modify it, override, add more methods etc.
 */
public class PoneySQLiteAdapter extends PoneySQLiteAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public PoneySQLiteAdapter(final android.content.Context ctx) {
        super(ctx);
    }
}
