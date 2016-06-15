/**************************************************************************
 * PoneyProviderUtilsBase.java, demactor Android
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
import com.jeanlui.demactor.criterias.base.Criterion;
import com.jeanlui.demactor.criterias.base.Criterion.Type;
import com.jeanlui.demactor.criterias.base.value.ArrayValue;
import com.jeanlui.demactor.criterias.base.CriteriaExpression;
import com.jeanlui.demactor.criterias.base.CriteriaExpression.GroupType;
import com.jeanlui.demactor.data.JockeySQLiteAdapter;
import com.jeanlui.demactor.entity.Poney;
import com.jeanlui.demactor.entity.Jockey;
import com.jeanlui.demactor.entity.Score;

import com.jeanlui.demactor.provider.PoneyProviderAdapter;
import com.jeanlui.demactor.provider.PoneytoJockeyProviderAdapter;
import com.jeanlui.demactor.provider.JockeyProviderAdapter;
import com.jeanlui.demactor.provider.ScoreProviderAdapter;
import com.jeanlui.demactor.provider.DemactorProvider;
import com.jeanlui.demactor.provider.contract.PoneyContract;
import com.jeanlui.demactor.provider.contract.PoneytoJockeyContract;
import com.jeanlui.demactor.provider.contract.JockeyContract;
import com.jeanlui.demactor.provider.contract.ScoreContract;

/**
 * Poney Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class PoneyProviderUtilsBase
            extends ProviderUtils<Poney> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "PoneyProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public PoneyProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final Poney item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = PoneyContract.itemToContentValues(item);
        itemValues.remove(PoneyContract.COL_IDLIOEM1);

        operations.add(ContentProviderOperation.newInsert(
                PoneyProviderAdapter.PONEY_URI)
                        .withValues(itemValues)
                        .build());

        if (item.getJockgFhjeys1() != null && item.getJockgFhjeys1().size() > 0) {
            for (Jockey jockey : item.getJockgFhjeys1()) {
                ContentValues jockeyValues = new ContentValues();
                jockeyValues.put(
                        PoneytoJockeyContract.COL_JOCKGFHJEYS1_FBGDFBDF,
                        jockey.getFbgDFbdf());

                operations.add(ContentProviderOperation.newInsert(
                    PoneytoJockeyProviderAdapter.PONEYTOJOCKEY_URI)
                        .withValues(jockeyValues)
                        .withValueBackReference(
                                PoneytoJockeyContract.COL_JOCKEYDF1_IDLIOEM1,
                                0)
                        .build());

            }
        }

        try {
            ContentProviderResult[] results =
                    prov.applyBatch(DemactorProvider.authority, operations);
            if (results[0] != null) {
                result = results[0].uri;
                item.setIdlioEm1(Integer.parseInt(result.getPathSegments().get(1)));
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
     * @param item Poney
     * @return number of row affected
     */
    public int delete(final Poney item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = PoneyProviderAdapter.PONEY_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getIdlioEm1()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return Poney
     */
    public Poney query(final Poney item) {
        return this.query(item.getIdlioEm1());
    }

    /**
     * Query the DB.
     *
     * @param idlioEm1 idlioEm1
     *
     * @return Poney
     */
    public Poney query(final int idlioEm1) {
        Poney result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(PoneyContract.ALIASED_COL_IDLIOEM1,
                    String.valueOf(idlioEm1));

        android.database.Cursor cursor = prov.query(
            PoneyProviderAdapter.PONEY_URI,
            PoneyContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = PoneyContract.cursorToItem(cursor);

            result.setJockgFhjeys1(
                this.getAssociateJockgFhjeys1(result));
            if (result.getScorvbnBe1() != null) {
                result.setScorvbnBe1(
                    this.getAssociateScorvbnBe1(result));
            }
        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<Poney>
     */
    public ArrayList<Poney> queryAll() {
        ArrayList<Poney> result =
                    new ArrayList<Poney>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PoneyProviderAdapter.PONEY_URI,
                PoneyContract.ALIASED_COLS,
                null,
                null,
                null);

        result = PoneyContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<Poney>
     */
    public ArrayList<Poney> query(CriteriaExpression expression) {
        ArrayList<Poney> result =
                    new ArrayList<Poney>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PoneyProviderAdapter.PONEY_URI,
                PoneyContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = PoneyContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item Poney
     
     * @return number of rows updated
     */
    public int update(final Poney item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = PoneyContract.itemToContentValues(
                item);

        Uri uri = PoneyProviderAdapter.PONEY_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getIdlioEm1()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());


        operations.add(ContentProviderOperation.newDelete(PoneytoJockeyProviderAdapter.PONEYTOJOCKEY_URI)
                .withSelection(PoneytoJockeyContract.COL_JOCKEYDF1_IDLIOEM1 + "= ?",
                                new String[]{String.valueOf(item.getIdlioEm1())})
                .build());

        for (Jockey jockey : item.getJockgFhjeys1()) {
            ContentValues jockeyValues = new ContentValues();
            jockeyValues.put(PoneytoJockeyContract.COL_JOCKGFHJEYS1_FBGDFBDF,
                    jockey.getFbgDFbdf());
            jockeyValues.put(PoneytoJockeyContract.COL_JOCKEYDF1_IDLIOEM1,
                    item.getIdlioEm1());

            operations.add(ContentProviderOperation.newInsert(PoneytoJockeyProviderAdapter.PONEYTOJOCKEY_URI)
                    .withValues(jockeyValues)
                    .build());
        }

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
     * Get associate JockgFhjeys1.
     * @param item Poney
     * @return Jockey
     */
    public ArrayList<Jockey> getAssociateJockgFhjeys1(
            final Poney item) {
        ArrayList<Jockey> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor poneytoJockeyCursor = prov.query(
                PoneytoJockeyProviderAdapter.PONEYTOJOCKEY_URI,
                PoneytoJockeyContract.ALIASED_COLS,
                PoneytoJockeyContract.ALIASED_COL_JOCKEYDF1_IDLIOEM1 
                        + "= ?",
                new String[]{String.valueOf(item.getIdlioEm1())},
                null);

        if (poneytoJockeyCursor.getCount() > 0) {
            CriteriaExpression jockeyCrits =
                    new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            ArrayValue arrayValue = new ArrayValue();
            inCrit.setKey(JockeyContract.ALIASED_COL_FBGDFBDF);
            inCrit.setType(Type.IN);
            inCrit.addValue(arrayValue);
            jockeyCrits.add(inCrit);

            while (poneytoJockeyCursor.moveToNext()) {
                int index = poneytoJockeyCursor.getColumnIndex(
                        PoneytoJockeyContract.COL_JOCKGFHJEYS1_FBGDFBDF);
                String jockeyFbgDFbdf = poneytoJockeyCursor.getString(index);

                arrayValue.addValue(jockeyFbgDFbdf);
            }
            poneytoJockeyCursor.close();
            android.database.Cursor jockeyCursor = prov.query(
                    JockeyProviderAdapter.JOCKEY_URI,
                    JockeyContract.ALIASED_COLS,
                    jockeyCrits.toSQLiteSelection(),
                    jockeyCrits.toSQLiteSelectionArgs(),
                    null);

            result = JockeyContract.cursorToItems(jockeyCursor);
            jockeyCursor.close();
        } else {
            result = new ArrayList<Jockey>();
        }

        return result;
    }

    /**
     * Get associate ScorvbnBe1.
     * @param item Poney
     * @return Score
     */
    public Score getAssociateScorvbnBe1(
            final Poney item) {
        Score result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor scoreCursor = prov.query(
                ScoreProviderAdapter.SCORE_URI,
                ScoreContract.ALIASED_COLS,
                ScoreContract.ALIASED_COL_IDFD1 + "= ?",
                new String[]{String.valueOf(item.getScorvbnBe1().getIdFD1())},
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

}
