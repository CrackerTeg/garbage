package com.crackerteg.adhellreborn.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.crackerteg.adhellreborn.App;
import com.crackerteg.adhellreborn.db.AppDatabase;
import com.crackerteg.adhellreborn.db.entity.AppInfo;
import com.crackerteg.adhellreborn.utils.AppsListDBInitializer;

import java.util.List;

public class ApplicationsListChangedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AsyncTask.execute(() ->
        {
            String packageName = intent.getData().getEncodedSchemeSpecificPart();
            AppDatabase mDb = AppDatabase.getAppDatabase(App.get().getApplicationContext());
            List<AppInfo> packageList = mDb.applicationInfoDao().getAll();
            if (packageList.size() == 0) return;
            if (intent.getAction().equalsIgnoreCase("android.intent.action.PACKAGE_ADDED"))
                mDb.applicationInfoDao().insert(AppsListDBInitializer.getInstance()
                        .generateAppInfo(context.getPackageManager(), packageName));
            else mDb.applicationInfoDao().deleteAppInfoByPackageName(packageName);
        });
    }
}
