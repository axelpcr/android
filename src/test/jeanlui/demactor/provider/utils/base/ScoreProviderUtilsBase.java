/**************************************************************************
 * ScoreProviderUtilsBase.java, demactor Android
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

import com.jeanlui.demactor.entity.Score;
import com.jeanlui.demactor.entity.Poney;
import com.jeanlui.demactor.entity.User;

import com.jeanlui.demactor.provider.ScoreProviderAdapter;
import com.jeanlui.demactor.provider.PoneyProviderAdapter;
import com.jeanlui.demactor.provider.UserProviderAdapter;
import com.jeanlui.demactor.provider.DemactorProvider;
import com.jeanlui.demactor.provider.contract.ScoreContract;
import com.jeanlui.demactor.provider.contract.PoneyContract;
import com.jeanlui.demactor.provider.contract.UserContract;

/**
 * Score Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class ScoreProviderUtilsBase
            extends ProviderUtils<Score> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "ScoreProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public ScoreProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final Score item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = ScoreContract.itemToContentValues(item);
        itemValues.remove(ScoreContract.COL_IDFD1);

        operations.add(ContentProviderOperation.newInsert(
                ScoreProviderAdapter.SCORE_URI)
                        .withValues(itemValues)
                        .build());

        if (item.getPonRTYeys1() != null && item.getPonRTYeys1().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(PoneyContract.COL_IDLIOEM1);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getPonRTYeys1().size(); i++) {
                inValue.addValue(String.valueOf(item.getPonRTYeys1().get(i).getIdlioEm1()));
            }

            operations.add(ContentProviderOperation.newUpdate(PoneyProviderAdapter.PONEY_URI)
                    .withValueBackReference(
                            PoneyContract
                                    .COL_SCORVBNBE1_IDFD1,
                            0)
                    .withSelection(
                            crit.toSQLiteSelection(),
                            crit.toSQLiteSelectionArgs())
                    .build());
        }
        if (item.getUseGHHNrs1() != null && item.getUseGHHNrs1().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(UserContract.COL_ID1HNY);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getUseGHHNrs1().size(); i++) {
                inValue.addValue(String.valueOf(item.getUseGHHNrs1().get(i).getId1HNY()));
            }

            operations.add(ContentProviderOperation.newUpdate(UserProviderAdapter.USER_URI)
                    .withValueBackReference(
                            UserContract
                                    .COL_SCOFGHRE1_IDFD1,
                            0)
                    .withSelection(
                            crit.toSQLiteSelection(),
                            crit.toSQLiteSelectionArgs())
                    .build());
        }

        try {
            ContentProviderResult[] results =
                    prov.applyBatch(DemactorProvider.authority, operations);
            if (results[0] != null) {
                result = results[0].uri;
                item.setIdFD1(Integer.parseInt(result.getPathSegments().get(1)));
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
     * @param item Score
     * @return number of row affected
     */
    public int delete(final Score item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = ScoreProviderAdapter.SCORE_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getIdFD1()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return Score
     */
    public Score query(final Score item) {
        return this.query(item.getIdFD1());
    }

    /**
     * Query the DB.
     *
     * @param idFD1 idFD1
     *
     * @return Score
     */
    public Score query(final int idFD1) {
        Score result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(ScoreContract.ALIASED_COL_IDFD1,
                    String.valueOf(idFD1));

        android.database.Cursor cursor = prov.query(
            ScoreProviderAdapter.SCORE_URI,
            ScoreContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = ScoreContract.cursorToItem(cursor);

            result.setPonRTYeys1(
                this.getAssociatePonRTYeys1(result));
            result.setUseGHHNrs1(
                this.getAssociateUseGHHNrs1(result));
        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<Score>
     */
    public ArrayList<Score> queryAll() {
        ArrayList<Score> result =
                    new ArrayList<Score>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                ScoreProviderAdapter.SCORE_URI,
                ScoreContract.ALIASED_COLS,
                null,
                null,
                null);

        result = ScoreContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<Score>
     */
    public ArrayList<Score> query(CriteriaExpression expression) {
        ArrayList<Score> result =
                    new ArrayList<Score>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                ScoreProviderAdapter.SCORE_URI,
                ScoreContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = ScoreContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item Score
     
     * @return number of rows updated
     */
    public int update(final Score item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = ScoreContract.itemToContentValues(
                item);

        Uri uri = ScoreProviderAdapter.SCORE_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getIdFD1()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());


        if (item.getPonRTYeys1() != null && item.getPonRTYeys1().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new ponRTYeys1 for Score
            CriteriaExpression ponRTYeys1Crit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(PoneyContract.COL_IDLIOEM1);
            crit.addValue(values);
            ponRTYeys1Crit.add(crit);


            for (Poney ponRTYeys1 : item.getPonRTYeys1()) {
                values.addValue(
                    String.valueOf(ponRTYeys1.getIdlioEm1()));
            }
            selection = ponRTYeys1Crit.toSQLiteSelection();
            selectionArgs = ponRTYeys1Crit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    PoneyProviderAdapter.PONEY_URI)
                    .withValue(
                            PoneyContract.COL_SCORVBNBE1_IDFD1,
                            item.getIdFD1())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated ponRTYeys1
            crit.setType(Type.NOT_IN);
            ponRTYeys1Crit.add(PoneyContract.COL_SCORVBNBE1_IDFD1,
                    String.valueOf(item.getIdFD1()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    PoneyProviderAdapter.PONEY_URI)
                    .withValue(
                            PoneyContract.COL_SCORVBNBE1_IDFD1,
                            null)
                    .withSelection(
                            ponRTYeys1Crit.toSQLiteSelection(),
                            ponRTYeys1Crit.toSQLiteSelectionArgs())
                    .build());
        }

        if (item.getUseGHHNrs1() != null && item.getUseGHHNrs1().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new useGHHNrs1 for Score
            CriteriaExpression useGHHNrs1Crit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(UserContract.COL_ID1HNY);
            crit.addValue(values);
            useGHHNrs1Crit.add(crit);


            for (User useGHHNrs1 : item.getUseGHHNrs1()) {
                values.addValue(
                    String.valueOf(useGHHNrs1.getId1HNY()));
            }
            selection = useGHHNrs1Crit.toSQLiteSelection();
            selectionArgs = useGHHNrs1Crit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    UserProviderAdapter.USER_URI)
                    .withValue(
                            UserContract.COL_SCOFGHRE1_IDFD1,
                            item.getIdFD1())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated useGHHNrs1
            crit.setType(Type.NOT_IN);
            useGHHNrs1Crit.add(UserContract.COL_SCOFGHRE1_IDFD1,
                    String.valueOf(item.getIdFD1()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    UserProviderAdapter.USER_URI)
                    .withValue(
                            UserContract.COL_SCOFGHRE1_IDFD1,
                            null)
                    .withSelection(
                            useGHHNrs1Crit.toSQLiteSelection(),
                            useGHHNrs1Crit.toSQLiteSelectionArgs())
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
     * Get associate PonRTYeys1.
     * @param item Score
     * @return Poney
     */
    public ArrayList<Poney> getAssociatePonRTYeys1(
            final Score item) {
        ArrayList<Poney> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor poneyCursor = prov.query(
                PoneyProviderAdapter.PONEY_URI,
                PoneyContract.ALIASED_COLS,
                PoneyContract.ALIASED_COL_SCORVBNBE1_IDFD1
                        + "= ?",
                new String[]{String.valueOf(item.getIdFD1())},
                null);

        result = PoneyContract.cursorToItems(
                        poneyCursor);
        poneyCursor.close();

        return result;
    }

    /**
     * Get associate UseGHHNrs1.
     * @param item Score
     * @return User
     */
    public ArrayList<User> getAssociateUseGHHNrs1(
            final Score item) {
        ArrayList<User> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor userCursor = prov.query(
                UserProviderAdapter.USER_URI,
                UserContract.ALIASED_COLS,
                UserContract.ALIASED_COL_SCOFGHRE1_IDFD1
                        + "= ?",
                new String[]{String.valueOf(item.getIdFD1())},
                null);

        result = UserContract.cursorToItems(
                        userCursor);
        userCursor.close();

        return result;
    }

}
