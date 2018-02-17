package com.crackerteg.adhellreborn.dagger.module;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;

import com.crackerteg.adhellreborn.dagger.scope.AdhellApplicationScope;
import com.crackerteg.adhellreborn.receiver.CustomDeviceAdminReceiver;

import dagger.Module;
import dagger.Provides;

@Module(includes = {AppModule.class})
public class AdminModule {
    @Provides
    @AdhellApplicationScope
    DevicePolicyManager providesDevicePolicyManager(Context appContext) {
        return (DevicePolicyManager) appContext.getSystemService(Context.DEVICE_POLICY_SERVICE);
    }

    @Provides
    @AdhellApplicationScope
    ComponentName providesComponentName(Context appContext) {
        return new ComponentName(appContext, CustomDeviceAdminReceiver.class);
    }
}
