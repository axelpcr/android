/**************************************************************************
 * UserGroupProviderAdapter.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.provider;

import com.jeanlui.demactor.provider.base.UserGroupProviderAdapterBase;
import com.jeanlui.demactor.provider.base.DemactorProviderBase;

/**
 * UserGroupProviderAdapter.
 *
 * A provider adapter is used to separate your provider requests for
 * each entity of your application.
 * You will find here basic methods for database manipulation.
 * Feel free to override any method here.
 */
public class UserGroupProviderAdapter
                    extends UserGroupProviderAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public UserGroupProviderAdapter(
            final DemactorProviderBase provider) {
        super(provider);
    }
}

