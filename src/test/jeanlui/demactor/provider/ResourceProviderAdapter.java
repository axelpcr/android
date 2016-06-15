/**************************************************************************
 * ResourceProviderAdapter.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.provider;

import com.jeanlui.demactor.provider.base.ResourceProviderAdapterBase;
import com.jeanlui.demactor.provider.base.DemactorProviderBase;

/**
 * ResourceProviderAdapter.
 *
 * A provider adapter is used to separate your provider requests for
 * each entity of your application.
 * You will find here basic methods for database manipulation.
 * Feel free to override any method here.
 */
public class ResourceProviderAdapter
                    extends ResourceProviderAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public ResourceProviderAdapter(
            final DemactorProviderBase provider) {
        super(provider);
    }
}

