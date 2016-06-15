
/**************************************************************************
 * PoneytoJockeySQLiteAdapterBase.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 4, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.data.base;

import java.util.ArrayList;
import android.content.ContentValues;


import android.database.sqlite.SQLiteDatabase;


import com.google.common.base.Strings;
import com.google.common.collect.ObjectArrays;
import com.jeanlui.demactor.data.SQLiteAdapter;
import com.jeanlui.demactor.data.PoneytoJockeySQLiteAdapter;
import com.jeanlui.demactor.data.JockeySQLiteAdapter;
import com.jeanlui.demactor.data.PoneySQLiteAdapter;
import com.jeanlui.demactor.provider.contract.PoneytoJockeyContract;
import com.jeanlui.demactor.provider.contract.JockeyContract;
import com.jeanlui.demactor.provider.contract.PoneyContract;
import com.jeanlui.demactor.entity.Jockey;
import com.jeanlui.demactor.entity.Poney;


import com.jeanlui.demactor.DemactorApplication;

import com.jeanlui.demactor.criterias.base.CriteriaExpression;
import com.jeanlui.demactor.criterias.base.Criterion;
import com.jeanlui.demactor.criterias.base.Criterion.Type;
import com.jeanlui.demactor.criterias.base.CriteriaExpression.GroupType;
import com.jeanlui.demactor.criterias.base.value.SelectValue;


/** PoneytoJockey adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit PoneytoJockeyAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class PoneytoJockeySQLiteAdapterBase
                        extends SQLiteAdapter<Void> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PoneytoJockeyDBAdapter";


    /**
     * Get the table name used in DB for your PoneytoJockey entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return PoneytoJockeyContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your PoneytoJockey entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = PoneytoJockeyContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the PoneytoJockey entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return PoneytoJockeyContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + PoneytoJockeyContract.TABLE_NAME    + " ("
        
         + PoneytoJockeyContract.COL_PONEYDFD1_FBGDFBDF    + " INTEGER NOT NULL,"
         + PoneytoJockeyContract.COL_DZERZERBCZE_IDLIOEM1    + " INTEGER NOT NULL,"
         + PoneytoJockeyContract.COL_JOCKEYDF1_IDLIOEM1    + " INTEGER NOT NULL,"
         + PoneytoJockeyContract.COL_JOCKGFHJEYS1_FBGDFBDF    + " INTEGER NOT NULL,"

        
         + "FOREIGN KEY(" + PoneytoJockeyContract.COL_PONEYDFD1_FBGDFBDF + ") REFERENCES " 
             + JockeyContract.TABLE_NAME 
                + " (" + JockeyContract.COL_FBGDFBDF + "),"
         + "FOREIGN KEY(" + PoneytoJockeyContract.COL_DZERZERBCZE_IDLIOEM1 + ") REFERENCES " 
             + PoneyContract.TABLE_NAME 
                + " (" + PoneyContract.COL_IDLIOEM1 + "),"
         + "FOREIGN KEY(" + PoneytoJockeyContract.COL_JOCKEYDF1_IDLIOEM1 + ") REFERENCES " 
             + PoneyContract.TABLE_NAME 
                + " (" + PoneyContract.COL_IDLIOEM1 + "),"
         + "FOREIGN KEY(" + PoneytoJockeyContract.COL_JOCKGFHJEYS1_FBGDFBDF + ") REFERENCES " 
             + JockeyContract.TABLE_NAME 
                + " (" + JockeyContract.COL_FBGDFBDF + ")"
        + ", PRIMARY KEY (" + PoneytoJockeyContract.COL_PONEYDFD1_FBGDFBDF + "," + PoneytoJockeyContract.COL_DZERZERBCZE_IDLIOEM1 + "," + PoneytoJockeyContract.COL_JOCKEYDF1_IDLIOEM1 + "," + PoneytoJockeyContract.COL_JOCKGFHJEYS1_FBGDFBDF + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public PoneytoJockeySQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }


    /**
     * Query the DB to find a PoneytoJockey entity.
     *
     * @param PoneyDFd1 The PoneyDFd1 of the entity to get from the DB
     * @param dzerzerBCze The dzerzerBCze of the entity to get from the DB
     * @param JockeyDF1 The JockeyDF1 of the entity to get from the DB
     * @param jockgFhjeys1 The jockgFhjeys1 of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final Jockey PoneyDFd1,
                final Poney dzerzerBCze,
                final Poney JockeyDF1,
                final Jockey jockgFhjeys1) {

        String selection = PoneytoJockeyContract.ALIASED_COL_PONEYDFD1_FBGDFBDF + " = ?";
        selection += " AND ";
        selection += PoneytoJockeyContract.ALIASED_COL_DZERZERBCZE_IDLIOEM1 + " = ?";
        selection += " AND ";
        selection += PoneytoJockeyContract.ALIASED_COL_JOCKEYDF1_IDLIOEM1 + " = ?";
        selection += " AND ";
        selection += PoneytoJockeyContract.ALIASED_COL_JOCKGFHJEYS1_FBGDFBDF + " = ?";
        

        String[] selectionArgs = new String[4];
        selectionArgs[0] = String.valueOf(PoneyDFd1.getFbgDFbdf());
        selectionArgs[1] = String.valueOf(dzerzerBCze.getIdlioEm1());
        selectionArgs[2] = String.valueOf(JockeyDF1.getIdlioEm1());
        selectionArgs[3] = String.valueOf(jockgFhjeys1.getFbgDFbdf());

        return this.query(
                PoneytoJockeyContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }



    /**
     * Insert a PoneytoJockey entity into database.
     *
     * @param poneydfd1 poneydfd1
     * @param dzerzerbcze dzerzerbcze
     * @return Id of the PoneytoJockey entity
     */
    public long insert(final int poneyDFd1FbgDFbdf,
            final int dzerzerBCzeIdlioEm1) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + PoneytoJockeyContract.TABLE_NAME + ")");
        }

        ContentValues values = new ContentValues();
        values.put(PoneytoJockeyContract.COL_PONEYDFD1_FBGDFBDF,
                poneyDFd1FbgDFbdf);
        values.put(PoneytoJockeyContract.COL_DZERZERBCZE_IDLIOEM1,
                dzerzerBCzeIdlioEm1);

        return this.mDatabase.insert(
                PoneytoJockeyContract.TABLE_NAME,
                null,
                values);
    }


    /**
     * Find & read PoneytoJockey by PoneyDFd1.
     * @param poneydfd1 dzerzerbcze
     * @param orderBy Order by string (can be null)
     * @return ArrayList of Poney matching poneydfd1
     */
    public android.database.Cursor getByPoneyDFd1(
            final int poneyDFd1FbgDFbdf,
            final String[] projection,
            String selection,
            String[] selectionArgs,
            final String orderBy) {

        android.database.Cursor ret = null;
        CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
        crit.add(PoneytoJockeyContract.COL_PONEYDFD1_FBGDFBDF,
                String.valueOf(poneyDFd1FbgDFbdf),
                Type.EQUALS);
        SelectValue value = new SelectValue();
        value.setRefKey(PoneytoJockeyContract.COL_DZERZERBCZE_IDLIOEM1);
        value.setRefTable(PoneytoJockeyContract.TABLE_NAME);
        value.setCriteria(crit);
        CriteriaExpression poneyCrit = new CriteriaExpression(GroupType.AND);
        Criterion poneySelectCrit = new Criterion();
        poneySelectCrit.setKey(PoneyContract.ALIASED_COL_IDLIOEM1);
        poneySelectCrit.setType(Type.IN);
        poneySelectCrit.addValue(value);
        poneyCrit.add(poneySelectCrit);

        if (Strings.isNullOrEmpty(selection)) {
            selection = poneyCrit.toSQLiteSelection();
            selectionArgs = poneyCrit.toSQLiteSelectionArgs();
        } else {
            selection += " AND " + poneyCrit.toSQLiteSelection();
            selectionArgs = ObjectArrays.concat(
                        poneyCrit.toSQLiteSelectionArgs(),
                        selectionArgs,
                        String.class);
        }

        ret = this.mDatabase.query(PoneyContract.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);
        return ret;
    }

    /**
     * Find & read PoneytoJockey by dzerzerBCze.
     * @param dzerzerbcze poneydfd1
     * @param orderBy Order by string (can be null)
     * @return ArrayList of Jockey matching dzerzerbcze
     */
    public android.database.Cursor getByDzerzerBCze(
            final int dzerzerBCzeIdlioEm1,
            final String[] projection,
            String selection,
            String[] selectionArgs,
            final String orderBy) {

        android.database.Cursor ret = null;
        CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
        crit.add(PoneytoJockeyContract.COL_DZERZERBCZE_IDLIOEM1,
                String.valueOf(dzerzerBCzeIdlioEm1),
                Type.EQUALS);
        SelectValue value = new SelectValue();
        value.setRefKey(PoneytoJockeyContract.COL_PONEYDFD1_FBGDFBDF);
        value.setRefTable(PoneytoJockeyContract.TABLE_NAME);
        value.setCriteria(crit);
        CriteriaExpression jockeyCrit = new CriteriaExpression(GroupType.AND);
        Criterion jockeySelectCrit = new Criterion();
        jockeySelectCrit.setKey(JockeyContract.ALIASED_COL_FBGDFBDF);
        jockeySelectCrit.setType(Type.IN);
        jockeySelectCrit.addValue(value);
        jockeyCrit.add(jockeySelectCrit);

        if (Strings.isNullOrEmpty(selection)) {
            selection = jockeyCrit.toSQLiteSelection();
            selectionArgs = jockeyCrit.toSQLiteSelectionArgs();
        } else {
            selection += " AND " + jockeyCrit.toSQLiteSelection();
            selectionArgs = ObjectArrays.concat(
                        jockeyCrit.toSQLiteSelectionArgs(),
                        selectionArgs,
                        String.class);
        }

        ret = this.mDatabase.query(JockeyContract.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);
        return ret;
    }


    @Override
    public Void cursorToItem(android.database.Cursor c) {
        return null;
    }

    @Override
    public ContentValues itemToContentValues(Void item) {
        return null;
    }

    @Override
    public long insert(Void item) {
        return -1;
    }

    @Override
    public int update(Void item) {
        return 0;
    }

    @Override
    public int delete(Void item) {
        return 0;
    }

}

