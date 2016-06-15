/**************************************************************************
 * CommentProviderAdapter.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.provider;

import com.jeanlui.demactor.provider.base.CommentProviderAdapterBase;
import com.jeanlui.demactor.provider.base.DemactorProviderBase;

/**
 * CommentProviderAdapter.
 *
 * A provider adapter is used to separate your provider requests for
 * each entity of your application.
 * You will find here basic methods for database manipulation.
 * Feel free to override any method here.
 */
public class CommentProviderAdapter
                    extends CommentProviderAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public CommentProviderAdapter(
            final DemactorProviderBase provider) {
        super(provider);
    }
}

