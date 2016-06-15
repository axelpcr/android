/**************************************************************************
 * SimpleEntityProviderUtilsBase.java, demactor Android
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

import com.jeanlui.demactor.entity.SimpleEntity;

import com.jeanlui.demactor.provider.SimpleEntityProviderAdapter;
import com.jeanlui.demactor.provider.DemactorProvider;
import com.jeanlui.demactor.provider.contract.SimpleEntityContract;

/**
 * SimpleEntity Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class SimpleEntityProviderUtilsBase
            extends ProviderUtils<SimpleEntity> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "SimpleEntityProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public SimpleEntityProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final SimpleEntity item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = SimpleEntityContract.itemToContentValues(item);
        itemValues.remove(SimpleEntityContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                SimpleEntityProviderAdapter.SIMPLEENTITY_URI)
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
     * @param item SimpleEntity
     * @return number of row affected
     */
    public int delete(final SimpleEntity item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = SimpleEntityProviderAdapter.SIMPLEENTITY_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return SimpleEntity
     */
    public SimpleEntity query(final SimpleEntity item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return SimpleEntity
     */
    public SimpleEntity query(final int id) {
        SimpleEntity result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(SimpleEntityContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            SimpleEntityProviderAdapter.SIMPLEENTITY_URI,
            SimpleEntityContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = SimpleEntityContract.cursorToItem(cursor);

        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<SimpleEntity>
     */
    public ArrayList<SimpleEntity> queryAll() {
        ArrayList<SimpleEntity> result =
                    new ArrayList<SimpleEntity>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                SimpleEntityProviderAdapter.SIMPLEENTITY_URI,
                SimpleEntityContract.ALIASED_COLS,
                null,
                null,
                null);

        result = SimpleEntityContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<SimpleEntity>
     */
    public ArrayList<SimpleEntity> query(CriteriaExpression expression) {
        ArrayList<SimpleEntity> result =
                    new ArrayList<SimpleEntity>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                SimpleEntityProviderAdapter.SIMPLEENTITY_URI,
                SimpleEntityContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = SimpleEntityContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item SimpleEntity
     
     * @return number of rows updated
     */
    public int update(final SimpleEntity item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = SimpleEntityContract.itemToContentValues(
                item);

        Uri uri = SimpleEntityProviderAdapter.SIMPLEENTITY_URI;
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
