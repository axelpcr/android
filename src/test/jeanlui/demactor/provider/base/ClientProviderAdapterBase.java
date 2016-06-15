/**************************************************************************
 * ClientProviderAdapterBase.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.provider.base;

import android.content.ContentUris;
import android.content.ContentValues;


import com.google.common.collect.ObjectArrays;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.google.common.base.Strings;

import com.jeanlui.demactor.entity.Client;
import com.jeanlui.demactor.provider.ProviderAdapter;
import com.jeanlui.demactor.provider.DemactorProvider;
import com.jeanlui.demactor.provider.contract.ClientContract;
import com.jeanlui.demactor.provider.contract.UserContract;
import com.jeanlui.demactor.provider.UserProviderAdapter;
import com.jeanlui.demactor.data.UserSQLiteAdapter;

import com.jeanlui.demactor.criterias.base.Criterion;
import com.jeanlui.demactor.criterias.base.Criterion.Type;
import com.jeanlui.demactor.criterias.base.CriteriaExpression;
import com.jeanlui.demactor.criterias.base.CriteriaExpression.GroupType;
import com.jeanlui.demactor.criterias.base.value.ArrayValue;
import com.jeanlui.demactor.data.ClientSQLiteAdapter;
import com.jeanlui.demactor.data.UserSQLiteAdapter;
import com.jeanlui.demactor.data.UserGroupSQLiteAdapter;
import com.jeanlui.demactor.data.UsertoUserSQLiteAdapter;
import com.jeanlui.demactor.harmony.util.DatabaseUtil;

/**
 * ClientProviderAdapterBase.
 */
