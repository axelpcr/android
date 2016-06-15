/**************************************************************************
 * ResourceWebServiceClientAdapter.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 20, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.data;

import android.content.Context;

import com.jeanlui.demactor.data.base.ResourceWebServiceClientAdapterBase;

/**
 * Class for all your ResourceWebServiceClient adapters.
 * You can add your own/customize your methods here.
 */
public class ResourceWebServiceClientAdapter
        extends ResourceWebServiceClientAdapterBase {

    public ResourceWebServiceClientAdapter(Context context, String host,
            Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);
    }
}

