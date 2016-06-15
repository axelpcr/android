/**************************************************************************
 * HiddenEntityProviderUtilsBase.java, demactor Android
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

import com.jeanlui.demactor.entity.HiddenEntity;

import com.jeanlui.demactor.provider.HiddenEntityProviderAdapter;
import com.jeanlui.demactor.provider.DemactorProvider;
import com.jeanlui.demactor.provider.contract.HiddenEntityContract;

/**
 * HiddenEntity Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class HiddenEntityProviderUtilsBase
            extends ProviderUtils<HiddenEntity> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "HiddenEntityProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public HiddenEntityProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final HiddenEntity item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = HiddenEntityContract.itemToContentValues(item);
        itemValues.remove(HiddenEntityContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                HiddenEntityProviderAdapter.HIDDENENTITY_URI)
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
     * @param item HiddenEntity
     * @return number of row affected
     */
    public int delete(final HiddenEntity item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = HiddenEntityProviderAdapter.HIDDENENTITY_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return HiddenEntity
     */
    public HiddenEntity query(final HiddenEntity item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return HiddenEntity
     */
    public HiddenEntity query(final int id) {
        HiddenEntity result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(HiddenEntityContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            HiddenEntityProviderAdapter.HIDDENENTITY_URI,
            HiddenEntityContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = HiddenEntityContract.cursorToItem(cursor);

        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<HiddenEntity>
     */
    public ArrayList<HiddenEntity> queryAll() {
        ArrayList<HiddenEntity> result =
                    new ArrayList<HiddenEntity>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                HiddenEntityProviderAdapter.HIDDENENTITY_URI,
                HiddenEntityContract.ALIASED_COLS,
                null,
                null,
                null);

        result = HiddenEntityContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<HiddenEntity>
     */
    public ArrayList<HiddenEntity> query(CriteriaExpression expression) {
        ArrayList<HiddenEntity> result =
                    new ArrayList<HiddenEntity>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                HiddenEntityProviderAdapter.HIDDENENTITY_URI,
                HiddenEntityContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = HiddenEntityContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item HiddenEntity
     
     * @return number of rows updated
     */
    public int update(final HiddenEntity item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = HiddenEntityContract.itemToContentValues(
                item);

        Uri uri = HiddenEntityProviderAdapter.HIDDENENTITY_URI;
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
