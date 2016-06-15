
/**************************************************************************
 * ClientSQLiteAdapterBase.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.data.base;

import java.util.ArrayList;
import android.content.ContentValues;


import android.database.sqlite.SQLiteDatabase;


import com.google.common.base.Strings;
import com.google.common.collect.ObjectArrays;
import com.jeanlui.demactor.data.SQLiteAdapter;
import com.jeanlui.demactor.data.ClientSQLiteAdapter;
import com.jeanlui.demactor.data.UserSQLiteAdapter;
import com.jeanlui.demactor.data.UserGroupSQLiteAdapter;
import com.jeanlui.demactor.data.UsertoUserSQLiteAdapter;
import com.jeanlui.demactor.provider.contract.ClientContract;
import com.jeanlui.demactor.provider.contract.UserContract;
import com.jeanlui.demactor.provider.contract.UserGroupContract;
import com.jeanlui.demactor.provider.contract.UsertoUserContract;
import com.jeanlui.demactor.entity.Client;
import com.jeanlui.demactor.entity.UserGroup;
import com.jeanlui.demactor.entity.User;
import com.jeanlui.demactor.entity.Title;


import com.jeanlui.demactor.DemactorApplication;


import com.jeanlui.demactor.harmony.util.DatabaseUtil;

/** Client adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit ClientAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class ClientSQLiteAdapterBase
                        extends SQLiteAdapter<Client> {

    /** TAG for debug purpose. */
    protected static final String TAG = "ClientDBAdapter";

    /** Mother Adapter. */
    private final UserSQLiteAdapter motherAdapter;

    /**
     * Get the table name used in DB for your Client entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return ClientContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your Client entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = ClientContract.TABLE_NAME;
        result += " INNER JOIN ";
        result += this.motherAdapter.getJoinedTableName();
        result += " ON ";
        result += ClientContract.ALIASED_COL_ID + " = " + UserContract.ALIASED_COL_ID;
        return result;
    }

    /**
     * Get the column names from the Client entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return ClientContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + ClientContract.TABLE_NAME    + " ("
        
         + ClientContract.COL_ADRESS    + " VARCHAR NOT NULL,"
         + UserContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT"

        
        + ", FOREIGN KEY (" + UserContract.COL_ID + ") REFERENCES " + UserContract.TABLE_NAME + "(" + UserContract.COL_ID + ") ON DELETE CASCADE"
        + ");"
;
    }

    @Override
    public SQLiteDatabase open() {
        SQLiteDatabase db = super.open();
        this.motherAdapter.open(db);
        return db;
    }

    @Override
    public SQLiteDatabase open(SQLiteDatabase db) {
        this.motherAdapter.open(db);
        return super.open(db);
    }
    /**
     * Constructor.
     * @param ctx context
     */
    public ClientSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
        this.motherAdapter = new UserSQLiteAdapter(ctx);
    }

    // Converters

    /**
     * Convert Client entity to Content Values for database.
     * @param item Client entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final Client item) {
        return ClientContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to Client entity.
     * @param cursor android.database.Cursor object
     * @return Client entity
     */
    public Client cursorToItem(final android.database.Cursor cursor) {
        return ClientContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to Client entity.
     * @param cursor android.database.Cursor object
     * @param result Client entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final Client result) {
        ClientContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read Client by id in database.
     *
     * @param id Identify of Client
     * @return Client entity
     */
    public Client getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final Client result = this.cursorToItem(cursor);
        cursor.close();

        if (result.getUserGroup() != null) {
            final UserGroupSQLiteAdapter userGroupAdapter =
                    new UserGroupSQLiteAdapter(this.ctx);
            userGroupAdapter.open(this.mDatabase);

            result.setUserGroup(userGroupAdapter.getByID(
                            result.getUserGroup().getId()));
        }
        UsertoUserSQLiteAdapter usertouserAdapter =
                new UsertoUserSQLiteAdapter(this.ctx);
        usertouserAdapter.open(this.mDatabase);
        android.database.Cursor friendsCursor = usertouserAdapter.getByUserInternalId(
                            result.getId(),
                            UserContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setFriends(new UserSQLiteAdapter(ctx).cursorToItems(friendsCursor));

        friendsCursor.close();
        return result;
    }

    /**
     * Find & read Client by userGroup.
     * @param usergroupId usergroupId
     * @param orderBy Order by string (can be null)
     * @return List of Client entities
     */
     public android.database.Cursor getByUserGroup(final int usergroupId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = UserContract.COL_USERGROUP_ID + "= ?";
        String idSelectionArgs = String.valueOf(usergroupId);
        if (!Strings.isNullOrEmpty(selection)) {
            selection += " AND " + idSelection;
            selectionArgs = ObjectArrays.concat(selectionArgs, idSelectionArgs);
        } else {
            selection = idSelection;
            selectionArgs = new String[]{idSelectionArgs};
        }
        final android.database.Cursor cursor = this.query(
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);

        return cursor;
     }

    /**
     * Read All Clients entities.
     *
     * @return List of Client entities
     */
    public ArrayList<Client> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<Client> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a Client entity into database.
     *
     * @param item The Client entity to persist
     * @return Id of the Client entity
     */
    public long insert(final Client item) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + ClientContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                ClientContract.itemToContentValues(item);
        values.remove(UserContract.COL_ID);
        this.motherAdapter.open(this.mDatabase);
        final ContentValues currentValues =
                DatabaseUtil.extractContentValues(values, ClientContract.COLS);
        values.put(UserContract.COL_ID,
                currentValues.getAsString(
                        UserContract.COL_ID));
        int insertResult = (int) this.motherAdapter.insert(null, values);
            currentValues.put(UserContract.COL_ID, insertResult);
        if (values.size() != 0) {
            this.insert(
                    null,
                    currentValues);
        } else {
            this.insert(
                    UserContract.COL_ID,
                    currentValues);
        }
        item.setId(insertResult);
        if (item.getFriends() != null) {
            UsertoUserSQLiteAdapterBase friendsAdapter =
                    new UsertoUserSQLiteAdapter(this.ctx);
            friendsAdapter.open(this.mDatabase);
            for (User i : item.getFriends()) {
                friendsAdapter.insert(insertResult,
                        i.getId());
            }
        }
        return insertResult;
    }

    /**
     * Either insert or update a Client entity into database whether.
     * it already exists or not.
     *
     * @param item The Client entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final Client item) {
        int result = 0;
        if (this.getByID(item.getId()) != null) {
            // Item already exists => update it
            result = this.update(item);
        } else {
            // Item doesn't exist => create it
            final long id = this.insert(item);
            if (id != 0) {
                result = 1;
            }
        }

        return result;
    }

    /**
     * Update a Client entity into database.
     *
     * @param item The Client entity to persist
     * @return count of updated entities
     */
    public int update(final Client item) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + ClientContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                ClientContract.itemToContentValues(item);
        final String whereClause =
                 UserContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        final ContentValues currentValues =
                DatabaseUtil.extractContentValues(values, ClientContract.COLS);
        this.motherAdapter.update(values, whereClause, whereArgs);

        return this.update(
                currentValues,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a Client entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + ClientContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                UserContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param client The entity to delete
     * @return count of updated entities
     */
    public int delete(final Client client) {
        return this.remove(client.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the Client corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                UserContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                ClientContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a Client entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = UserContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                ClientContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

