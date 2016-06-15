/**************************************************************************
 * PoneyProviderAdapter.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 4, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.provider;

import com.jeanlui.demactor.provider.base.PoneyProviderAdapterBase;
import com.jeanlui.demactor.provider.base.DemactorProviderBase;

/**
 * PoneyProviderAdapter.
 *
 * A provider adapter is used to separate your provider requests for
 * each entity of your application.
 * You will find here basic methods for database manipulation.
 * Feel free to override any method here.
 */
public class PoneyProviderAdapter
                    extends PoneyProviderAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public PoneyProviderAdapter(
            final DemactorProviderBase provider) {
        super(provider);
    }
}

