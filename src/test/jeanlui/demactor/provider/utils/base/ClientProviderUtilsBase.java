/**************************************************************************
 * ClientProviderUtilsBase.java, demactor Android
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
import com.jeanlui.demactor.data.UserSQLiteAdapter;
import com.jeanlui.demactor.entity.Client;
import com.jeanlui.demactor.entity.UserGroup;
import com.jeanlui.demactor.entity.User;

import com.jeanlui.demactor.provider.ClientProviderAdapter;
import com.jeanlui.demactor.provider.UserGroupProviderAdapter;
import com.jeanlui.demactor.provider.UsertoUserProviderAdapter;
import com.jeanlui.demactor.provider.UserProviderAdapter;
import com.jeanlui.demactor.provider.DemactorProvider;
import com.jeanlui.demactor.provider.contract.ClientContract;
import com.jeanlui.demactor.provider.contract.UserContract;
import com.jeanlui.demactor.provider.contract.UserGroupContract;
import com.jeanlui.demactor.provider.contract.UsertoUserContract;

/**
 * Client Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class ClientProviderUtilsBase
            extends ProviderUtils<Client> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "ClientProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public ClientProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final Client item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = ClientContract.itemToContentValues(item);
        itemValues.remove(UserContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                ClientProviderAdapter.CLIENT_URI)
                        .withValues(itemValues)
                        .build());

        if (item.getFriends() != null && item.getFriends().size() > 0) {
            for (User user : item.getFriends()) {
                ContentValues userValues = new ContentValues();
                userValues.put(
                        UsertoUserContract.COL_FRIENDS_ID,
                        user.getId());

                operations.add(ContentProviderOperation.newInsert(
                    UsertoUserProviderAdapter.USERTOUSER_URI)
                        .withValues(userValues)
                        .withValueBackReference(
                                UsertoUserContract.COL_USERINTERNALID_ID,
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
     * @param item Client
     * @return number of row affected
     */
    public int delete(final Client item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = ClientProviderAdapter.CLIENT_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return Client
     */
    public Client query(final Client item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return Client
     */
    public Client query(final int id) {
        Client result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(UserContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            ClientProviderAdapter.CLIENT_URI,
            ClientContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = ClientContract.cursorToItem(cursor);

            if (result.getUserGroup() != null) {
                result.setUserGroup(
                    this.getAssociateUserGroup(result));
            }
            result.setFriends(
                this.getAssociateFriends(result));
        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<Client>
     */
    public ArrayList<Client> queryAll() {
        ArrayList<Client> result =
                    new ArrayList<Client>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                ClientProviderAdapter.CLIENT_URI,
                ClientContract.ALIASED_COLS,
                null,
                null,
                null);

        result = ClientContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<Client>
     */
    public ArrayList<Client> query(CriteriaExpression expression) {
        ArrayList<Client> result =
                    new ArrayList<Client>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                ClientProviderAdapter.CLIENT_URI,
                ClientContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = ClientContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item Client
     
     * @return number of rows updated
     */
    public int update(final Client item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = ClientContract.itemToContentValues(
                item);

        Uri uri = ClientProviderAdapter.CLIENT_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());


        operations.add(ContentProviderOperation.newDelete(UsertoUserProviderAdapter.USERTOUSER_URI)
                .withSelection(UsertoUserContract.COL_USERINTERNALID_ID + "= ?",
                                new String[]{String.valueOf(item.getId())})
                .build());

        for (User user : item.getFriends()) {
            ContentValues userValues = new ContentValues();
            userValues.put(UsertoUserContract.COL_FRIENDS_ID,
                    user.getId());
            userValues.put(UsertoUserContract.COL_USERINTERNALID_ID,
                    item.getId());

            operations.add(ContentProviderOperation.newInsert(UsertoUserProviderAdapter.USERTOUSER_URI)
                    .withValues(userValues)
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
     * Get associate UserGroup.
     * @param item Client
     * @return UserGroup
     */
    public UserGroup getAssociateUserGroup(
            final Client item) {
        UserGroup result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor userGroupCursor = prov.query(
                UserGroupProviderAdapter.USERGROUP_URI,
                UserGroupContract.ALIASED_COLS,
                UserGroupContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getUserGroup().getId())},
                null);

        if (userGroupCursor.getCount() > 0) {
            userGroupCursor.moveToFirst();
            result = UserGroupContract.cursorToItem(userGroupCursor);
        } else {
            result = null;
        }
        userGroupCursor.close();

        return result;
    }

    /**
     * Get associate Friends.
     * @param item Client
     * @return User
     */
    public ArrayList<User> getAssociateFriends(
            final Client item) {
        ArrayList<User> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor usertoUserCursor = prov.query(
                UsertoUserProviderAdapter.USERTOUSER_URI,
                UsertoUserContract.ALIASED_COLS,
                UsertoUserContract.ALIASED_COL_USERINTERNALID_ID 
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        if (usertoUserCursor.getCount() > 0) {
            CriteriaExpression userCrits =
                    new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            ArrayValue arrayValue = new ArrayValue();
            inCrit.setKey(UserContract.ALIASED_COL_ID);
            inCrit.setType(Type.IN);
            inCrit.addValue(arrayValue);
            userCrits.add(inCrit);

            while (usertoUserCursor.moveToNext()) {
                int index = usertoUserCursor.getColumnIndex(
                        UsertoUserContract.COL_FRIENDS_ID);
                String userId = usertoUserCursor.getString(index);

                arrayValue.addValue(userId);
            }
            usertoUserCursor.close();
            android.database.Cursor userCursor = prov.query(
                    UserProviderAdapter.USER_URI,
                    UserContract.ALIASED_COLS,
                    userCrits.toSQLiteSelection(),
                    userCrits.toSQLiteSelectionArgs(),
                    null);

            result = UserContract.cursorToItems(userCursor);
            userCursor.close();
        } else {
            result = new ArrayList<User>();
        }

        return result;
    }

}
