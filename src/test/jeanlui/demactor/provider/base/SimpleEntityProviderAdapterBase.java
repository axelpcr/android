/**************************************************************************
 * SimpleEntityProviderAdapterBase.java, demactor Android
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



import com.jeanlui.demactor.entity.SimpleEntity;
import com.jeanlui.demactor.provider.ProviderAdapter;
import com.jeanlui.demactor.provider.DemactorProvider;
import com.jeanlui.demactor.provider.contract.SimpleEntityContract;
import com.jeanlui.demactor.data.SimpleEntitySQLiteAdapter;

/**
 * SimpleEntityProviderAdapterBase.
 */
public abstract class SimpleEntityProviderAdapterBase
                extends ProviderAdapter<SimpleEntity> {

    /** TAG for debug purpose. */
    protected static final String TAG = "SimpleEntityProviderAdapter";

    /** SIMPLEENTITY_URI. */
    public      static Uri SIMPLEENTITY_URI;

    /** simpleEntity type. */
    protected static final String simpleEntityType =
            "simpleentity";

    /** SIMPLEENTITY_ALL. */
    protected static final int SIMPLEENTITY_ALL =
            1619867563;
    /** SIMPLEENTITY_ONE. */
    protected static final int SIMPLEENTITY_ONE =
            1619867564;


    /**
     * Static constructor.
     */
    static {
        SIMPLEENTITY_URI =
                DemactorProvider.generateUri(
                        simpleEntityType);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                simpleEntityType,
                SIMPLEENTITY_ALL);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                simpleEntityType + "/#",
                SIMPLEENTITY_ONE);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public SimpleEntityProviderAdapterBase(
            DemactorProviderBase provider) {
        super(
            provider,
            new SimpleEntitySQLiteAdapter(provider.getContext()));

        this.uriIds.add(SIMPLEENTITY_ALL);
        this.uriIds.add(SIMPLEENTITY_ONE);
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
            case SIMPLEENTITY_ALL:
                result = collection + "simpleentity";
                break;
            case SIMPLEENTITY_ONE:
                result = single + "simpleentity";
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
            case SIMPLEENTITY_ONE:
                String id = uri.getPathSegments().get(1);
                selection = SimpleEntityContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case SIMPLEENTITY_ALL:
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
            case SIMPLEENTITY_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(SimpleEntityContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            SIMPLEENTITY_URI,
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

            case SIMPLEENTITY_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case SIMPLEENTITY_ONE:
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
            case SIMPLEENTITY_ONE:
                selectionArgs = new String[1];
                selection = SimpleEntityContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case SIMPLEENTITY_ALL:
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
        return SIMPLEENTITY_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = SimpleEntityContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    SimpleEntityContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

