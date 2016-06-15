/**************************************************************************
 * DemactorSQLiteOpenHelper.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.data;

import com.jeanlui.demactor.data.base.DemactorSQLiteOpenHelperBase;

import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * This class makes it easy for ContentProvider implementations to defer <br />
 * opening and upgrading the database until first use, to avoid blocking <br />
 * application startup with long-running database upgrades.
 * @see android.database.sqlite.SQLiteOpenHelper
 */
public class DemactorSQLiteOpenHelper
                    extends DemactorSQLiteOpenHelperBase {

    /**
     * Constructor.
     * @param ctx context
     * @param name name
     * @param factory factory
     * @param version version
     */
    public DemactorSQLiteOpenHelper(final android.content.Context ctx,
           final String name, final CursorFactory factory, final int version) {
        super(ctx, name, factory, version);
    }

}
