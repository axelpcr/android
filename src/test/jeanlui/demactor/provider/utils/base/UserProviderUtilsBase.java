/**************************************************************************
 * JockeyProviderUtilsBase.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 4, 2016
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

import com.jeanlui.demactor.entity.Jockey;
import com.jeanlui.demactor.entity.Score;
import com.jeanlui.demactor.entity.Jockey;

import com.jeanlui.demactor.provider.JockeyProviderAdapter;
import com.jeanlui.demactor.provider.ScoreProviderAdapter;
import com.jeanlui.demactor.provider.JockeyProviderAdapter;
import com.jeanlui.demactor.provider.DemactorProvider;
import com.jeanlui.demactor.provider.contract.JockeyContract;
import com.jeanlui.demactor.provider.contract.ScoreContract;
import com.jeanlui.demactor.provider.contract.JockeyContract;

/**
 * Jockey Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class JockeyProviderUtilsBase
            extends ProviderUtils<Jockey> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "JockeyProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public JockeyProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final Jockey item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = JockeyContract.itemToContentValues(item);
        itemValues.remove(JockeyContract.COL_ID1HNY);

        operations.add(ContentProviderOperation.newInsert(
                JockeyProviderAdapter.Jockey_URI)
                        .withValues(itemValues)
                        .build());


        try {
            ContentProviderResult[] results =
                    prov.applyBatch(DemactorProvider.authority, operations);
            if (results[0] != null) {
                result = results[0].uri;
                item.setId1HNY(Integer.parseInt(result.getPathSegments().get(1)));
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
     * @param item Jockey
     * @return number of row affected
     */
    public int delete(final Jockey item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = JockeyProviderAdapter.Jockey_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId1HNY()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return Jockey
     */
    public Jockey query(final Jockey item) {
        return this.query(item.getId1HNY());
    }

    /**
     * Query the DB.
     *
     * @param id1HNY id1HNY
     *
     * @return Jockey
     */
    public Jockey query(final int id1HNY) {
        Jockey result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(JockeyContract.ALIASED_COL_ID1HNY,
                    String.valueOf(id1HNY));

        android.database.Cursor cursor = prov.query(
            JockeyProviderAdapter.Jockey_URI,
            JockeyContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = JockeyContract.cursorToItem(cursor);

            if (result.getScoFGHre1() != null) {
                result.setScoFGHre1(
                    this.getAssociateScoFGHre1(result));
            }
            if (result.getJocFGHkey1() != null) {
                result.setJocFGHkey1(
                    this.getAssociateJocFGHkey1(result));
            }
        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<Jockey>
     */
    public ArrayList<Jockey> queryAll() {
        ArrayList<Jockey> result =
                    new ArrayList<Jockey>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                JockeyProviderAdapter.Jockey_URI,
                JockeyContract.ALIASED_COLS,
                null,
                null,
                null);

        result = JockeyContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<Jockey>
     */
    public ArrayList<Jockey> query(CriteriaExpression expression) {
        ArrayList<Jockey> result =
                    new ArrayList<Jockey>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                JockeyProviderAdapter.Jockey_URI,
                JockeyContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = JockeyContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item Jockey
     
     * @return number of rows updated
     */
    public int update(final Jockey item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = JockeyContract.itemToContentValues(
                item);

        Uri uri = JockeyProviderAdapter.Jockey_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId1HNY()));


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

    /** Relations operations. */
    /**
     * Get associate ScoFGHre1.
     * @param item Jockey
     * @return Score
     */
    public Score getAssociateScoFGHre1(
            final Jockey item) {
        Score result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor scoreCursor = prov.query(
                ScoreProviderAdapter.SCORE_URI,
                ScoreContract.ALIASED_COLS,
                ScoreContract.ALIASED_COL_IDFD1 + "= ?",
                new String[]{String.valueOf(item.getScoFGHre1().getIdFD1())},
                null);

        if (scoreCursor.getCount() > 0) {
            scoreCursor.moveToFirst();
            result = ScoreContract.cursorToItem(scoreCursor);
        } else {
            result = null;
        }
        scoreCursor.close();

        return result;
    }

    /**
     * Get associate JocFGHkey1.
     * @param item Jockey
     * @return Jockey
     */
    public Jockey getAssociateJocFGHkey1(
            final Jockey item) {
        Jockey result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor jockeyCursor = prov.query(
                JockeyProviderAdapter.JOCKEY_URI,
                JockeyContract.ALIASED_COLS,
                JockeyContract.ALIASED_COL_FBGDFBDF + "= ?",
                new String[]{String.valueOf(item.getJocFGHkey1().getFbgDFbdf())},
                null);

        if (jockeyCursor.getCount() > 0) {
            jockeyCursor.moveToFirst();
            result = JockeyContract.cursorToItem(jockeyCursor);
        } else {
            result = null;
        }
        jockeyCursor.close();

        return result;
    }

}
