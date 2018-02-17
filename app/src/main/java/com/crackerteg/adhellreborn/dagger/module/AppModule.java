package com.crackerteg.adhellreborn.dagger.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import com.crackerteg.adhellreborn.dagger.scope.AdhellApplicationScope;
import com.crackerteg.adhellreborn.db.AppDatabase;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private static final String APP_GENERAL_PREFERENCES = "app_general_preferences";

    private Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @AdhellApplicationScope
    Application providesApplication() {
        return mApplication;
    }

    @Provides
    @AdhellApplicationScope
    Context providesContext() {
        return mApplication.getApplicationContext();
    }

    @Provides
    @AdhellApplicationScope
    AppDatabase providesAppDatabase() {
        return AppDatabase.getAppDatabase(mApplication.getApplicationContext());
    }

    @Provides
    @AdhellApplicationScope
    PackageManager providesPackageManager() {
        return mApplication.getPackageManager();
    }

    @Provides
    @AdhellApplicationScope
    SharedPreferences providesSharedPreferences(Context context) {
        return context.getSharedPreferences(APP_GENERAL_PREFERENCES, Context.MODE_PRIVATE);
    }
}
