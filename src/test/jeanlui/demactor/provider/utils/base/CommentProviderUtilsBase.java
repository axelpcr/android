/**************************************************************************
 * CommentProviderUtilsBase.java, demactor Android
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

import com.jeanlui.demactor.entity.Comment;
import com.jeanlui.demactor.entity.User;
import com.jeanlui.demactor.entity.Post;
import com.jeanlui.demactor.entity.GroupToComment;

import com.jeanlui.demactor.provider.CommentProviderAdapter;
import com.jeanlui.demactor.provider.UserProviderAdapter;
import com.jeanlui.demactor.provider.PostProviderAdapter;
import com.jeanlui.demactor.provider.GroupToCommentProviderAdapter;
import com.jeanlui.demactor.provider.DemactorProvider;
import com.jeanlui.demactor.provider.contract.CommentContract;
import com.jeanlui.demactor.provider.contract.UserContract;
import com.jeanlui.demactor.provider.contract.PostContract;
import com.jeanlui.demactor.provider.contract.GroupToCommentContract;

/**
 * Comment Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class CommentProviderUtilsBase
            extends ProviderUtils<Comment> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "CommentProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public CommentProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final Comment item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = CommentContract.itemToContentValues(item);
        itemValues.remove(CommentContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                CommentProviderAdapter.COMMENT_URI)
                        .withValues(itemValues)
                        .build());

        if (item.getGroups() != null && item.getGroups().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(GroupToCommentContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getGroups().size(); i++) {
                inValue.addValue(String.valueOf(item.getGroups().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(GroupToCommentProviderAdapter.GROUPTOCOMMENT_URI)
                    .withValueBackReference(
                            GroupToCommentContract
                                    .COL_COMMENTGROUPSINTERNAL_ID,
                            0)
                    .withSelection(
                            crit.toSQLiteSelection(),
                            crit.toSQLiteSelectionArgs())
                    .build());
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
     * @param item Comment
     * @return number of row affected
     */
    public int delete(final Comment item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = CommentProviderAdapter.COMMENT_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return Comment
     */
    public Comment query(final Comment item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return Comment
     */
    public Comment query(final int id) {
        Comment result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(CommentContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            CommentProviderAdapter.COMMENT_URI,
            CommentContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = CommentContract.cursorToItem(cursor);

            if (result.getOwner() != null) {
                result.setOwner(
                    this.getAssociateOwner(result));
            }
            if (result.getPost() != null) {
                result.setPost(
                    this.getAssociatePost(result));
            }
            result.setGroups(
                this.getAssociateGroups(result));
        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<Comment>
     */
    public ArrayList<Comment> queryAll() {
        ArrayList<Comment> result =
                    new ArrayList<Comment>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                CommentProviderAdapter.COMMENT_URI,
                CommentContract.ALIASED_COLS,
                null,
                null,
                null);

        result = CommentContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<Comment>
     */
    public ArrayList<Comment> query(CriteriaExpression expression) {
        ArrayList<Comment> result =
                    new ArrayList<Comment>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                CommentProviderAdapter.COMMENT_URI,
                CommentContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = CommentContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item Comment
     
     * @return number of rows updated
     */
    public int update(final Comment item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = CommentContract.itemToContentValues(
                item);

        Uri uri = CommentProviderAdapter.COMMENT_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());


        if (item.getGroups() != null && item.getGroups().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new groups for Comment
            CriteriaExpression groupsCrit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(GroupToCommentContract.COL_ID);
            crit.addValue(values);
            groupsCrit.add(crit);


            for (GroupToComment groups : item.getGroups()) {
                values.addValue(
                    String.valueOf(groups.getId()));
            }
            selection = groupsCrit.toSQLiteSelection();
            selectionArgs = groupsCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    GroupToCommentProviderAdapter.GROUPTOCOMMENT_URI)
                    .withValue(
                            GroupToCommentContract.COL_COMMENTGROUPSINTERNAL_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated groups
            crit.setType(Type.NOT_IN);
            groupsCrit.add(GroupToCommentContract.COL_COMMENTGROUPSINTERNAL_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    GroupToCommentProviderAdapter.GROUPTOCOMMENT_URI)
                    .withValue(
                            GroupToCommentContract.COL_COMMENTGROUPSINTERNAL_ID,
                            null)
                    .withSelection(
                            groupsCrit.toSQLiteSelection(),
                            groupsCrit.toSQLiteSelectionArgs())
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
     * @param item Comment
     * @return User
     */
    public User getAssociateOwner(
            final Comment item) {
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
     * Get associate Post.
     * @param item Comment
     * @return Post
     */
    public Post getAssociatePost(
            final Comment item) {
        Post result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor postCursor = prov.query(
                PostProviderAdapter.POST_URI,
                PostContract.ALIASED_COLS,
                PostContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getPost().getId())},
                null);

        if (postCursor.getCount() > 0) {
            postCursor.moveToFirst();
            result = PostContract.cursorToItem(postCursor);
        } else {
            result = null;
        }
        postCursor.close();

        return result;
    }

    /**
     * Get associate Groups.
     * @param item Comment
     * @return GroupToComment
     */
    public ArrayList<GroupToComment> getAssociateGroups(
            final Comment item) {
        ArrayList<GroupToComment> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor groupToCommentCursor = prov.query(
                GroupToCommentProviderAdapter.GROUPTOCOMMENT_URI,
                GroupToCommentContract.ALIASED_COLS,
                GroupToCommentContract.ALIASED_COL_COMMENTGROUPSINTERNAL_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = GroupToCommentContract.cursorToItems(
                        groupToCommentCursor);
        groupToCommentCursor.close();

        return result;
    }

}
