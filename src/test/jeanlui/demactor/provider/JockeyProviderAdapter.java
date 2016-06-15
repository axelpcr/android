/**************************************************************************
 * JockeyProviderAdapter.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 4, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.provider;

import com.jeanlui.demactor.provider.base.JockeyProviderAdapterBase;
import com.jeanlui.demactor.provider.base.DemactorProviderBase;

/**
 * JockeyProviderAdapter.
 *
 * A provider adapter is used to separate your provider requests for
 * each entity of your application.
 * You will find here basic methods for database manipulation.
 * Feel free to override any method here.
 */
public class JockeyProviderAdapter
                    extends JockeyProviderAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public JockeyProviderAdapter(
            final DemactorProviderBase provider) {
        super(provider);
    }
}

