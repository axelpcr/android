/**************************************************************************
 * GroupToCommentProviderAdapter.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.provider;

import com.jeanlui.demactor.provider.base.GroupToCommentProviderAdapterBase;
import com.jeanlui.demactor.provider.base.DemactorProviderBase;

/**
 * GroupToCommentProviderAdapter.
 *
 * A provider adapter is used to separate your provider requests for
 * each entity of your application.
 * You will find here basic methods for database manipulation.
 * Feel free to override any method here.
 */
public class GroupToCommentProviderAdapter
                    extends GroupToCommentProviderAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public GroupToCommentProviderAdapter(
            final DemactorProviderBase provider) {
        super(provider);
    }
}

