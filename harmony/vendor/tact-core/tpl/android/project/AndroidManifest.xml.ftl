<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
      package="${project_namespace}"
      android:versionCode="1"
      android:versionName="@string/app_version">
    <uses-sdk
    	android:minSdkVersion="7"
    	android:targetSdkVersion="17" />
    <application
    	android:label="@string/app_name"
    	android:name=".${project_name?cap_first}Application"
    	android:allowBackup="true"
    	android:theme="@style/Theme.Sherlock">
        <activity android:name=".HomeActivity"
                  android:label="@string/app_name">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
    <uses-permission android:name="android.permission.GET_TASKS"  />
</manifest>
