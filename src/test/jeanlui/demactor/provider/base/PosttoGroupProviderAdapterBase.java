/**************************************************************************
 * PosttoGroupProviderAdapterBase.java, demactor Android
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
import com.jeanlui.demactor.provider.contract.PosttoGroupContract;
import com.jeanlui.demactor.data.PosttoGroupSQLiteAdapter;
import com.jeanlui.demactor.data.PostSQLiteAdapter;
import com.jeanlui.demactor.data.GroupSQLiteAdapter;

/**
 * PosttoGroupProviderAdapterBase.
 */
public abstract class PosttoGroupProviderAdapterBase
                extends ProviderAdapter<Void> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PosttoGroupProviderAdapter";

    /** POSTTOGROUP_URI. */
    public      static Uri POSTTOGROUP_URI;

    /** posttoGroup type. */
    protected static final String posttoGroupType =
            "posttogroup";

    /** POSTTOGROUP_ALL. */
    protected static final int POSTTOGROUP_ALL =
            638523388;
    /** POSTTOGROUP_ONE. */
    protected static final int POSTTOGROUP_ONE =
            638523389;

    /** POSTTOGROUP_POSTINTERNALID. */
    protected static final int POSTTOGROUP_POSTINTERNALID =
            638523390;
    /** POSTTOGROUP_GROUPS. */
    protected static final int POSTTOGROUP_GROUPS =
            638523391;

    /**
     * Static constructor.
     */
    static {
        POSTTOGROUP_URI =
                DemactorProvider.generateUri(
                        posttoGroupType);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                posttoGroupType,
                POSTTOGROUP_ALL);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                posttoGroupType + "/#" + "/#",
                POSTTOGROUP_ONE);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                posttoGroupType + "/#" + "/#" + "/postinternalid",
                POSTTOGROUP_POSTINTERNALID);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                posttoGroupType + "/#" + "/#" + "/groups",
                POSTTOGROUP_GROUPS);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public PosttoGroupProviderAdapterBase(
            DemactorProviderBase provider) {
        super(
            provider,
            new PosttoGroupSQLiteAdapter(provider.getContext()));

        this.uriIds.add(POSTTOGROUP_ALL);
        this.uriIds.add(POSTTOGROUP_ONE);
        this.uriIds.add(POSTTOGROUP_POSTINTERNALID);
        this.uriIds.add(POSTTOGROUP_GROUPS);
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
            case POSTTOGROUP_ONE:
                String PostInternalIdId = uri.getPathSegments().get(1);
                String groupsId = uri.getPathSegments().get(2);
                selection = PosttoGroupContract.COL_POSTINTERNALID_ID
                        + " = ?"
                        + " AND "
                        + PosttoGroupContract.COL_GROUPS_ID
                        + " = ?";
                selectionArgs = new String[2];
                selectionArgs[0] = PostInternalIdId;
                selectionArgs[1] = groupsId;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case POSTTOGROUP_ALL:
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
            case POSTTOGROUP_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(PosttoGroupContract.COL_POSTINTERNALID_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            POSTTOGROUP_URI,
                            values.getAsString(PosttoGroupContract.COL_POSTINTERNALID_ID)
                            + "/"
                            + values.getAsString(PosttoGroupContract.COL_GROUPS_ID));
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
        android.database.Cursor posttoGroupCursor;

        switch (matchedUri) {

            case POSTTOGROUP_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case POSTTOGROUP_ONE:
                result = this.queryById(uri.getPathSegments().get(1),
                        uri.getPathSegments().get(2));
                break;

            case POSTTOGROUP_POSTINTERNALID:
                posttoGroupCursor = this.queryById(
                        uri.getPathSegments().get(1),
                        uri.getPathSegments().get(2));

                if (posttoGroupCursor.getCount() > 0) {
                    posttoGroupCursor.moveToFirst();
                    int PostInternalIdId = posttoGroupCursor.getInt(
                            posttoGroupCursor.getColumnIndex(
                                    PosttoGroupContract.COL_POSTINTERNALID_ID));

                    PostSQLiteAdapter postAdapter = new PostSQLiteAdapter(this.ctx);
                    postAdapter.open(this.getDb());
                    result = postAdapter.query(PostInternalIdId);
                }
                break;

            case POSTTOGROUP_GROUPS:
                posttoGroupCursor = this.queryById(
                        uri.getPathSegments().get(1),
                        uri.getPathSegments().get(2));

                if (posttoGroupCursor.getCount() > 0) {
                    posttoGroupCursor.moveToFirst();
                    int groupsId = posttoGroupCursor.getInt(
                            posttoGroupCursor.getColumnIndex(
                                    PosttoGroupContract.COL_GROUPS_ID));

                    GroupSQLiteAdapter groupAdapter = new GroupSQLiteAdapter(this.ctx);
                    groupAdapter.open(this.getDb());
                    result = groupAdapter.query(groupsId);
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
            case POSTTOGROUP_ONE:
                selectionArgs = new String[2];
                selection = PosttoGroupContract.COL_POSTINTERNALID_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                selection += " AND " + PosttoGroupContract.COL_GROUPS_ID + " = ?";
                selectionArgs[1] = uri.getPathSegments().get(2);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case POSTTOGROUP_ALL:
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
        return POSTTOGROUP_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String PostInternalIdId, String groupsId) {
        android.database.Cursor result = null;
        String selection = PosttoGroupContract.ALIASED_COL_POSTINTERNALID_ID
                        + " = ?"
                        + " AND "
                        + PosttoGroupContract.ALIASED_COL_GROUPS_ID
                        + " = ?";

        String[] selectionArgs = new String[2];
        selectionArgs[0] = PostInternalIdId;
        selectionArgs[1] = groupsId;
        
        

        result = this.adapter.query(
                    PosttoGroupContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

