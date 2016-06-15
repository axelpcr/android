/**************************************************************************
 * PostProviderUtilsBase.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.provider.utils.base;

import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;

import android.content.ContentResolver;
import android.content.ContentValues;

import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;


import com.jeanlui.demactor.provider.utils.ProviderUtils;
import com.jeanlui.demactor.criterias.base.Criterion;
import com.jeanlui.demactor.criterias.base.Criterion.Type;
import com.jeanlui.demactor.criterias.base.value.ArrayValue;
import com.jeanlui.demactor.criterias.base.CriteriaExpression;
import com.jeanlui.demactor.criterias.base.CriteriaExpression.GroupType;
import com.jeanlui.demactor.data.GroupSQLiteAdapter;
import com.jeanlui.demactor.entity.Post;
import com.jeanlui.demactor.entity.User;
import com.jeanlui.demactor.entity.Comment;
import com.jeanlui.demactor.entity.Group;

import com.jeanlui.demactor.provider.PostProviderAdapter;
import com.jeanlui.demactor.provider.UserProviderAdapter;
import com.jeanlui.demactor.provider.CommentProviderAdapter;
import com.jeanlui.demactor.provider.PosttoGroupProviderAdapter;
import com.jeanlui.demactor.provider.GroupProviderAdapter;
import com.jeanlui.demactor.provider.DemactorProvider;
import com.jeanlui.demactor.provider.contract.PostContract;
import com.jeanlui.demactor.provider.contract.UserContract;
import com.jeanlui.demactor.provider.contract.CommentContract;
import com.jeanlui.demactor.provider.contract.PosttoGroupContract;
import com.jeanlui.demactor.provider.contract.GroupContract;

/**
 * Post Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class PostProviderUtilsBase
            extends ProviderUtils<Post> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "PostProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public PostProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final Post item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = PostContract.itemToContentValues(item);
        itemValues.remove(PostContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                PostProviderAdapter.POST_URI)
                        .withValues(itemValues)
                        .build());

        if (item.getComments() != null && item.getComments().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(CommentContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getComments().size(); i++) {
                inValue.addValue(String.valueOf(item.getComments().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(CommentProviderAdapter.COMMENT_URI)
                    .withValueBackReference(
                            CommentContract
                                    .COL_POST_ID,
                            0)
                    .withSelection(
                            crit.toSQLiteSelection(),
                            crit.toSQLiteSelectionArgs())
                    .build());
        }
        if (item.getGroups() != null && item.getGroups().size() > 0) {
            for (Group group : item.getGroups()) {
                ContentValues groupValues = new ContentValues();
                groupValues.put(
                        PosttoGroupContract.COL_GROUPS_ID,
                        group.getId());

                operations.add(ContentProviderOperation.newInsert(
                    PosttoGroupProviderAdapter.POSTTOGROUP_URI)
                        .withValues(groupValues)
                        .withValueBackReference(
                                PosttoGroupContract.COL_POSTINTERNALID_ID,
                                0)
                        .build());

            }
        }

        try {
            ContentProviderResult[] results =
                    prov.applyBatch(DemactorProvider.authority, operations);
            if (results[0] != null) {
                result = results[0].uri;
                item.setId(Integer.parseInt(result.getPathSegments().get(1)));
            }
        } catch (RemoteException e) {
            android.util.Log.e(TAG, e.getMessage());
        } catch (OperationApplicationException e) {
            android.util.Log.e(TAG, e.getMessage());
        }

        return result;
    }


    /**
     * Delete from DB.
     * @param item Post
     * @return number of row affected
     */
    public int delete(final Post item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = PostProviderAdapter.POST_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return Post
     */
    public Post query(final Post item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return Post
     */
    public Post query(final int id) {
        Post result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(PostContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            PostProviderAdapter.POST_URI,
            PostContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = PostContract.cursorToItem(cursor);

            if (result.getOwner() != null) {
                result.setOwner(
                    this.getAssociateOwner(result));
            }
            result.setComments(
                this.getAssociateComments(result));
            result.setGroups(
                this.getAssociateGroups(result));
        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<Post>
     */
    public ArrayList<Post> queryAll() {
        ArrayList<Post> result =
                    new ArrayList<Post>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PostProviderAdapter.POST_URI,
                PostContract.ALIASED_COLS,
                null,
                null,
                null);

        result = PostContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<Post>
     */
    public ArrayList<Post> query(CriteriaExpression expression) {
        ArrayList<Post> result =
                    new ArrayList<Post>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PostProviderAdapter.POST_URI,
                PostContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = PostContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item Post
     
     * @return number of rows updated
     */
    public int update(final Post item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = PostContract.itemToContentValues(
                item);

        Uri uri = PostProviderAdapter.POST_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());


        if (item.getComments() != null && item.getComments().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new comments for Post
            CriteriaExpression commentsCrit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(CommentContract.COL_ID);
            crit.addValue(values);
            commentsCrit.add(crit);


            for (Comment comments : item.getComments()) {
                values.addValue(
                    String.valueOf(comments.getId()));
            }
            selection = commentsCrit.toSQLiteSelection();
            selectionArgs = commentsCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    CommentProviderAdapter.COMMENT_URI)
                    .withValue(
                            CommentContract.COL_POST_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated comments
            crit.setType(Type.NOT_IN);
            commentsCrit.add(CommentContract.COL_POST_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    CommentProviderAdapter.COMMENT_URI)
                    .withValue(
                            CommentContract.COL_POST_ID,
                            null)
                    .withSelection(
                            commentsCrit.toSQLiteSelection(),
                            commentsCrit.toSQLiteSelectionArgs())
                    .build());
        }

        operations.add(ContentProviderOperation.newDelete(PosttoGroupProviderAdapter.POSTTOGROUP_URI)
                .withSelection(PosttoGroupContract.COL_POSTINTERNALID_ID + "= ?",
                                new String[]{String.valueOf(item.getId())})
                .build());

        for (Group group : item.getGroups()) {
            ContentValues groupValues = new ContentValues();
            groupValues.put(PosttoGroupContract.COL_GROUPS_ID,
                    group.getId());
            groupValues.put(PosttoGroupContract.COL_POSTINTERNALID_ID,
                    item.getId());

            operations.add(ContentProviderOperation.newInsert(PosttoGroupProviderAdapter.POSTTOGROUP_URI)
                    .withValues(groupValues)
                    .build());
        }

        try {
            ContentProviderResult[] results = prov.applyBatch(DemactorProvider.authority, operations);
            result = results[0].count;
        } catch (RemoteException e) {
            android.util.Log.e(TAG, e.getMessage());
        } catch (OperationApplicationException e) {
            android.util.Log.e(TAG, e.getMessage());
        }

        return result;
    }

    /** Relations operations. */
    /**
     * Get associate Owner.
     * @param item Post
     * @return User
     */
    public User getAssociateOwner(
            final Post item) {
        User result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor userCursor = prov.query(
                UserProviderAdapter.USER_URI,
                UserContract.ALIASED_COLS,
                UserContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getOwner().getId())},
                null);

        if (userCursor.getCount() > 0) {
            userCursor.moveToFirst();
            result = UserContract.cursorToItem(userCursor);
        } else {
            result = null;
        }
        userCursor.close();

        return result;
    }

    /**
     * Get associate Comments.
     * @param item Post
     * @return Comment
     */
    public ArrayList<Comment> getAssociateComments(
            final Post item) {
        ArrayList<Comment> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor commentCursor = prov.query(
                CommentProviderAdapter.COMMENT_URI,
                CommentContract.ALIASED_COLS,
                CommentContract.ALIASED_COL_POST_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = CommentContract.cursorToItems(
                        commentCursor);
        commentCursor.close();

        return result;
    }

    /**
     * Get associate Groups.
     * @param item Post
     * @return Group
     */
    public ArrayList<Group> getAssociateGroups(
            final Post item) {
        ArrayList<Group> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor posttoGroupCursor = prov.query(
                PosttoGroupProviderAdapter.POSTTOGROUP_URI,
                PosttoGroupContract.ALIASED_COLS,
                PosttoGroupContract.ALIASED_COL_POSTINTERNALID_ID 
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        if (posttoGroupCursor.getCount() > 0) {
            CriteriaExpression groupCrits =
                    new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            ArrayValue arrayValue = new ArrayValue();
            inCrit.setKey(GroupContract.ALIASED_COL_ID);
            inCrit.setType(Type.IN);
            inCrit.addValue(arrayValue);
            groupCrits.add(inCrit);

            while (posttoGroupCursor.moveToNext()) {
                int index = posttoGroupCursor.getColumnIndex(
                        PosttoGroupContract.COL_GROUPS_ID);
                String groupId = posttoGroupCursor.getString(index);

                arrayValue.addValue(groupId);
            }
            posttoGroupCursor.close();
            android.database.Cursor groupCursor = prov.query(
                    GroupProviderAdapter.GROUP_URI,
                    GroupContract.ALIASED_COLS,
                    groupCrits.toSQLiteSelection(),
                    groupCrits.toSQLiteSelectionArgs(),
                    null);

            result = GroupContract.cursorToItems(groupCursor);
            groupCursor.close();
        } else {
            result = new ArrayList<Group>();
        }

        return result;
    }

}
