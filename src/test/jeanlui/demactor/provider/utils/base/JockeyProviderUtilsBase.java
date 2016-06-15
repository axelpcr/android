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
import com.jeanlui.demactor.criterias.base.Criterion;
import com.jeanlui.demactor.criterias.base.Criterion.Type;
import com.jeanlui.demactor.criterias.base.value.ArrayValue;
import com.jeanlui.demactor.criterias.base.CriteriaExpression;
import com.jeanlui.demactor.criterias.base.CriteriaExpression.GroupType;
import com.jeanlui.demactor.data.PoneySQLiteAdapter;
import com.jeanlui.demactor.entity.Jockey;
import com.jeanlui.demactor.entity.Poney;
import com.jeanlui.demactor.entity.User;

import com.jeanlui.demactor.provider.JockeyProviderAdapter;
import com.jeanlui.demactor.provider.PoneytoJockeyProviderAdapter;
import com.jeanlui.demactor.provider.PoneyProviderAdapter;
import com.jeanlui.demactor.provider.UserProviderAdapter;
import com.jeanlui.demactor.provider.DemactorProvider;
import com.jeanlui.demactor.provider.contract.JockeyContract;
import com.jeanlui.demactor.provider.contract.PoneytoJockeyContract;
import com.jeanlui.demactor.provider.contract.PoneyContract;
import com.jeanlui.demactor.provider.contract.UserContract;

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
        itemValues.remove(JockeyContract.COL_FBGDFBDF);

        operations.add(ContentProviderOperation.newInsert(
                JockeyProviderAdapter.JOCKEY_URI)
                        .withValues(itemValues)
                        .build());

        if (item.getDzerzerBCze() != null && item.getDzerzerBCze().size() > 0) {
            for (Poney poney : item.getDzerzerBCze()) {
                ContentValues poneyValues = new ContentValues();
                poneyValues.put(
                        PoneytoJockeyContract.COL_DZERZERBCZE_IDLIOEM1,
                        poney.getIdlioEm1());

                operations.add(ContentProviderOperation.newInsert(
                    PoneytoJockeyProviderAdapter.PONEYTOJOCKEY_URI)
                        .withValues(poneyValues)
                        .withValueBackReference(
                                PoneytoJockeyContract.COL_PONEYDFD1_FBGDFBDF,
                                0)
                        .build());

            }
        }

        try {
            ContentProviderResult[] results =
                    prov.applyBatch(DemactorProvider.authority, operations);
            if (results[0] != null) {
                result = results[0].uri;
                item.setFbgDFbdf(Integer.parseInt(result.getPathSegments().get(1)));
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

        Uri uri = JockeyProviderAdapter.JOCKEY_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getFbgDFbdf()));

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
        return this.query(item.getFbgDFbdf());
    }

    /**
     * Query the DB.
     *
     * @param fbgDFbdf fbgDFbdf
     *
     * @return Jockey
     */
    public Jockey query(final int fbgDFbdf) {
        Jockey result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(JockeyContract.ALIASED_COL_FBGDFBDF,
                    String.valueOf(fbgDFbdf));

        android.database.Cursor cursor = prov.query(
            JockeyProviderAdapter.JOCKEY_URI,
            JockeyContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = JockeyContract.cursorToItem(cursor);

            result.setDzerzerBCze(
                this.getAssociateDzerzerBCze(result));
            if (result.getIuytrezBa() != null) {
                result.setIuytrezBa(
                    this.getAssociateIuytrezBa(result));
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
                JockeyProviderAdapter.JOCKEY_URI,
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
                JockeyProviderAdapter.JOCKEY_URI,
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

        Uri uri = JockeyProviderAdapter.JOCKEY_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getFbgDFbdf()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());


        operations.add(ContentProviderOperation.newDelete(PoneytoJockeyProviderAdapter.PONEYTOJOCKEY_URI)
                .withSelection(PoneytoJockeyContract.COL_PONEYDFD1_FBGDFBDF + "= ?",
                                new String[]{String.valueOf(item.getFbgDFbdf())})
                .build());

        for (Poney poney : item.getDzerzerBCze()) {
            ContentValues poneyValues = new ContentValues();
            poneyValues.put(PoneytoJockeyContract.COL_DZERZERBCZE_IDLIOEM1,
                    poney.getIdlioEm1());
            poneyValues.put(PoneytoJockeyContract.COL_PONEYDFD1_FBGDFBDF,
                    item.getFbgDFbdf());

            operations.add(ContentProviderOperation.newInsert(PoneytoJockeyProviderAdapter.PONEYTOJOCKEY_URI)
                    .withValues(poneyValues)
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
     * Get associate DzerzerBCze.
     * @param item Jockey
     * @return Poney
     */
    public ArrayList<Poney> getAssociateDzerzerBCze(
            final Jockey item) {
        ArrayList<Poney> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor poneytoJockeyCursor = prov.query(
                PoneytoJockeyProviderAdapter.PONEYTOJOCKEY_URI,
                PoneytoJockeyContract.ALIASED_COLS,
                PoneytoJockeyContract.ALIASED_COL_PONEYDFD1_FBGDFBDF 
                        + "= ?",
                new String[]{String.valueOf(item.getFbgDFbdf())},
                null);

        if (poneytoJockeyCursor.getCount() > 0) {
            CriteriaExpression poneyCrits =
                    new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            ArrayValue arrayValue = new ArrayValue();
            inCrit.setKey(PoneyContract.ALIASED_COL_IDLIOEM1);
            inCrit.setType(Type.IN);
            inCrit.addValue(arrayValue);
            poneyCrits.add(inCrit);

            while (poneytoJockeyCursor.moveToNext()) {
                int index = poneytoJockeyCursor.getColumnIndex(
                        PoneytoJockeyContract.COL_DZERZERBCZE_IDLIOEM1);
                String poneyIdlioEm1 = poneytoJockeyCursor.getString(index);

                arrayValue.addValue(poneyIdlioEm1);
            }
            poneytoJockeyCursor.close();
            android.database.Cursor poneyCursor = prov.query(
                    PoneyProviderAdapter.PONEY_URI,
                    PoneyContract.ALIASED_COLS,
                    poneyCrits.toSQLiteSelection(),
                    poneyCrits.toSQLiteSelectionArgs(),
                    null);

            result = PoneyContract.cursorToItems(poneyCursor);
            poneyCursor.close();
        } else {
            result = new ArrayList<Poney>();
        }

        return result;
    }

    /**
     * Get associate IuytrezBa.
     * @param item Jockey
     * @return User
     */
    public User getAssociateIuytrezBa(
            final Jockey item) {
        User result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor userCursor = prov.query(
                UserProviderAdapter.USER_URI,
                UserContract.ALIASED_COLS,
                UserContract.ALIASED_COL_ID1HNY + "= ?",
                new String[]{String.valueOf(item.getIuytrezBa().getId1HNY())},
                null);

        if (userCursor.getCount() > 0) {
            userCursor.moveToFirst();
            result = UserContract.cursorToItem(userCursor);
        } else {
            result = null;
        }
        userCursor.close();

        return result;
    }

}
