/**************************************************************************
 * JockeyListAdapter.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 4, 2016
 *
 **************************************************************************/
package com.tactfactory.demact.view.jockey;


import com.tactfactory.demact.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tactfactory.demact.entity.Jockey;
import com.tactfactory.demact.harmony.view.HarmonyCursorAdapter;
import com.tactfactory.demact.harmony.view.HarmonyViewHolder;
import com.jeanlui.demactor.provider.contract.JockeyContract;
import com.jeanlui.demactor.provider.contract.PoneytoJockeyContract;
import com.jeanlui.demactor.provider.contract.PoneyContract;
import com.jeanlui.demactor.provider.contract.UserContract;

/**
 * List adapter for Jockey entity.
 */
public class JockeyListAdapter extends HarmonyCursorAdapter<Jockey> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public JockeyListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public JockeyListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected Jockey cursorToItem(Cursor cursor) {
        return JockeyContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return JockeyContract.COL_FBGDFBDF;
    }
    
    @Override
    protected HarmonyViewHolder<Jockey> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<Jockey> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_jockey);
        }

        /**
         * Populate row with a {@link Jockey}.
         *
         * @param model {@link Jockey} data
         */
        public void populate(final Jockey model) {
            TextView dfdfgdDDfgdfgView = (TextView) this.getView().findViewById(
                    R.id.row_jockey_dfdfgdddfgdfg);
                    
            TextView dfgdfgdfgdfFgView = (TextView) this.getView().findViewById(
                    R.id.row_jockey_dfgdfgdfgdffg);
                    
            TextView iuytrezBaView = (TextView) this.getView().findViewById(
                    R.id.row_jockey_iuytrezba);
                    

            if (model.getDfdfgdDDfgdfg() != null) {
                dfdfgdDDfgdfgView.setText(model.getDfdfgdDDfgdfg());
            }
            if (model.getDfgdfgdfgdfFg() != null) {
                dfgdfgdfgdfFgView.setText(model.getDfgdfgdfgdfFg());
            }
            if (model.getIuytrezBa() != null) {
                iuytrezBaView.setText(
                        String.valueOf(model.getIuytrezBa().getId1HNY()));
            }
        }
    }
}
