/**************************************************************************
 * GroupToCommentProviderAdapterBase.java, demactor Android
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



import com.jeanlui.demactor.entity.GroupToComment;
import com.jeanlui.demactor.provider.ProviderAdapter;
import com.jeanlui.demactor.provider.DemactorProvider;
import com.jeanlui.demactor.provider.contract.GroupToCommentContract;
import com.jeanlui.demactor.data.GroupToCommentSQLiteAdapter;
import com.jeanlui.demactor.data.GroupSQLiteAdapter;

/**
 * GroupToCommentProviderAdapterBase.
 */
public abstract class GroupToCommentProviderAdapterBase
                extends ProviderAdapter<GroupToComment> {

    /** TAG for debug purpose. */
    protected static final String TAG = "GroupToCommentProviderAdapter";

    /** GROUPTOCOMMENT_URI. */
    public      static Uri GROUPTOCOMMENT_URI;

    /** groupToComment type. */
    protected static final String groupToCommentType =
            "grouptocomment";

    /** GROUPTOCOMMENT_ALL. */
    protected static final int GROUPTOCOMMENT_ALL =
            1786715077;
    /** GROUPTOCOMMENT_ONE. */
    protected static final int GROUPTOCOMMENT_ONE =
            1786715078;

    /** GROUPTOCOMMENT_GROUP. */
    protected static final int GROUPTOCOMMENT_GROUP =
            1786715079;

    /**
     * Static constructor.
     */
    static {
        GROUPTOCOMMENT_URI =
                DemactorProvider.generateUri(
                        groupToCommentType);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                groupToCommentType,
                GROUPTOCOMMENT_ALL);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                groupToCommentType + "/#",
                GROUPTOCOMMENT_ONE);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                groupToCommentType + "/#" + "/group",
                GROUPTOCOMMENT_GROUP);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public GroupToCommentProviderAdapterBase(
            DemactorProviderBase provider) {
        super(
            provider,
            new GroupToCommentSQLiteAdapter(provider.getContext()));

        this.uriIds.add(GROUPTOCOMMENT_ALL);
        this.uriIds.add(GROUPTOCOMMENT_ONE);
        this.uriIds.add(GROUPTOCOMMENT_GROUP);
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
            case GROUPTOCOMMENT_ALL:
                result = collection + "grouptocomment";
                break;
            case GROUPTOCOMMENT_ONE:
                result = single + "grouptocomment";
                break;
            case GROUPTOCOMMENT_GROUP:
                result = single + "grouptocomment";
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
            case GROUPTOCOMMENT_ONE:
                String id = uri.getPathSegments().get(1);
                selection = GroupToCommentContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case GROUPTOCOMMENT_ALL:
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
            case GROUPTOCOMMENT_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(GroupToCommentContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            GROUPTOCOMMENT_URI,
                            values.getAsString(GroupToCommentContract.COL_ID));
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
        android.database.Cursor groupToCommentCursor;

        switch (matchedUri) {

            case GROUPTOCOMMENT_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case GROUPTOCOMMENT_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            case GROUPTOCOMMENT_GROUP:
                groupToCommentCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (groupToCommentCursor.getCount() > 0) {
                    groupToCommentCursor.moveToFirst();
                    int groupId = groupToCommentCursor.getInt(
                            groupToCommentCursor.getColumnIndex(
                                    GroupToCommentContract.COL_GROUP_ID));

                    GroupSQLiteAdapter groupAdapter = new GroupSQLiteAdapter(this.ctx);
                    groupAdapter.open(this.getDb());
                    result = groupAdapter.query(groupId);
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
            case GROUPTOCOMMENT_ONE:
                selectionArgs = new String[1];
                selection = GroupToCommentContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case GROUPTOCOMMENT_ALL:
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
        return GROUPTOCOMMENT_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = GroupToCommentContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    GroupToCommentContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

