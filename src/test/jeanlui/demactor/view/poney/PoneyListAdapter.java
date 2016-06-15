/**************************************************************************
 * PoneyListAdapter.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 4, 2016
 *
 **************************************************************************/
package com.tactfactory.demact.view.poney;


import com.tactfactory.demact.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tactfactory.demact.entity.Poney;
import com.tactfactory.demact.harmony.view.HarmonyCursorAdapter;
import com.tactfactory.demact.harmony.view.HarmonyViewHolder;
import com.jeanlui.demactor.provider.contract.PoneyContract;
import com.jeanlui.demactor.provider.contract.PoneytoJockeyContract;
import com.jeanlui.demactor.provider.contract.JockeyContract;
import com.jeanlui.demactor.provider.contract.ScoreContract;

/**
 * List adapter for Poney entity.
 */
public class PoneyListAdapter extends HarmonyCursorAdapter<Poney> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public PoneyListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public PoneyListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected Poney cursorToItem(Cursor cursor) {
        return PoneyContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return PoneyContract.COL_IDLIOEM1;
    }
    
    @Override
    protected HarmonyViewHolder<Poney> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<Poney> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_poney);
        }

        /**
         * Populate row with a {@link Poney}.
         *
         * @param model {@link Poney} data
         */
        public void populate(final Poney model) {
            TextView iomAiome1View = (TextView) this.getView().findViewById(
                    R.id.row_poney_iomaiome1);
                    
            TextView scorvbnBe1View = (TextView) this.getView().findViewById(
                    R.id.row_poney_scorvbnbe1);
                    

            if (model.getIomAiome1() != null) {
                iomAiome1View.setText(model.getIomAiome1());
            }
            if (model.getScorvbnBe1() != null) {
                scorvbnBe1View.setText(
                        String.valueOf(model.getScorvbnBe1().getIdFD1()));
            }
        }
    }
}
