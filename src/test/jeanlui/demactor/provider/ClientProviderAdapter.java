/**************************************************************************
 * ClientProviderAdapter.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.provider;

import com.jeanlui.demactor.provider.base.ClientProviderAdapterBase;
import com.jeanlui.demactor.provider.base.DemactorProviderBase;

/**
 * ClientProviderAdapter.
 *
 * A provider adapter is used to separate your provider requests for
 * each entity of your application.
 * You will find here basic methods for database manipulation.
 * Feel free to override any method here.
 */
public class ClientProviderAdapter
                    extends ClientProviderAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public ClientProviderAdapter(
            final DemactorProviderBase provider) {
        super(provider);
    }
}

