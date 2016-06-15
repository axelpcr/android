/**************************************************************************
 * ViewComponentProviderAdapterBase.java, demactor Android
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



import com.jeanlui.demactor.entity.ViewComponent;
import com.jeanlui.demactor.provider.ProviderAdapter;
import com.jeanlui.demactor.provider.DemactorProvider;
import com.jeanlui.demactor.provider.contract.ViewComponentContract;
import com.jeanlui.demactor.data.ViewComponentSQLiteAdapter;

/**
 * ViewComponentProviderAdapterBase.
 */
public abstract class ViewComponentProviderAdapterBase
                extends ProviderAdapter<ViewComponent> {

    /** TAG for debug purpose. */
    protected static final String TAG = "ViewComponentProviderAdapter";

    /** VIEWCOMPONENT_URI. */
    public      static Uri VIEWCOMPONENT_URI;

    /** viewComponent type. */
    protected static final String viewComponentType =
            "viewcomponent";

    /** VIEWCOMPONENT_ALL. */
    protected static final int VIEWCOMPONENT_ALL =
            1203741768;
    /** VIEWCOMPONENT_ONE. */
    protected static final int VIEWCOMPONENT_ONE =
            1203741769;


    /**
     * Static constructor.
     */
    static {
        VIEWCOMPONENT_URI =
                DemactorProvider.generateUri(
                        viewComponentType);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                viewComponentType,
                VIEWCOMPONENT_ALL);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                viewComponentType + "/#",
                VIEWCOMPONENT_ONE);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public ViewComponentProviderAdapterBase(
            DemactorProviderBase provider) {
        super(
            provider,
            new ViewComponentSQLiteAdapter(provider.getContext()));

        this.uriIds.add(VIEWCOMPONENT_ALL);
        this.uriIds.add(VIEWCOMPONENT_ONE);
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
            case VIEWCOMPONENT_ALL:
                result = collection + "viewcomponent";
                break;
            case VIEWCOMPONENT_ONE:
                result = single + "viewcomponent";
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
            case VIEWCOMPONENT_ONE:
                String id = uri.getPathSegments().get(1);
                selection = ViewComponentContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case VIEWCOMPONENT_ALL:
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
            case VIEWCOMPONENT_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(ViewComponentContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            VIEWCOMPONENT_URI,
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

            case VIEWCOMPONENT_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case VIEWCOMPONENT_ONE:
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
            case VIEWCOMPONENT_ONE:
                selectionArgs = new String[1];
                selection = ViewComponentContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case VIEWCOMPONENT_ALL:
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
        return VIEWCOMPONENT_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = ViewComponentContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    ViewComponentContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

