/**************************************************************************
 * TestContextMock.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.test.base;



import com.jeanlui.demactor.provider.DemactorProvider;



import com.jeanlui.demactor.data.DemactorSQLiteOpenHelper;


import android.content.BroadcastReceiver;
import android.content.ContentProvider;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;

import android.test.AndroidTestCase;
import android.test.IsolatedContext;
import android.test.RenamingDelegatingContext;
import android.test.mock.MockContentResolver;


/** android.content.Context mock for tests.<br/>
 * <b><i>This class will be overwrited whenever
 * you regenerate the project with Harmony.</i></b>
 */
public class TestContextMock {
    private final static String CONTEXT_PREFIX = "test.";
    private final static String PROVIDER_AUTHORITY =
                    "com.jeanlui.demactor.provider";
    private final static Class<? extends ContentProvider> PROVIDER_CLASS =
                    DemactorProvider.class;

    private static android.content.Context context = null;
    private AndroidTestCase androidTestCase;
    private android.content.Context baseContext;

    public TestContextMock(AndroidTestCase androidTestCase) {
        this.androidTestCase = androidTestCase;
    }

    /**
     * Get the original context
     * @return unmocked android.content.Context
     */
    protected android.content.Context getBaseContext() {
        return this.baseContext;
    }

    /**
     * Get the mock for ContentResolver
     * @return MockContentResolver
     */
    protected MockContentResolver getMockContentResolver() {
        return new MockContentResolver();
    }

    /**
     * Get the mock for android.content.Context
     * @return MockContext
     */
    protected android.content.Context getMockContext() {
            return this.androidTestCase.getContext();
    }

    /**
     * Initialize the mock android.content.Context
     * @throws Exception
     */
    protected void setMockContext() throws Exception {
        if (this.baseContext == null) {
            this.baseContext = this.androidTestCase.getContext();
        }

        if (context == null) {
            ContentProvider provider = PROVIDER_CLASS.newInstance();
            MockContentResolver resolver = this.getMockContentResolver();

            RenamingDelegatingContext targetContextWrapper
                = new RenamingDelegatingContext(
                    // The context that most methods are delegated to:
                    this.getMockContext(),
                    // The context that file methods are delegated to:
                    this.baseContext,
                    // Prefix database
                    CONTEXT_PREFIX);

            context = new TestContextIsolatedBase(
                    resolver,
                    targetContextWrapper);

            PackageManager packageManager = this.baseContext.getPackageManager();
            ProviderInfo providerInfo = packageManager.resolveContentProvider(
                    DemactorProvider.class.getPackage().getName(), 0);

            provider.attachInfo(context, providerInfo);

            resolver.addProvider(PROVIDER_AUTHORITY, provider);

            // Call INITIALIZE_DATABASE to create adapters and database (if needed)
            provider.call("INITIALIZE_DATABASE", null, null);
        }

        this.androidTestCase.setContext(context);
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        DemactorSQLiteOpenHelper.isJUnit = true;
        this.setMockContext();

    }
}
