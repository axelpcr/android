/**************************************************************************
 * UserGroupProviderUtilsBase.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.provider.utils.base;

import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;

import android.content.ContentResolver;
import android.content.ContentValues;

import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;


import com.jeanlui.demactor.provider.utils.ProviderUtils;
import com.jeanlui.demactor.criterias.base.CriteriaExpression;
import com.jeanlui.demactor.criterias.base.CriteriaExpression.GroupType;

import com.jeanlui.demactor.entity.UserGroup;

import com.jeanlui.demactor.provider.UserGroupProviderAdapter;
import com.jeanlui.demactor.provider.DemactorProvider;
import com.jeanlui.demactor.provider.contract.UserGroupContract;

/**
 * UserGroup Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class UserGroupProviderUtilsBase
            extends ProviderUtils<UserGroup> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "UserGroupProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public UserGroupProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final UserGroup item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = UserGroupContract.itemToContentValues(item);
        itemValues.remove(UserGroupContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                UserGroupProviderAdapter.USERGROUP_URI)
                        .withValues(itemValues)
                        .build());


        try {
            ContentProviderResult[] results =
                    prov.applyBatch(DemactorProvider.authority, operations);
            if (results[0] != null) {
                result = results[0].uri;
                item.setId(Integer.parseInt(result.getPathSegments().get(1)));
            }
        } catch (RemoteException e) {
            android.util.Log.e(TAG, e.getMessage());
        } catch (OperationApplicationException e) {
            android.util.Log.e(TAG, e.getMessage());
        }

        return result;
    }


    /**
     * Delete from DB.
     * @param item UserGroup
     * @return number of row affected
     */
    public int delete(final UserGroup item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = UserGroupProviderAdapter.USERGROUP_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return UserGroup
     */
    public UserGroup query(final UserGroup item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return UserGroup
     */
    public UserGroup query(final int id) {
        UserGroup result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(UserGroupContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            UserGroupProviderAdapter.USERGROUP_URI,
            UserGroupContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = UserGroupContract.cursorToItem(cursor);

        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<UserGroup>
     */
    public ArrayList<UserGroup> queryAll() {
        ArrayList<UserGroup> result =
                    new ArrayList<UserGroup>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                UserGroupProviderAdapter.USERGROUP_URI,
                UserGroupContract.ALIASED_COLS,
                null,
                null,
                null);

        result = UserGroupContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<UserGroup>
     */
    public ArrayList<UserGroup> query(CriteriaExpression expression) {
        ArrayList<UserGroup> result =
                    new ArrayList<UserGroup>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                UserGroupProviderAdapter.USERGROUP_URI,
                UserGroupContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = UserGroupContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item UserGroup
     
     * @return number of rows updated
     */
    public int update(final UserGroup item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = UserGroupContract.itemToContentValues(
                item);

        Uri uri = UserGroupProviderAdapter.USERGROUP_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());



        try {
            ContentProviderResult[] results = prov.applyBatch(DemactorProvider.authority, operations);
            result = results[0].count;
        } catch (RemoteException e) {
            android.util.Log.e(TAG, e.getMessage());
        } catch (OperationApplicationException e) {
            android.util.Log.e(TAG, e.getMessage());
        }

        return result;
    }

    
}
