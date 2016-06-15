/**************************************************************************
 * UserGroupProviderAdapterBase.java, demactor Android
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



import com.jeanlui.demactor.entity.UserGroup;
import com.jeanlui.demactor.provider.ProviderAdapter;
import com.jeanlui.demactor.provider.DemactorProvider;
import com.jeanlui.demactor.provider.contract.UserGroupContract;
import com.jeanlui.demactor.data.UserGroupSQLiteAdapter;

/**
 * UserGroupProviderAdapterBase.
 */
public abstract class UserGroupProviderAdapterBase
                extends ProviderAdapter<UserGroup> {

    /** TAG for debug purpose. */
    protected static final String TAG = "UserGroupProviderAdapter";

    /** USERGROUP_URI. */
    public      static Uri USERGROUP_URI;

    /** userGroup type. */
    protected static final String userGroupType =
            "usergroup";

    /** USERGROUP_ALL. */
    protected static final int USERGROUP_ALL =
            1973690028;
    /** USERGROUP_ONE. */
    protected static final int USERGROUP_ONE =
            1973690029;


    /**
     * Static constructor.
     */
    static {
        USERGROUP_URI =
                DemactorProvider.generateUri(
                        userGroupType);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                userGroupType,
                USERGROUP_ALL);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                userGroupType + "/#",
                USERGROUP_ONE);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public UserGroupProviderAdapterBase(
            DemactorProviderBase provider) {
        super(
            provider,
            new UserGroupSQLiteAdapter(provider.getContext()));

        this.uriIds.add(USERGROUP_ALL);
        this.uriIds.add(USERGROUP_ONE);
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
            case USERGROUP_ALL:
                result = collection + "usergroup";
                break;
            case USERGROUP_ONE:
                result = single + "usergroup";
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
            case USERGROUP_ONE:
                String id = uri.getPathSegments().get(1);
                selection = UserGroupContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case USERGROUP_ALL:
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
            case USERGROUP_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(UserGroupContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            USERGROUP_URI,
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

            case USERGROUP_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case USERGROUP_ONE:
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
            case USERGROUP_ONE:
                selectionArgs = new String[1];
                selection = UserGroupContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case USERGROUP_ALL:
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
        return USERGROUP_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = UserGroupContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    UserGroupContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

