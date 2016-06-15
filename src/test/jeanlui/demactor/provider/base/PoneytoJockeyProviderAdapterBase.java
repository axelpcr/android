/**************************************************************************
 * PoneytoJockeyProviderAdapterBase.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 4, 2016
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
import com.jeanlui.demactor.provider.contract.PoneytoJockeyContract;
import com.jeanlui.demactor.data.PoneytoJockeySQLiteAdapter;
import com.jeanlui.demactor.data.JockeySQLiteAdapter;
import com.jeanlui.demactor.data.PoneySQLiteAdapter;

/**
 * PoneytoJockeyProviderAdapterBase.
 */
public abstract class PoneytoJockeyProviderAdapterBase
                extends ProviderAdapter<Void> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PoneytoJockeyProviderAdapter";

    /** PONEYTOJOCKEY_URI. */
    public      static Uri PONEYTOJOCKEY_URI;

    /** poneytoJockey type. */
    protected static final String poneytoJockeyType =
            "poneytojockey";

    /** PONEYTOJOCKEY_ALL. */
    protected static final int PONEYTOJOCKEY_ALL =
            721610657;
    /** PONEYTOJOCKEY_ONE. */
    protected static final int PONEYTOJOCKEY_ONE =
            721610658;

    /** PONEYTOJOCKEY_PONEYDFD1. */
    protected static final int PONEYTOJOCKEY_PONEYDFD1 =
            721610659;
    /** PONEYTOJOCKEY_DZERZERBCZE. */
    protected static final int PONEYTOJOCKEY_DZERZERBCZE =
            721610660;
    /** PONEYTOJOCKEY_JOCKEYDF1. */
    protected static final int PONEYTOJOCKEY_JOCKEYDF1 =
            721610661;
    /** PONEYTOJOCKEY_JOCKGFHJEYS1. */
    protected static final int PONEYTOJOCKEY_JOCKGFHJEYS1 =
            721610662;

    /**
     * Static constructor.
     */
    static {
        PONEYTOJOCKEY_URI =
                DemactorProvider.generateUri(
                        poneytoJockeyType);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                poneytoJockeyType,
                PONEYTOJOCKEY_ALL);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                poneytoJockeyType + "/#" + "/#" + "/#" + "/#",
                PONEYTOJOCKEY_ONE);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                poneytoJockeyType + "/#" + "/#" + "/#" + "/#" + "/poneydfd1",
                PONEYTOJOCKEY_PONEYDFD1);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                poneytoJockeyType + "/#" + "/#" + "/#" + "/#" + "/dzerzerbcze",
                PONEYTOJOCKEY_DZERZERBCZE);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                poneytoJockeyType + "/#" + "/#" + "/#" + "/#" + "/jockeydf1",
                PONEYTOJOCKEY_JOCKEYDF1);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                poneytoJockeyType + "/#" + "/#" + "/#" + "/#" + "/jockgfhjeys1",
                PONEYTOJOCKEY_JOCKGFHJEYS1);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public PoneytoJockeyProviderAdapterBase(
            DemactorProviderBase provider) {
        super(
            provider,
            new PoneytoJockeySQLiteAdapter(provider.getContext()));

        this.uriIds.add(PONEYTOJOCKEY_ALL);
        this.uriIds.add(PONEYTOJOCKEY_ONE);
        this.uriIds.add(PONEYTOJOCKEY_PONEYDFD1);
        this.uriIds.add(PONEYTOJOCKEY_DZERZERBCZE);
        this.uriIds.add(PONEYTOJOCKEY_JOCKEYDF1);
        this.uriIds.add(PONEYTOJOCKEY_JOCKGFHJEYS1);
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
            case PONEYTOJOCKEY_ONE:
                String PoneyDFd1FbgDFbdf = uri.getPathSegments().get(1);
                String dzerzerBCzeIdlioEm1 = uri.getPathSegments().get(2);
                String JockeyDF1IdlioEm1 = uri.getPathSegments().get(3);
                String jockgFhjeys1FbgDFbdf = uri.getPathSegments().get(4);
                selection = PoneytoJockeyContract.COL_PONEYDFD1_FBGDFBDF
                        + " = ?"
                        + " AND "
                        + PoneytoJockeyContract.COL_DZERZERBCZE_IDLIOEM1
                        + " = ?"
                        + " AND "
                        + PoneytoJockeyContract.COL_JOCKEYDF1_IDLIOEM1
                        + " = ?"
                        + " AND "
                        + PoneytoJockeyContract.COL_JOCKGFHJEYS1_FBGDFBDF
                        + " = ?";
                selectionArgs = new String[4];
                selectionArgs[0] = PoneyDFd1FbgDFbdf;
                selectionArgs[1] = dzerzerBCzeIdlioEm1;
                selectionArgs[2] = JockeyDF1IdlioEm1;
                selectionArgs[3] = jockgFhjeys1FbgDFbdf;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case PONEYTOJOCKEY_ALL:
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
            case PONEYTOJOCKEY_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(PoneytoJockeyContract.COL_PONEYDFD1_FBGDFBDF, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            PONEYTOJOCKEY_URI,
                            values.getAsString(PoneytoJockeyContract.COL_PONEYDFD1_FBGDFBDF)
                            + "/"
                            + values.getAsString(PoneytoJockeyContract.COL_DZERZERBCZE_IDLIOEM1)
                            + "/"
                            + values.getAsString(PoneytoJockeyContract.COL_JOCKEYDF1_IDLIOEM1)
                            + "/"
                            + values.getAsString(PoneytoJockeyContract.COL_JOCKGFHJEYS1_FBGDFBDF));
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
        android.database.Cursor poneytoJockeyCursor;

        switch (matchedUri) {

            case PONEYTOJOCKEY_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case PONEYTOJOCKEY_ONE:
                result = this.queryById(uri.getPathSegments().get(1),
                        uri.getPathSegments().get(2),
                        uri.getPathSegments().get(3),
                        uri.getPathSegments().get(4));
                break;

            case PONEYTOJOCKEY_PONEYDFD1:
                poneytoJockeyCursor = this.queryById(
                        uri.getPathSegments().get(1),
                        uri.getPathSegments().get(2),
                        uri.getPathSegments().get(3),
                        uri.getPathSegments().get(4));

                if (poneytoJockeyCursor.getCount() > 0) {
                    poneytoJockeyCursor.moveToFirst();
                    int PoneyDFd1FbgDFbdf = poneytoJockeyCursor.getInt(
                            poneytoJockeyCursor.getColumnIndex(
                                    PoneytoJockeyContract.COL_PONEYDFD1_FBGDFBDF));

                    JockeySQLiteAdapter jockeyAdapter = new JockeySQLiteAdapter(this.ctx);
                    jockeyAdapter.open(this.getDb());
                    result = jockeyAdapter.query(PoneyDFd1FbgDFbdf);
                }
                break;

            case PONEYTOJOCKEY_DZERZERBCZE:
                poneytoJockeyCursor = this.queryById(
                        uri.getPathSegments().get(1),
                        uri.getPathSegments().get(2),
                        uri.getPathSegments().get(3),
                        uri.getPathSegments().get(4));

                if (poneytoJockeyCursor.getCount() > 0) {
                    poneytoJockeyCursor.moveToFirst();
                    int dzerzerBCzeIdlioEm1 = poneytoJockeyCursor.getInt(
                            poneytoJockeyCursor.getColumnIndex(
                                    PoneytoJockeyContract.COL_DZERZERBCZE_IDLIOEM1));

                    PoneySQLiteAdapter poneyAdapter = new PoneySQLiteAdapter(this.ctx);
                    poneyAdapter.open(this.getDb());
                    result = poneyAdapter.query(dzerzerBCzeIdlioEm1);
                }
                break;

            case PONEYTOJOCKEY_JOCKEYDF1:
                poneytoJockeyCursor = this.queryById(
                        uri.getPathSegments().get(1),
                        uri.getPathSegments().get(2),
                        uri.getPathSegments().get(3),
                        uri.getPathSegments().get(4));

                if (poneytoJockeyCursor.getCount() > 0) {
                    poneytoJockeyCursor.moveToFirst();
                    int JockeyDF1IdlioEm1 = poneytoJockeyCursor.getInt(
                            poneytoJockeyCursor.getColumnIndex(
                                    PoneytoJockeyContract.COL_JOCKEYDF1_IDLIOEM1));

                    PoneySQLiteAdapter poneyAdapter = new PoneySQLiteAdapter(this.ctx);
                    poneyAdapter.open(this.getDb());
                    result = poneyAdapter.query(JockeyDF1IdlioEm1);
                }
                break;

            case PONEYTOJOCKEY_JOCKGFHJEYS1:
                poneytoJockeyCursor = this.queryById(
                        uri.getPathSegments().get(1),
                        uri.getPathSegments().get(2),
                        uri.getPathSegments().get(3),
                        uri.getPathSegments().get(4));

                if (poneytoJockeyCursor.getCount() > 0) {
                    poneytoJockeyCursor.moveToFirst();
                    int jockgFhjeys1FbgDFbdf = poneytoJockeyCursor.getInt(
                            poneytoJockeyCursor.getColumnIndex(
                                    PoneytoJockeyContract.COL_JOCKGFHJEYS1_FBGDFBDF));

                    JockeySQLiteAdapter jockeyAdapter = new JockeySQLiteAdapter(this.ctx);
                    jockeyAdapter.open(this.getDb());
                    result = jockeyAdapter.query(jockgFhjeys1FbgDFbdf);
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
            case PONEYTOJOCKEY_ONE:
                selectionArgs = new String[4];
                selection = PoneytoJockeyContract.COL_PONEYDFD1_FBGDFBDF + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                selection += " AND " + PoneytoJockeyContract.COL_DZERZERBCZE_IDLIOEM1 + " = ?";
                selectionArgs[1] = uri.getPathSegments().get(2);
                selection += " AND " + PoneytoJockeyContract.COL_JOCKEYDF1_IDLIOEM1 + " = ?";
                selectionArgs[2] = uri.getPathSegments().get(3);
                selection += " AND " + PoneytoJockeyContract.COL_JOCKGFHJEYS1_FBGDFBDF + " = ?";
                selectionArgs[3] = uri.getPathSegments().get(4);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case PONEYTOJOCKEY_ALL:
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
        return PONEYTOJOCKEY_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String PoneyDFd1FbgDFbdf, String dzerzerBCzeIdlioEm1, String JockeyDF1IdlioEm1, String jockgFhjeys1FbgDFbdf) {
        android.database.Cursor result = null;
        String selection = PoneytoJockeyContract.ALIASED_COL_PONEYDFD1_FBGDFBDF
                        + " = ?"
                        + " AND "
                        + PoneytoJockeyContract.ALIASED_COL_DZERZERBCZE_IDLIOEM1
                        + " = ?"
                        + " AND "
                        + PoneytoJockeyContract.ALIASED_COL_JOCKEYDF1_IDLIOEM1
                        + " = ?"
                        + " AND "
                        + PoneytoJockeyContract.ALIASED_COL_JOCKGFHJEYS1_FBGDFBDF
                        + " = ?";

        String[] selectionArgs = new String[4];
        selectionArgs[0] = PoneyDFd1FbgDFbdf;
        selectionArgs[1] = dzerzerBCzeIdlioEm1;
        selectionArgs[2] = JockeyDF1IdlioEm1;
        selectionArgs[3] = jockgFhjeys1FbgDFbdf;
        
        

        result = this.adapter.query(
                    PoneytoJockeyContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

