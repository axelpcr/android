/**************************************************************************
 * UserProviderAdapterBase.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 4, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.provider.base;

import android.content.ContentUris;
import android.content.ContentValues;


import com.google.common.collect.ObjectArrays;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;



import com.jeanlui.demactor.entity.User;
import com.jeanlui.demactor.provider.ProviderAdapter;
import com.jeanlui.demactor.provider.DemactorProvider;
import com.jeanlui.demactor.provider.contract.UserContract;
import com.jeanlui.demactor.data.UserSQLiteAdapter;
import com.jeanlui.demactor.data.ScoreSQLiteAdapter;
import com.jeanlui.demactor.data.JockeySQLiteAdapter;

/**
 * UserProviderAdapterBase.
 */
public abstract class UserProviderAdapterBase
                extends ProviderAdapter<User> {

    /** TAG for debug purpose. */
    protected static final String TAG = "UserProviderAdapter";

    /** USER_URI. */
    public      static Uri USER_URI;

    /** user type. */
    protected static final String userType =
            "user";

    /** USER_ALL. */
    protected static final int USER_ALL =
            2645995;
    /** USER_ONE. */
    protected static final int USER_ONE =
            2645996;

    /** USER_SCOFGHRE1. */
    protected static final int USER_SCOFGHRE1 =
            2645997;
    /** USER_JOCFGHKEY1. */
    protected static final int USER_JOCFGHKEY1 =
            2645998;

    /**
     * Static constructor.
     */
    static {
        USER_URI =
                DemactorProvider.generateUri(
                        userType);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                userType,
                USER_ALL);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                userType + "/#",
                USER_ONE);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                userType + "/#" + "/scofghre1",
                USER_SCOFGHRE1);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                userType + "/#" + "/jocfghkey1",
                USER_JOCFGHKEY1);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public UserProviderAdapterBase(
            DemactorProviderBase provider) {
        super(
            provider,
            new UserSQLiteAdapter(provider.getContext()));

        this.uriIds.add(USER_ALL);
        this.uriIds.add(USER_ONE);
        this.uriIds.add(USER_SCOFGHRE1);
        this.uriIds.add(USER_JOCFGHKEY1);
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
            case USER_ALL:
                result = collection + "user";
                break;
            case USER_ONE:
                result = single + "user";
                break;
            case USER_SCOFGHRE1:
                result = single + "user";
                break;
            case USER_JOCFGHKEY1:
                result = single + "user";
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
            case USER_ONE:
                String id1HNY = uri.getPathSegments().get(1);
                selection = UserContract.COL_ID1HNY
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id1HNY;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case USER_ALL:
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
            case USER_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(UserContract.COL_ID1HNY, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            USER_URI,
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
        android.database.Cursor userCursor;

        switch (matchedUri) {

            case USER_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case USER_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            case USER_SCOFGHRE1:
                userCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (userCursor.getCount() > 0) {
                    userCursor.moveToFirst();
                    int scoFGHre1IdFD1 = userCursor.getInt(
                            userCursor.getColumnIndex(
                                    UserContract.COL_SCOFGHRE1_IDFD1));

                    ScoreSQLiteAdapter scoreAdapter = new ScoreSQLiteAdapter(this.ctx);
                    scoreAdapter.open(this.getDb());
                    result = scoreAdapter.query(scoFGHre1IdFD1);
                }
                break;

            case USER_JOCFGHKEY1:
                userCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (userCursor.getCount() > 0) {
                    userCursor.moveToFirst();
                    int jocFGHkey1FbgDFbdf = userCursor.getInt(
                            userCursor.getColumnIndex(
                                    UserContract.COL_JOCFGHKEY1_FBGDFBDF));

                    JockeySQLiteAdapter jockeyAdapter = new JockeySQLiteAdapter(this.ctx);
                    jockeyAdapter.open(this.getDb());
                    result = jockeyAdapter.query(jocFGHkey1FbgDFbdf);
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
            case USER_ONE:
                selectionArgs = new String[1];
                selection = UserContract.COL_ID1HNY + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case USER_ALL:
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
        return USER_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id1HNY) {
        android.database.Cursor result = null;
        String selection = UserContract.ALIASED_COL_ID1HNY
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id1HNY;
        
        

        result = this.adapter.query(
                    UserContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

