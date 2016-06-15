/**************************************************************************
 * UsertoUserProviderAdapterBase.java, demactor Android
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



import com.jeanlui.demactor.provider.ProviderAdapter;
import com.jeanlui.demactor.provider.DemactorProvider;
import com.jeanlui.demactor.provider.contract.UsertoUserContract;
import com.jeanlui.demactor.data.UsertoUserSQLiteAdapter;
import com.jeanlui.demactor.data.UserSQLiteAdapter;

/**
 * UsertoUserProviderAdapterBase.
 */
public abstract class UsertoUserProviderAdapterBase
                extends ProviderAdapter<Void> {

    /** TAG for debug purpose. */
    protected static final String TAG = "UsertoUserProviderAdapter";

    /** USERTOUSER_URI. */
    public      static Uri USERTOUSER_URI;

    /** usertoUser type. */
    protected static final String usertoUserType =
            "usertouser";

    /** USERTOUSER_ALL. */
    protected static final int USERTOUSER_ALL =
            229915793;
    /** USERTOUSER_ONE. */
    protected static final int USERTOUSER_ONE =
            229915794;

    /** USERTOUSER_USERINTERNALID. */
    protected static final int USERTOUSER_USERINTERNALID =
            229915795;
    /** USERTOUSER_FRIENDS. */
    protected static final int USERTOUSER_FRIENDS =
            229915796;

    /**
     * Static constructor.
     */
    static {
        USERTOUSER_URI =
                DemactorProvider.generateUri(
                        usertoUserType);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                usertoUserType,
                USERTOUSER_ALL);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                usertoUserType + "/#" + "/#",
                USERTOUSER_ONE);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                usertoUserType + "/#" + "/#" + "/userinternalid",
                USERTOUSER_USERINTERNALID);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                usertoUserType + "/#" + "/#" + "/friends",
                USERTOUSER_FRIENDS);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public UsertoUserProviderAdapterBase(
            DemactorProviderBase provider) {
        super(
            provider,
            new UsertoUserSQLiteAdapter(provider.getContext()));

        this.uriIds.add(USERTOUSER_ALL);
        this.uriIds.add(USERTOUSER_ONE);
        this.uriIds.add(USERTOUSER_USERINTERNALID);
        this.uriIds.add(USERTOUSER_FRIENDS);
    }

    @Override
    public String getType(final Uri uri) {
        return null;
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
            case USERTOUSER_ONE:
                String UserInternalIdId = uri.getPathSegments().get(1);
                String friendsId = uri.getPathSegments().get(2);
                selection = UsertoUserContract.COL_USERINTERNALID_ID
                        + " = ?"
                        + " AND "
                        + UsertoUserContract.COL_FRIENDS_ID
                        + " = ?";
                selectionArgs = new String[2];
                selectionArgs[0] = UserInternalIdId;
                selectionArgs[1] = friendsId;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case USERTOUSER_ALL:
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
            case USERTOUSER_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(UsertoUserContract.COL_USERINTERNALID_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            USERTOUSER_URI,
                            values.getAsString(UsertoUserContract.COL_USERINTERNALID_ID)
                            + "/"
                            + values.getAsString(UsertoUserContract.COL_FRIENDS_ID));
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
        android.database.Cursor usertoUserCursor;

        switch (matchedUri) {

            case USERTOUSER_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case USERTOUSER_ONE:
                result = this.queryById(uri.getPathSegments().get(1),
                        uri.getPathSegments().get(2));
                break;

            case USERTOUSER_USERINTERNALID:
                usertoUserCursor = this.queryById(
                        uri.getPathSegments().get(1),
                        uri.getPathSegments().get(2));

                if (usertoUserCursor.getCount() > 0) {
                    usertoUserCursor.moveToFirst();
                    int UserInternalIdId = usertoUserCursor.getInt(
                            usertoUserCursor.getColumnIndex(
                                    UsertoUserContract.COL_USERINTERNALID_ID));

                    UserSQLiteAdapter userAdapter = new UserSQLiteAdapter(this.ctx);
                    userAdapter.open(this.getDb());
                    result = userAdapter.query(UserInternalIdId);
                }
                break;

            case USERTOUSER_FRIENDS:
                usertoUserCursor = this.queryById(
                        uri.getPathSegments().get(1),
                        uri.getPathSegments().get(2));

                if (usertoUserCursor.getCount() > 0) {
                    usertoUserCursor.moveToFirst();
                    int friendsId = usertoUserCursor.getInt(
                            usertoUserCursor.getColumnIndex(
                                    UsertoUserContract.COL_FRIENDS_ID));

                    UserSQLiteAdapter userAdapter = new UserSQLiteAdapter(this.ctx);
                    userAdapter.open(this.getDb());
                    result = userAdapter.query(friendsId);
                }
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
            case USERTOUSER_ONE:
                selectionArgs = new String[2];
                selection = UsertoUserContract.COL_USERINTERNALID_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                selection += " AND " + UsertoUserContract.COL_FRIENDS_ID + " = ?";
                selectionArgs[1] = uri.getPathSegments().get(2);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case USERTOUSER_ALL:
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
        return USERTOUSER_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String UserInternalIdId, String friendsId) {
        android.database.Cursor result = null;
        String selection = UsertoUserContract.ALIASED_COL_USERINTERNALID_ID
                        + " = ?"
                        + " AND "
                        + UsertoUserContract.ALIASED_COL_FRIENDS_ID
                        + " = ?";

        String[] selectionArgs = new String[2];
        selectionArgs[0] = UserInternalIdId;
        selectionArgs[1] = friendsId;
        
        

        result = this.adapter.query(
                    UsertoUserContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

