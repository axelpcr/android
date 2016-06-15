/**************************************************************************
 * GroupProviderAdapterBase.java, demactor Android
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



import com.jeanlui.demactor.entity.Group;
import com.jeanlui.demactor.provider.ProviderAdapter;
import com.jeanlui.demactor.provider.DemactorProvider;
import com.jeanlui.demactor.provider.contract.GroupContract;
import com.jeanlui.demactor.provider.contract.GroupToCommentContract;
import com.jeanlui.demactor.data.GroupSQLiteAdapter;
import com.jeanlui.demactor.data.GroupToCommentSQLiteAdapter;

/**
 * GroupProviderAdapterBase.
 */
public abstract class GroupProviderAdapterBase
                extends ProviderAdapter<Group> {

    /** TAG for debug purpose. */
    protected static final String TAG = "GroupProviderAdapter";

    /** GROUP_URI. */
    public      static Uri GROUP_URI;

    /** group type. */
    protected static final String groupType =
            "group";

    /** GROUP_ALL. */
    protected static final int GROUP_ALL =
            69076575;
    /** GROUP_ONE. */
    protected static final int GROUP_ONE =
            69076576;

    /** GROUP_COMMENTS. */
    protected static final int GROUP_COMMENTS =
            69076577;

    /**
     * Static constructor.
     */
    static {
        GROUP_URI =
                DemactorProvider.generateUri(
                        groupType);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                groupType,
                GROUP_ALL);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                groupType + "/#",
                GROUP_ONE);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                groupType + "/#" + "/comments",
                GROUP_COMMENTS);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public GroupProviderAdapterBase(
            DemactorProviderBase provider) {
        super(
            provider,
            new GroupSQLiteAdapter(provider.getContext()));

        this.uriIds.add(GROUP_ALL);
        this.uriIds.add(GROUP_ONE);
        this.uriIds.add(GROUP_COMMENTS);
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
            case GROUP_ALL:
                result = collection + "group";
                break;
            case GROUP_ONE:
                result = single + "group";
                break;
            case GROUP_COMMENTS:
                result = collection + "group";
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
            case GROUP_ONE:
                String id = uri.getPathSegments().get(1);
                selection = GroupContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case GROUP_ALL:
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
            case GROUP_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(GroupContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            GROUP_URI,
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
        int groupId;

        switch (matchedUri) {

            case GROUP_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case GROUP_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            case GROUP_COMMENTS:
                groupId = Integer.parseInt(uri.getPathSegments().get(1));
                GroupToCommentSQLiteAdapter commentsAdapter = new GroupToCommentSQLiteAdapter(this.ctx);
                commentsAdapter.open(this.getDb());
                result = commentsAdapter.getByGroup(groupId, GroupToCommentContract.ALIASED_COLS, selection, selectionArgs, null);
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
            case GROUP_ONE:
                selectionArgs = new String[1];
                selection = GroupContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case GROUP_ALL:
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
        return GROUP_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = GroupContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    GroupContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

