/**************************************************************************
 * HiddenEntityProviderAdapterBase.java, demactor Android
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



import com.jeanlui.demactor.entity.HiddenEntity;
import com.jeanlui.demactor.provider.ProviderAdapter;
import com.jeanlui.demactor.provider.DemactorProvider;
import com.jeanlui.demactor.provider.contract.HiddenEntityContract;
import com.jeanlui.demactor.data.HiddenEntitySQLiteAdapter;

/**
 * HiddenEntityProviderAdapterBase.
 */
public abstract class HiddenEntityProviderAdapterBase
                extends ProviderAdapter<HiddenEntity> {

    /** TAG for debug purpose. */
    protected static final String TAG = "HiddenEntityProviderAdapter";

    /** HIDDENENTITY_URI. */
    public      static Uri HIDDENENTITY_URI;

    /** hiddenEntity type. */
    protected static final String hiddenEntityType =
            "hiddenentity";

    /** HIDDENENTITY_ALL. */
    protected static final int HIDDENENTITY_ALL =
            973710771;
    /** HIDDENENTITY_ONE. */
    protected static final int HIDDENENTITY_ONE =
            973710772;


    /**
     * Static constructor.
     */
    static {
        HIDDENENTITY_URI =
                DemactorProvider.generateUri(
                        hiddenEntityType);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                hiddenEntityType,
                HIDDENENTITY_ALL);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                hiddenEntityType + "/#",
                HIDDENENTITY_ONE);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public HiddenEntityProviderAdapterBase(
            DemactorProviderBase provider) {
        super(
            provider,
            new HiddenEntitySQLiteAdapter(provider.getContext()));

        this.uriIds.add(HIDDENENTITY_ALL);
        this.uriIds.add(HIDDENENTITY_ONE);
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
            case HIDDENENTITY_ALL:
                result = collection + "hiddenentity";
                break;
            case HIDDENENTITY_ONE:
                result = single + "hiddenentity";
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
            case HIDDENENTITY_ONE:
                String id = uri.getPathSegments().get(1);
                selection = HiddenEntityContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case HIDDENENTITY_ALL:
                result = this.adapter.delete(
                            selection,
                            selectionArgs);
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
                Uri result = null;
        int id = 0;
        switch (matchedUri) {
            case HIDDENENTITY_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(HiddenEntityContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            HIDDENENTITY_URI,
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

        switch (matchedUri) {

            case HIDDENENTITY_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case HIDDENENTITY_ONE:
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

        
        int matchedUri = DemactorProviderBase.getUriMatcher()
                .match(uri);
        int result = -1;
        switch (matchedUri) {
            case HIDDENENTITY_ONE:
                selectionArgs = new String[1];
                selection = HiddenEntityContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case HIDDENENTITY_ALL:
                result = this.adapter.update(
                            values,
                            selection,
                            selectionArgs);
                break;
            default:
                result = -1;
                break;
        }
        return result;
    }



    /**
     * Get the entity URI.
     * @return The URI
     */
    @Override
    public Uri getUri() {
        return HIDDENENTITY_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = HiddenEntityContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    HiddenEntityContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

