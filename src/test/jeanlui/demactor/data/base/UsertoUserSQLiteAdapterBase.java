
/**************************************************************************
 * UsertoUserSQLiteAdapterBase.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.data.base;

import java.util.ArrayList;
import android.content.ContentValues;


import android.database.sqlite.SQLiteDatabase;


import com.google.common.base.Strings;
import com.google.common.collect.ObjectArrays;
import com.jeanlui.demactor.data.SQLiteAdapter;
import com.jeanlui.demactor.data.UsertoUserSQLiteAdapter;
import com.jeanlui.demactor.data.UserSQLiteAdapter;
import com.jeanlui.demactor.provider.contract.UsertoUserContract;
import com.jeanlui.demactor.provider.contract.UserContract;
import com.jeanlui.demactor.entity.User;


import com.jeanlui.demactor.DemactorApplication;

import com.jeanlui.demactor.criterias.base.CriteriaExpression;
import com.jeanlui.demactor.criterias.base.Criterion;
import com.jeanlui.demactor.criterias.base.Criterion.Type;
import com.jeanlui.demactor.criterias.base.CriteriaExpression.GroupType;
import com.jeanlui.demactor.criterias.base.value.SelectValue;


/** UsertoUser adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit UsertoUserAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class UsertoUserSQLiteAdapterBase
                        extends SQLiteAdapter<Void> {

    /** TAG for debug purpose. */
    protected static final String TAG = "UsertoUserDBAdapter";


    /**
     * Get the table name used in DB for your UsertoUser entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return UsertoUserContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your UsertoUser entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = UsertoUserContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the UsertoUser entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return UsertoUserContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + UsertoUserContract.TABLE_NAME    + " ("
        
         + UsertoUserContract.COL_USERINTERNALID_ID    + " INTEGER NOT NULL,"
         + UsertoUserContract.COL_FRIENDS_ID    + " INTEGER NOT NULL,"

        
         + "FOREIGN KEY(" + UsertoUserContract.COL_USERINTERNALID_ID + ") REFERENCES " 
             + UserContract.TABLE_NAME 
                + " (" + UserContract.COL_ID + "),"
         + "FOREIGN KEY(" + UsertoUserContract.COL_FRIENDS_ID + ") REFERENCES " 
             + UserContract.TABLE_NAME 
                + " (" + UserContract.COL_ID + ")"
        + ", PRIMARY KEY (" + UsertoUserContract.COL_USERINTERNALID_ID + "," + UsertoUserContract.COL_FRIENDS_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public UsertoUserSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }


    /**
     * Query the DB to find a UsertoUser entity.
     *
     * @param UserInternalId The UserInternalId of the entity to get from the DB
     * @param friends The friends of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final User UserInternalId,
                final User friends) {

        String selection = UsertoUserContract.ALIASED_COL_USERINTERNALID_ID + " = ?";
        selection += " AND ";
        selection += UsertoUserContract.ALIASED_COL_FRIENDS_ID + " = ?";
        

        String[] selectionArgs = new String[2];
        selectionArgs[0] = String.valueOf(UserInternalId.getId());
        selectionArgs[1] = String.valueOf(friends.getId());

        return this.query(
                UsertoUserContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }



    /**
     * Insert a UsertoUser entity into database.
     *
     * @param userinternalid userinternalid
     * @param friends friends
     * @return Id of the UsertoUser entity
     */
    public long insert(final int userInternalIdId,
            final int friendsId) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + UsertoUserContract.TABLE_NAME + ")");
        }

        ContentValues values = new ContentValues();
        values.put(UsertoUserContract.COL_USERINTERNALID_ID,
                userInternalIdId);
        values.put(UsertoUserContract.COL_FRIENDS_ID,
                friendsId);

        return this.mDatabase.insert(
                UsertoUserContract.TABLE_NAME,
                null,
                values);
    }


    /**
     * Find & read UsertoUser by UserInternalId.
     * @param userinternalid friends
     * @param orderBy Order by string (can be null)
     * @return ArrayList of User matching userinternalid
     */
    public android.database.Cursor getByUserInternalId(
            final int userInternalIdId,
            final String[] projection,
            String selection,
            String[] selectionArgs,
            final String orderBy) {

        android.database.Cursor ret = null;
        CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
        crit.add(UsertoUserContract.COL_USERINTERNALID_ID,
                String.valueOf(userInternalIdId),
                Type.EQUALS);
        SelectValue value = new SelectValue();
        value.setRefKey(UsertoUserContract.COL_FRIENDS_ID);
        value.setRefTable(UsertoUserContract.TABLE_NAME);
        value.setCriteria(crit);
        CriteriaExpression userCrit = new CriteriaExpression(GroupType.AND);
        Criterion userSelectCrit = new Criterion();
        userSelectCrit.setKey(UserContract.ALIASED_COL_ID);
        userSelectCrit.setType(Type.IN);
        userSelectCrit.addValue(value);
        userCrit.add(userSelectCrit);

        if (Strings.isNullOrEmpty(selection)) {
            selection = userCrit.toSQLiteSelection();
            selectionArgs = userCrit.toSQLiteSelectionArgs();
        } else {
            selection += " AND " + userCrit.toSQLiteSelection();
            selectionArgs = ObjectArrays.concat(
                        userCrit.toSQLiteSelectionArgs(),
                        selectionArgs,
                        String.class);
        }

        ret = this.mDatabase.query(UserContract.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);
        return ret;
    }

    /**
     * Find & read UsertoUser by friends.
     * @param friends userinternalid
     * @param orderBy Order by string (can be null)
     * @return ArrayList of User matching friends
     */
    public android.database.Cursor getByFriends(
            final int friendsId,
            final String[] projection,
            String selection,
            String[] selectionArgs,
            final String orderBy) {

        android.database.Cursor ret = null;
        CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
        crit.add(UsertoUserContract.COL_FRIENDS_ID,
                String.valueOf(friendsId),
                Type.EQUALS);
        SelectValue value = new SelectValue();
        value.setRefKey(UsertoUserContract.COL_USERINTERNALID_ID);
        value.setRefTable(UsertoUserContract.TABLE_NAME);
        value.setCriteria(crit);
        CriteriaExpression userCrit = new CriteriaExpression(GroupType.AND);
        Criterion userSelectCrit = new Criterion();
        userSelectCrit.setKey(UserContract.ALIASED_COL_ID);
        userSelectCrit.setType(Type.IN);
        userSelectCrit.addValue(value);
        userCrit.add(userSelectCrit);

        if (Strings.isNullOrEmpty(selection)) {
            selection = userCrit.toSQLiteSelection();
            selectionArgs = userCrit.toSQLiteSelectionArgs();
        } else {
            selection += " AND " + userCrit.toSQLiteSelection();
            selectionArgs = ObjectArrays.concat(
                        userCrit.toSQLiteSelectionArgs(),
                        selectionArgs,
                        String.class);
        }

        ret = this.mDatabase.query(UserContract.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);
        return ret;
    }


    @Override
    public Void cursorToItem(android.database.Cursor c) {
        return null;
    }

    @Override
    public ContentValues itemToContentValues(Void item) {
        return null;
    }

    @Override
    public long insert(Void item) {
        return -1;
    }

    @Override
    public int update(Void item) {
        return 0;
    }

    @Override
    public int delete(Void item) {
        return 0;
    }

}

