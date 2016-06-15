/**************************************************************************
 * PoneyProviderAdapterBase.java, demactor Android
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



import com.jeanlui.demactor.entity.Poney;
import com.jeanlui.demactor.provider.ProviderAdapter;
import com.jeanlui.demactor.provider.DemactorProvider;
import com.jeanlui.demactor.provider.contract.PoneyContract;
import com.jeanlui.demactor.provider.contract.JockeyContract;
import com.jeanlui.demactor.data.PoneySQLiteAdapter;
import com.jeanlui.demactor.data.PoneytoJockeySQLiteAdapter;
import com.jeanlui.demactor.data.JockeySQLiteAdapter;
import com.jeanlui.demactor.data.ScoreSQLiteAdapter;

/**
 * PoneyProviderAdapterBase.
 */
public abstract class PoneyProviderAdapterBase
                extends ProviderAdapter<Poney> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PoneyProviderAdapter";

    /** PONEY_URI. */
    public      static Uri PONEY_URI;

    /** poney type. */
    protected static final String poneyType =
            "poney";

    /** PONEY_ALL. */
    protected static final int PONEY_ALL =
            77297443;
    /** PONEY_ONE. */
    protected static final int PONEY_ONE =
            77297444;

    /** PONEY_JOCKGFHJEYS1. */
    protected static final int PONEY_JOCKGFHJEYS1 =
            77297445;
    /** PONEY_SCORVBNBE1. */
    protected static final int PONEY_SCORVBNBE1 =
            77297446;

    /**
     * Static constructor.
     */
    static {
        PONEY_URI =
                DemactorProvider.generateUri(
                        poneyType);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                poneyType,
                PONEY_ALL);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                poneyType + "/#",
                PONEY_ONE);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                poneyType + "/#" + "/jockgfhjeys1",
                PONEY_JOCKGFHJEYS1);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                poneyType + "/#" + "/scorvbnbe1",
                PONEY_SCORVBNBE1);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public PoneyProviderAdapterBase(
            DemactorProviderBase provider) {
        super(
            provider,
            new PoneySQLiteAdapter(provider.getContext()));

        this.uriIds.add(PONEY_ALL);
        this.uriIds.add(PONEY_ONE);
        this.uriIds.add(PONEY_JOCKGFHJEYS1);
        this.uriIds.add(PONEY_SCORVBNBE1);
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
            case PONEY_ALL:
                result = collection + "poney";
                break;
            case PONEY_ONE:
                result = single + "poney";
                break;
            case PONEY_JOCKGFHJEYS1:
                result = collection + "poney";
                break;
            case PONEY_SCORVBNBE1:
                result = single + "poney";
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
            case PONEY_ONE:
                String idlioEm1 = uri.getPathSegments().get(1);
                selection = PoneyContract.COL_IDLIOEM1
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = idlioEm1;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case PONEY_ALL:
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
            case PONEY_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(PoneyContract.COL_IDLIOEM1, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            PONEY_URI,
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
        android.database.Cursor poneyCursor;
        int poneyIdlioEm1;

        switch (matchedUri) {

            case PONEY_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case PONEY_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            case PONEY_JOCKGFHJEYS1:
                poneyIdlioEm1 = Integer.parseInt(uri.getPathSegments().get(1));
                PoneytoJockeySQLiteAdapter jockgFhjeys1Adapter = new PoneytoJockeySQLiteAdapter(this.ctx);
                jockgFhjeys1Adapter.open(this.getDb());
                result = jockgFhjeys1Adapter.getByJockeyDF1(poneyIdlioEm1, JockeyContract.ALIASED_COLS, selection, selectionArgs, null);
                break;

            case PONEY_SCORVBNBE1:
                poneyCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (poneyCursor.getCount() > 0) {
                    poneyCursor.moveToFirst();
                    int scorvbnBe1IdFD1 = poneyCursor.getInt(
                            poneyCursor.getColumnIndex(
                                    PoneyContract.COL_SCORVBNBE1_IDFD1));

                    ScoreSQLiteAdapter scoreAdapter = new ScoreSQLiteAdapter(this.ctx);
                    scoreAdapter.open(this.getDb());
                    result = scoreAdapter.query(scorvbnBe1IdFD1);
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
            case PONEY_ONE:
                selectionArgs = new String[1];
                selection = PoneyContract.COL_IDLIOEM1 + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case PONEY_ALL:
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
        return PONEY_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String idlioEm1) {
        android.database.Cursor result = null;
        String selection = PoneyContract.ALIASED_COL_IDLIOEM1
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = idlioEm1;
        
        

        result = this.adapter.query(
                    PoneyContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

