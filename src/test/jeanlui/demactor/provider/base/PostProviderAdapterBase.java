/**************************************************************************
 * PostProviderAdapterBase.java, demactor Android
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



import com.jeanlui.demactor.entity.Post;
import com.jeanlui.demactor.provider.ProviderAdapter;
import com.jeanlui.demactor.provider.DemactorProvider;
import com.jeanlui.demactor.provider.contract.PostContract;
import com.jeanlui.demactor.provider.contract.CommentContract;
import com.jeanlui.demactor.provider.contract.GroupContract;
import com.jeanlui.demactor.data.PostSQLiteAdapter;
import com.jeanlui.demactor.data.UserSQLiteAdapter;
import com.jeanlui.demactor.data.CommentSQLiteAdapter;
import com.jeanlui.demactor.data.PosttoGroupSQLiteAdapter;
import com.jeanlui.demactor.data.GroupSQLiteAdapter;

/**
 * PostProviderAdapterBase.
 */
public abstract class PostProviderAdapterBase
                extends ProviderAdapter<Post> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PostProviderAdapter";

    /** POST_URI. */
    public      static Uri POST_URI;

    /** post type. */
    protected static final String postType =
            "post";

    /** POST_ALL. */
    protected static final int POST_ALL =
            2493632;
    /** POST_ONE. */
    protected static final int POST_ONE =
            2493633;

    /** POST_OWNER. */
    protected static final int POST_OWNER =
            2493634;
    /** POST_COMMENTS. */
    protected static final int POST_COMMENTS =
            2493635;
    /** POST_GROUPS. */
    protected static final int POST_GROUPS =
            2493636;

    /**
     * Static constructor.
     */
    static {
        POST_URI =
                DemactorProvider.generateUri(
                        postType);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                postType,
                POST_ALL);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                postType + "/#",
                POST_ONE);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                postType + "/#" + "/owner",
                POST_OWNER);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                postType + "/#" + "/comments",
                POST_COMMENTS);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                postType + "/#" + "/groups",
                POST_GROUPS);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public PostProviderAdapterBase(
            DemactorProviderBase provider) {
        super(
            provider,
            new PostSQLiteAdapter(provider.getContext()));

        this.uriIds.add(POST_ALL);
        this.uriIds.add(POST_ONE);
        this.uriIds.add(POST_OWNER);
        this.uriIds.add(POST_COMMENTS);
        this.uriIds.add(POST_GROUPS);
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
            case POST_ALL:
                result = collection + "post";
                break;
            case POST_ONE:
                result = single + "post";
                break;
            case POST_OWNER:
                result = single + "post";
                break;
            case POST_COMMENTS:
                result = collection + "post";
                break;
            case POST_GROUPS:
                result = collection + "post";
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
            case POST_ONE:
                String id = uri.getPathSegments().get(1);
                selection = PostContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case POST_ALL:
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
            case POST_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(PostContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            POST_URI,
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
        android.database.Cursor postCursor;
        int postId;

        switch (matchedUri) {

            case POST_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case POST_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            case POST_OWNER:
                postCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (postCursor.getCount() > 0) {
                    postCursor.moveToFirst();
                    int ownerId = postCursor.getInt(
                            postCursor.getColumnIndex(
                                    PostContract.COL_OWNER_ID));

                    UserSQLiteAdapter userAdapter = new UserSQLiteAdapter(this.ctx);
                    userAdapter.open(this.getDb());
                    result = userAdapter.query(ownerId);
                }
                break;

            case POST_COMMENTS:
                postId = Integer.parseInt(uri.getPathSegments().get(1));
                CommentSQLiteAdapter commentsAdapter = new CommentSQLiteAdapter(this.ctx);
                commentsAdapter.open(this.getDb());
                result = commentsAdapter.getByPost(postId, CommentContract.ALIASED_COLS, selection, selectionArgs, null);
                break;

            case POST_GROUPS:
                postId = Integer.parseInt(uri.getPathSegments().get(1));
                PosttoGroupSQLiteAdapter groupsAdapter = new PosttoGroupSQLiteAdapter(this.ctx);
                groupsAdapter.open(this.getDb());
                result = groupsAdapter.getByPostInternalId(postId, GroupContract.ALIASED_COLS, selection, selectionArgs, null);
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
            case POST_ONE:
                selectionArgs = new String[1];
                selection = PostContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case POST_ALL:
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
        return POST_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = PostContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    PostContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