public abstract class ClientProviderAdapterBase
                extends ProviderAdapter<Client> {

    /** TAG for debug purpose. */
    protected static final String TAG = "ClientProviderAdapter";

    /** CLIENT_URI. */
    public      static Uri CLIENT_URI;

    /** client type. */
    protected static final String clientType =
            "client";

    /** CLIENT_ALL. */
    protected static final int CLIENT_ALL =
            2021122027;
    /** CLIENT_ONE. */
    protected static final int CLIENT_ONE =
            2021122028;


    /**
     * Static constructor.
     */
    static {
        CLIENT_URI =
                DemactorProvider.generateUri(
                        clientType);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                clientType,
                CLIENT_ALL);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                clientType + "/#",
                CLIENT_ONE);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public ClientProviderAdapterBase(
            DemactorProviderBase provider) {
        super(
            provider,
            new ClientSQLiteAdapter(provider.getContext()));

        this.uriIds.add(CLIENT_ALL);
        this.uriIds.add(CLIENT_ONE);
    }

    @Override
    public String getType(final Uri uri) {
        String result;
        final String single =
                "vnc.android.cursor.item/"
                    + DemactorProvider.authority + ".";
        final String collection =
                "vnc.android.cursor.collection/"
                    + DemactorProvider.authority + ".";

        int matchedUri = DemactorProviderBase
                .getUriMatcher().match(uri);

        switch (matchedUri) {
            case CLIENT_ALL:
                result = collection + "client";
                break;
            case CLIENT_ONE:
                result = single + "client";
                break;
            default:
                result = null;
                break;
        }

        return result;
    }

    @Override
    public int delete(
            final Uri uri,
            String selection,
            String[] selectionArgs) {
        int matchedUri = DemactorProviderBase
                    .getUriMatcher().match(uri);
        int result = -1;
        switch (matchedUri) {
            case CLIENT_ONE:
                String id = uri.getPathSegments().get(1);
                Uri motherUri = Uri.withAppendedPath(
                        UserProviderAdapter.USER_URI, id);
                result = this.ctx.getContentResolver().delete(motherUri,
                        selection, selectionArgs);
                break;
            case CLIENT_ALL:
                // Query the ids of the changing fields.
                android.database.Cursor idsCursor = this.adapter.query(
                        new String[]{UserContract.ALIASED_COL_ID},
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null);
                // If there are ids
                if (idsCursor.getCount() > 0) {
                    CriteriaExpression parentCrit = this.cursorToIDSelection(idsCursor,
                                UserContract.COL_ID);
                    String parentSelection = parentCrit.toSQLiteSelection();
                    String[] parentSelectionArgs = parentCrit.toSQLiteSelectionArgs();
                    result = this.ctx.getContentResolver().delete(
                            UserProviderAdapter.USER_URI,
                            parentSelection,
                            parentSelectionArgs);
                }
                break;
            default:
                result = -1;
                break;
        }
        return result;
    }

    @Override
    public Uri insert(final Uri uri, final ContentValues values) {
        int matchedUri = DemactorProviderBase
                .getUriMatcher().match(uri);
        ContentValues clientValues =
            DatabaseUtil.extractContentValues(values, ClientContract.COLS);
        values.put(UserContract.COL_ID,
                clientValues.getAsString(
                        UserContract.COL_ID));
        Uri result = null;
        int id = 0;
        switch (matchedUri) {
            case CLIENT_ALL:
                Uri newUri = this.ctx.getContentResolver().insert(
                        UserProviderAdapter.USER_URI,
                        values);
                int newId = Integer.parseInt(newUri.getPathSegments().get(1));
                clientValues.put(UserContract.COL_ID, newId);
                if (clientValues.size() > 0) {
                    id = (int) this.adapter.insert(null, clientValues);
                } else {
                    id = (int) this.adapter.insert(UserContract.COL_ID, clientValues);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            CLIENT_URI,
                            String.valueOf(id));
                }
                break;
            default:
                result = null;
                break;
        }
        return result;
    }

    @Override
    public android.database.Cursor query(final Uri uri,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        final String sortOrder) {

        int matchedUri = DemactorProviderBase.getUriMatcher()
                .match(uri);
        android.database.Cursor result = null;
        android.database.Cursor clientCursor;
        int clientId;

        switch (matchedUri) {

            case CLIENT_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case CLIENT_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            default:
                result = null;
                break;
        }

        return result;
    }

    @Override
    public int update(
            final Uri uri,
            final ContentValues values,
            String selection,
            String[] selectionArgs) {

        ContentValues clientValues = DatabaseUtil.extractContentValues(values, ClientContract.COLS);
        int matchedUri = DemactorProviderBase.getUriMatcher()
                .match(uri);
        int result = -1;
        switch (matchedUri) {
            case CLIENT_ONE:
                selectionArgs = new String[1];
                selection = UserContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                Uri parentUri = Uri.withAppendedPath(UserProviderAdapter.USER_URI,
                        uri.getPathSegments().get(1));
                result = this.ctx.getContentResolver().update(
                        parentUri,
                        values,
                        null,
                        null);
                result += this.adapter.update(
                        clientValues,
                        selection,
                        selectionArgs);
                break;
            case CLIENT_ALL:
                // Query the ids of the changing fields.
                android.database.Cursor idsCursor = this.adapter.query(
                        new String[]{UserContract.ALIASED_COL_ID},
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null);
                // If there are ids
                if (idsCursor.getCount() > 0) {
                    // If there are values in this table
                    if (clientValues.size() > 0) {
                        CriteriaExpression currentCrit = this.cursorToIDSelection(
                                idsCursor,
                                UserContract.COL_ID);

                        String currentSelection = currentCrit.toSQLiteSelection();
                        String[] currentSelectionArgs = currentCrit
                                .toSQLiteSelectionArgs();
                        // Update the current table
                        result += this.adapter.update(
                                clientValues,
                                currentSelection,
                                currentSelectionArgs);
                    }
                    // If there are still values to be updated in parents
                    if (values.size() > 0) {
                        CriteriaExpression parentCrit = this.cursorToIDSelection(
                                idsCursor,
                                UserContract.COL_ID);

                        String parentSelection = parentCrit.toSQLiteSelection();
                        String[] parentSelectionArgs = parentCrit
                                .toSQLiteSelectionArgs();
                        // Update the parents tables
                        result = this.ctx.getContentResolver().update(
                                UserProviderAdapter.USER_URI,
                                values,
                                parentSelection,
                                parentSelectionArgs);
                    }
                }
                break;
            default:
                result = -1;
                break;
        }
        return result;
    }

    /**
     * Transform a cursor of ids into a criteria expression.
     *
     * @param cursor The cursor
     * @param key The key to map the ids to
     *
     * @return The expression
     */
    protected CriteriaExpression cursorToIDSelection(android.database.Cursor cursor, String key) {
        CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
        Criterion inCrit = new Criterion();
        inCrit.setKey(key);
        inCrit.setType(Type.IN);
        ArrayValue inArray = new ArrayValue();
        cursor.moveToFirst();
        do {
            inArray.addValue(cursor.getString(cursor.getColumnIndex(UserContract.COL_ID)));
        } while (cursor.moveToNext());
        inCrit.addValue(inArray);
        crit.add(inCrit);
        return crit;
    }


    /**
     * Get the entity URI.
     * @return The URI
     */
    @Override
    public Uri getUri() {
        return CLIENT_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = UserContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    ClientContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

