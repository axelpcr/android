/**************************************************************************
 * PosttoGroupContractBase.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.provider.contract.base;

import android.content.ContentValues;


import java.util.ArrayList;

import com.jeanlui.demactor.entity.Post;
import com.jeanlui.demactor.entity.Group;



import com.jeanlui.demactor.provider.contract.PosttoGroupContract;

/** Demactor contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class PosttoGroupContractBase {


        /** PostInternalId_id. */
    public static final String COL_POSTINTERNALID_ID =
            "PostInternalId_id";
    /** Alias. */
    public static final String ALIASED_COL_POSTINTERNALID_ID =
            PosttoGroupContract.TABLE_NAME + "." + COL_POSTINTERNALID_ID;

    /** groups_id. */
    public static final String COL_GROUPS_ID =
            "groups_id";
    /** Alias. */
    public static final String ALIASED_COL_GROUPS_ID =
            PosttoGroupContract.TABLE_NAME + "." + COL_GROUPS_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "PosttoGroup";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "PosttoGroup";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        PosttoGroupContract.COL_POSTINTERNALID_ID,
        
        PosttoGroupContract.COL_GROUPS_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        PosttoGroupContract.ALIASED_COL_POSTINTERNALID_ID,
        
        PosttoGroupContract.ALIASED_COL_GROUPS_ID
    };

}
