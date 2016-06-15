/**************************************************************************
 * UsertoUserContractBase.java, demactor Android
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

import com.jeanlui.demactor.entity.User;



import com.jeanlui.demactor.provider.contract.UsertoUserContract;

/** Demactor contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class UsertoUserContractBase {


        /** UserInternalId_id. */
    public static final String COL_USERINTERNALID_ID =
            "UserInternalId_id";
    /** Alias. */
    public static final String ALIASED_COL_USERINTERNALID_ID =
            UsertoUserContract.TABLE_NAME + "." + COL_USERINTERNALID_ID;

    /** friends_id. */
    public static final String COL_FRIENDS_ID =
            "friends_id";
    /** Alias. */
    public static final String ALIASED_COL_FRIENDS_ID =
            UsertoUserContract.TABLE_NAME + "." + COL_FRIENDS_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "UsertoUser";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "UsertoUser";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        UsertoUserContract.COL_USERINTERNALID_ID,
        
        UsertoUserContract.COL_FRIENDS_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        UsertoUserContract.ALIASED_COL_USERINTERNALID_ID,
        
        UsertoUserContract.ALIASED_COL_FRIENDS_ID
    };

}
