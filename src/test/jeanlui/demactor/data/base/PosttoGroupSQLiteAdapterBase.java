
/**************************************************************************
 * PosttoGroupSQLiteAdapterBase.java, demactor Android
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
import com.jeanlui.demactor.data.PosttoGroupSQLiteAdapter;
import com.jeanlui.demactor.data.PostSQLiteAdapter;
import com.jeanlui.demactor.data.GroupSQLiteAdapter;
import com.jeanlui.demactor.provider.contract.PosttoGroupContract;
import com.jeanlui.demactor.provider.contract.PostContract;
import com.jeanlui.demactor.provider.contract.GroupContract;
import com.jeanlui.demactor.entity.Post;
import com.jeanlui.demactor.entity.Group;


import com.jeanlui.demactor.DemactorApplication;

import com.jeanlui.demactor.criterias.base.CriteriaExpression;
import com.jeanlui.demactor.criterias.base.Criterion;
import com.jeanlui.demactor.criterias.base.Criterion.Type;
import com.jeanlui.demactor.criterias.base.CriteriaExpression.GroupType;
import com.jeanlui.demactor.criterias.base.value.SelectValue;


/** PosttoGroup adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit PosttoGroupAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class PosttoGroupSQLiteAdapterBase
                        extends SQLiteAdapter<Void> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PosttoGroupDBAdapter";


    /**
     * Get the table name used in DB for your PosttoGroup entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return PosttoGroupContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your PosttoGroup entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = PosttoGroupContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the PosttoGroup entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return PosttoGroupContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + PosttoGroupContract.TABLE_NAME    + " ("
        
         + PosttoGroupContract.COL_POSTINTERNALID_ID    + " INTEGER NOT NULL,"
         + PosttoGroupContract.COL_GROUPS_ID    + " INTEGER NOT NULL,"

        
         + "FOREIGN KEY(" + PosttoGroupContract.COL_POSTINTERNALID_ID + ") REFERENCES " 
             + PostContract.TABLE_NAME 
                + " (" + PostContract.COL_ID + "),"
         + "FOREIGN KEY(" + PosttoGroupContract.COL_GROUPS_ID + ") REFERENCES " 
             + GroupContract.TABLE_NAME 
                + " (" + GroupContract.COL_ID + ")"
        + ", PRIMARY KEY (" + PosttoGroupContract.COL_POSTINTERNALID_ID + "," + PosttoGroupContract.COL_GROUPS_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public PosttoGroupSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }


    /**
     * Query the DB to find a PosttoGroup entity.
     *
     * @param PostInternalId The PostInternalId of the entity to get from the DB
     * @param groups The groups of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final Post PostInternalId,
                final Group groups) {

        String selection = PosttoGroupContract.ALIASED_COL_POSTINTERNALID_ID + " = ?";
        selection += " AND ";
        selection += PosttoGroupContract.ALIASED_COL_GROUPS_ID + " = ?";
        

        String[] selectionArgs = new String[2];
        selectionArgs[0] = String.valueOf(PostInternalId.getId());
        selectionArgs[1] = String.valueOf(groups.getId());

        return this.query(
                PosttoGroupContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }



    /**
     * Insert a PosttoGroup entity into database.
     *
     * @param postinternalid postinternalid
     * @param groups groups
     * @return Id of the PosttoGroup entity
     */
    public long insert(final int postInternalIdId,
            final int groupsId) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + PosttoGroupContract.TABLE_NAME + ")");
        }

        ContentValues values = new ContentValues();
        values.put(PosttoGroupContract.COL_POSTINTERNALID_ID,
                postInternalIdId);
        values.put(PosttoGroupContract.COL_GROUPS_ID,
                groupsId);

        return this.mDatabase.insert(
                PosttoGroupContract.TABLE_NAME,
                null,
                values);
    }


    /**
     * Find & read PosttoGroup by PostInternalId.
     * @param postinternalid groups
     * @param orderBy Order by string (can be null)
     * @return ArrayList of Group matching postinternalid
     */
    public android.database.Cursor getByPostInternalId(
            final int postInternalIdId,
            final String[] projection,
            String selection,
            String[] selectionArgs,
            final String orderBy) {

        android.database.Cursor ret = null;
        CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
        crit.add(PosttoGroupContract.COL_POSTINTERNALID_ID,
                String.valueOf(postInternalIdId),
                Type.EQUALS);
        SelectValue value = new SelectValue();
        value.setRefKey(PosttoGroupContract.COL_GROUPS_ID);
        value.setRefTable(PosttoGroupContract.TABLE_NAME);
        value.setCriteria(crit);
        CriteriaExpression groupCrit = new CriteriaExpression(GroupType.AND);
        Criterion groupSelectCrit = new Criterion();
        groupSelectCrit.setKey(GroupContract.ALIASED_COL_ID);
        groupSelectCrit.setType(Type.IN);
        groupSelectCrit.addValue(value);
        groupCrit.add(groupSelectCrit);

        if (Strings.isNullOrEmpty(selection)) {
            selection = groupCrit.toSQLiteSelection();
            selectionArgs = groupCrit.toSQLiteSelectionArgs();
        } else {
            selection += " AND " + groupCrit.toSQLiteSelection();
            selectionArgs = ObjectArrays.concat(
                        groupCrit.toSQLiteSelectionArgs(),
                        selectionArgs,
                        String.class);
        }

        ret = this.mDatabase.query(GroupContract.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);
        return ret;
    }

    /**
     * Find & read PosttoGroup by groups.
     * @param groups postinternalid
     * @param orderBy Order by string (can be null)
     * @return ArrayList of Post matching groups
     */
    public android.database.Cursor getByGroups(
            final int groupsId,
            final String[] projection,
            String selection,
            String[] selectionArgs,
            final String orderBy) {

        android.database.Cursor ret = null;
        CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
        crit.add(PosttoGroupContract.COL_GROUPS_ID,
                String.valueOf(groupsId),
                Type.EQUALS);
        SelectValue value = new SelectValue();
        value.setRefKey(PosttoGroupContract.COL_POSTINTERNALID_ID);
        value.setRefTable(PosttoGroupContract.TABLE_NAME);
        value.setCriteria(crit);
        CriteriaExpression postCrit = new CriteriaExpression(GroupType.AND);
        Criterion postSelectCrit = new Criterion();
        postSelectCrit.setKey(PostContract.ALIASED_COL_ID);
        postSelectCrit.setType(Type.IN);
        postSelectCrit.addValue(value);
        postCrit.add(postSelectCrit);

        if (Strings.isNullOrEmpty(selection)) {
            selection = postCrit.toSQLiteSelection();
            selectionArgs = postCrit.toSQLiteSelectionArgs();
        } else {
            selection += " AND " + postCrit.toSQLiteSelection();
            selectionArgs = ObjectArrays.concat(
                        postCrit.toSQLiteSelectionArgs(),
                        selectionArgs,
                        String.class);
        }

        ret = this.mDatabase.query(PostContract.TABLE_NAME,
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

