/**************************************************************************
 * CommentProviderAdapterBase.java, demactor Android
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



import com.jeanlui.demactor.entity.Comment;
import com.jeanlui.demactor.provider.ProviderAdapter;
import com.jeanlui.demactor.provider.DemactorProvider;
import com.jeanlui.demactor.provider.contract.CommentContract;
import com.jeanlui.demactor.provider.contract.GroupToCommentContract;
import com.jeanlui.demactor.data.CommentSQLiteAdapter;
import com.jeanlui.demactor.data.UserSQLiteAdapter;
import com.jeanlui.demactor.data.PostSQLiteAdapter;
import com.jeanlui.demactor.data.GroupToCommentSQLiteAdapter;

/**
 * CommentProviderAdapterBase.
 */
public abstract class CommentProviderAdapterBase
                extends ProviderAdapter<Comment> {

    /** TAG for debug purpose. */
    protected static final String TAG = "CommentProviderAdapter";

    /** COMMENT_URI. */
    public      static Uri COMMENT_URI;

    /** comment type. */
    protected static final String commentType =
            "comment";

    /** COMMENT_ALL. */
    protected static final int COMMENT_ALL =
            1679915457;
    /** COMMENT_ONE. */
    protected static final int COMMENT_ONE =
            1679915458;

    /** COMMENT_OWNER. */
    protected static final int COMMENT_OWNER =
            1679915459;
    /** COMMENT_POST. */
    protected static final int COMMENT_POST =
            1679915460;
    /** COMMENT_GROUPS. */
    protected static final int COMMENT_GROUPS =
            1679915461;

    /**
     * Static constructor.
     */
    static {
        COMMENT_URI =
                DemactorProvider.generateUri(
                        commentType);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                commentType,
                COMMENT_ALL);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                commentType + "/#",
                COMMENT_ONE);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                commentType + "/#" + "/owner",
                COMMENT_OWNER);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                commentType + "/#" + "/post",
                COMMENT_POST);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                commentType + "/#" + "/groups",
                COMMENT_GROUPS);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public CommentProviderAdapterBase(
            DemactorProviderBase provider) {
        super(
            provider,
            new CommentSQLiteAdapter(provider.getContext()));

        this.uriIds.add(COMMENT_ALL);
        this.uriIds.add(COMMENT_ONE);
        this.uriIds.add(COMMENT_OWNER);
        this.uriIds.add(COMMENT_POST);
        this.uriIds.add(COMMENT_GROUPS);
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
            case COMMENT_ALL:
                result = collection + "comment";
                break;
            case COMMENT_ONE:
                result = single + "comment";
                break;
            case COMMENT_OWNER:
                result = single + "comment";
                break;
            case COMMENT_POST:
                result = single + "comment";
                break;
            case COMMENT_GROUPS:
                result = collection + "comment";
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
            case COMMENT_ONE:
                String id = uri.getPathSegments().get(1);
                selection = CommentContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case COMMENT_ALL:
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
            case COMMENT_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(CommentContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            COMMENT_URI,
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
        android.database.Cursor commentCursor;
        int commentId;

        switch (matchedUri) {

            case COMMENT_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case COMMENT_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            case COMMENT_OWNER:
                commentCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (commentCursor.getCount() > 0) {
                    commentCursor.moveToFirst();
                    int ownerId = commentCursor.getInt(
                            commentCursor.getColumnIndex(
                                    CommentContract.COL_OWNER_ID));

                    UserSQLiteAdapter userAdapter = new UserSQLiteAdapter(this.ctx);
                    userAdapter.open(this.getDb());
                    result = userAdapter.query(ownerId);
                }
                break;

            case COMMENT_POST:
                commentCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (commentCursor.getCount() > 0) {
                    commentCursor.moveToFirst();
                    int postId = commentCursor.getInt(
                            commentCursor.getColumnIndex(
                                    CommentContract.COL_POST_ID));

                    PostSQLiteAdapter postAdapter = new PostSQLiteAdapter(this.ctx);
                    postAdapter.open(this.getDb());
                    result = postAdapter.query(postId);
                }
                break;

            case COMMENT_GROUPS:
                commentId = Integer.parseInt(uri.getPathSegments().get(1));
                GroupToCommentSQLiteAdapter groupsAdapter = new GroupToCommentSQLiteAdapter(this.ctx);
                groupsAdapter.open(this.getDb());
                result = groupsAdapter.getByCommentgroupsInternal(commentId, GroupToCommentContract.ALIASED_COLS, selection, selectionArgs, null);
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
            case COMMENT_ONE:
                selectionArgs = new String[1];
                selection = CommentContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case COMMENT_ALL:
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
        return COMMENT_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = CommentContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    CommentContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

