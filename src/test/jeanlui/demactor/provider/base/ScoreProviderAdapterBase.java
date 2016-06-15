/**************************************************************************
 * ScoreProviderAdapterBase.java, demactor Android
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



import com.jeanlui.demactor.entity.Score;
import com.jeanlui.demactor.provider.ProviderAdapter;
import com.jeanlui.demactor.provider.DemactorProvider;
import com.jeanlui.demactor.provider.contract.ScoreContract;
import com.jeanlui.demactor.provider.contract.PoneyContract;
import com.jeanlui.demactor.provider.contract.UserContract;
import com.jeanlui.demactor.data.ScoreSQLiteAdapter;
import com.jeanlui.demactor.data.PoneySQLiteAdapter;
import com.jeanlui.demactor.data.UserSQLiteAdapter;

/**
 * ScoreProviderAdapterBase.
 */
public abstract class ScoreProviderAdapterBase
                extends ProviderAdapter<Score> {

    /** TAG for debug purpose. */
    protected static final String TAG = "ScoreProviderAdapter";

    /** SCORE_URI. */
    public      static Uri SCORE_URI;

    /** score type. */
    protected static final String scoreType =
            "score";

    /** SCORE_ALL. */
    protected static final int SCORE_ALL =
            79711858;
    /** SCORE_ONE. */
    protected static final int SCORE_ONE =
            79711859;

    /** SCORE_PONRTYEYS1. */
    protected static final int SCORE_PONRTYEYS1 =
            79711860;
    /** SCORE_USEGHHNRS1. */
    protected static final int SCORE_USEGHHNRS1 =
            79711861;

    /**
     * Static constructor.
     */
    static {
        SCORE_URI =
                DemactorProvider.generateUri(
                        scoreType);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                scoreType,
                SCORE_ALL);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                scoreType + "/#",
                SCORE_ONE);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                scoreType + "/#" + "/ponrtyeys1",
                SCORE_PONRTYEYS1);
        DemactorProvider.getUriMatcher().addURI(
                DemactorProvider.authority,
                scoreType + "/#" + "/useghhnrs1",
                SCORE_USEGHHNRS1);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public ScoreProviderAdapterBase(
            DemactorProviderBase provider) {
        super(
            provider,
            new ScoreSQLiteAdapter(provider.getContext()));

        this.uriIds.add(SCORE_ALL);
        this.uriIds.add(SCORE_ONE);
        this.uriIds.add(SCORE_PONRTYEYS1);
        this.uriIds.add(SCORE_USEGHHNRS1);
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
            case SCORE_ALL:
                result = collection + "score";
                break;
            case SCORE_ONE:
                result = single + "score";
                break;
            case SCORE_PONRTYEYS1:
                result = collection + "score";
                break;
            case SCORE_USEGHHNRS1:
                result = collection + "score";
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
            case SCORE_ONE:
                String idFD1 = uri.getPathSegments().get(1);
                selection = ScoreContract.COL_IDFD1
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = idFD1;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case SCORE_ALL:
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
            case SCORE_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(ScoreContract.COL_IDFD1, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            SCORE_URI,
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
        int scoreIdFD1;

        switch (matchedUri) {

            case SCORE_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case SCORE_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            case SCORE_PONRTYEYS1:
                scoreIdFD1 = Integer.parseInt(uri.getPathSegments().get(1));
                PoneySQLiteAdapter ponRTYeys1Adapter = new PoneySQLiteAdapter(this.ctx);
                ponRTYeys1Adapter.open(this.getDb());
                result = ponRTYeys1Adapter.getByScorvbnBe1(scoreIdFD1, PoneyContract.ALIASED_COLS, selection, selectionArgs, null);
                break;

            case SCORE_USEGHHNRS1:
                scoreIdFD1 = Integer.parseInt(uri.getPathSegments().get(1));
                UserSQLiteAdapter useGHHNrs1Adapter = new UserSQLiteAdapter(this.ctx);
                useGHHNrs1Adapter.open(this.getDb());
                result = useGHHNrs1Adapter.getByScoFGHre1(scoreIdFD1, UserContract.ALIASED_COLS, selection, selectionArgs, null);
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
            case SCORE_ONE:
                selectionArgs = new String[1];
                selection = ScoreContract.COL_IDFD1 + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case SCORE_ALL:
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
        return SCORE_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String idFD1) {
        android.database.Cursor result = null;
        String selection = ScoreContract.ALIASED_COL_IDFD1
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = idFD1;
        
        

        result = this.adapter.query(
                    ScoreContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

