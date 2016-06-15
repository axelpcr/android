/**************************************************************************
 * CacheProgressImageLoaderListener.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 20, 2016
 *
 **************************************************************************/

package com.jeanlui.demactor.harmony.util;

import android.graphics.Bitmap;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.jeanlui.demactor.entity.base.EntityResourceBase;
import com.jeanlui.demactor.harmony.widget.ProgressImageLoaderListener;


public class CacheProgressImageLoaderListener extends ProgressImageLoaderListener {
    /**
     * Container for the path.
     */
    private EntityResourceBase container;

    /**
     * Constructor.
     * @param container
     */
    public CacheProgressImageLoaderListener(EntityResourceBase container) {
        this.container = container;
    }

    @Override
    public void onLoadingStarted(String imageUri, View view) {
    }

    @Override
    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
        super.onLoadingComplete(imageUri, view, loadedImage);

        String path = ImageLoader.getInstance().getDiscCache().get(imageUri).getAbsolutePath();

        this.container.setLocalPath(path);
        this.container.setPath(path);
    }
}