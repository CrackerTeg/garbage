package com.crackerteg.adhellreborn.db;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.crackerteg.adhellreborn.db.dao.AppInfoDao;
import com.crackerteg.adhellreborn.db.dao.AppPermissionDao;
import com.crackerteg.adhellreborn.db.dao.BlockUrlDao;
import com.crackerteg.adhellreborn.db.dao.BlockUrlProviderDao;
import com.crackerteg.adhellreborn.db.dao.DisabledPackageDao;
import com.crackerteg.adhellreborn.db.dao.FirewallWhitelistedPackageDao;
import com.crackerteg.adhellreborn.db.dao.PolicyPackageDao;
import com.crackerteg.adhellreborn.db.dao.ReportBlockedUrlDao;
import com.crackerteg.adhellreborn.db.dao.UserBlockUrlDao;
import com.crackerteg.adhellreborn.db.dao.WhiteUrlDao;
import com.crackerteg.adhellreborn.db.entity.AppInfo;
import com.crackerteg.adhellreborn.db.entity.AppPermission;
import com.crackerteg.adhellreborn.db.entity.BlockUrl;
import com.crackerteg.adhellreborn.db.entity.BlockUrlProvider;
import com.crackerteg.adhellreborn.db.entity.DisabledPackage;
import com.crackerteg.adhellreborn.db.entity.FirewallWhitelistedPackage;
import com.crackerteg.adhellreborn.db.entity.PolicyPackage;
import com.crackerteg.adhellreborn.db.entity.ReportBlockedUrl;
import com.crackerteg.adhellreborn.db.entity.UserBlockUrl;
import com.crackerteg.adhellreborn.db.entity.WhiteUrl;
import com.crackerteg.adhellreborn.db.migration.Migration_14_15;
import com.crackerteg.adhellreborn.db.migration.Migration_15_16;
import com.crackerteg.adhellreborn.db.migration.Migration_16_17;
import com.crackerteg.adhellreborn.db.migration.Migration_17_18;
import com.crackerteg.adhellreborn.db.migration.Migration_18_19;
import com.crackerteg.adhellreborn.db.migration.Migration_19_20;
import com.crackerteg.adhellreborn.db.migration.Migration_20_21;
import com.crackerteg.adhellreborn.db.migration.Migration_21_22;

@Database(entities = {
        AppInfo.class,
        AppPermission.class,
        BlockUrl.class,
        BlockUrlProvider.class,
        DisabledPackage.class,
        FirewallWhitelistedPackage.class,
        PolicyPackage.class,
        ReportBlockedUrl.class,
        UserBlockUrl.class,
        WhiteUrl.class
}, version = 22)
public abstract class AppDatabase extends RoomDatabase {
    private static final Migration MIGRATION_14_15 = new Migration_14_15(14, 15);
    private static final Migration MIGRATION_15_16 = new Migration_15_16(15, 16);
    private static final Migration MIGRATION_16_17 = new Migration_16_17(16, 17);
    private static final Migration MIGRATION_17_18 = new Migration_17_18(17, 18);
    private static final Migration MIGRATION_18_19 = new Migration_18_19(18, 19);
    private static final Migration MIGRATION_19_20 = new Migration_19_20(19, 20);
    private static final Migration MIGRATION_20_21 = new Migration_20_21(20, 21);
    private static final Migration MIGRATION_21_22 = new Migration_21_22(21, 22);
    private static AppDatabase INSTANCE;

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "adhell-database")
                            .addMigrations(MIGRATION_14_15)
                            .addMigrations(MIGRATION_15_16)
                            .addMigrations(MIGRATION_16_17)
                            .addMigrations(MIGRATION_17_18)
                            .addMigrations(MIGRATION_18_19)
                            .addMigrations(MIGRATION_19_20)
                            .addMigrations(MIGRATION_20_21)
                            .addMigrations(MIGRATION_21_22)
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public abstract BlockUrlDao blockUrlDao();

    public abstract BlockUrlProviderDao blockUrlProviderDao();

    public abstract ReportBlockedUrlDao reportBlockedUrlDao();

    public abstract AppInfoDao applicationInfoDao();

    public abstract WhiteUrlDao whiteUrlDao();

    public abstract UserBlockUrlDao userBlockUrlDao();

    public abstract PolicyPackageDao policyPackageDao();

    public abstract DisabledPackageDao disabledPackageDao();

    public abstract FirewallWhitelistedPackageDao firewallWhitelistedPackageDao();

    public abstract AppPermissionDao appPermissionDao();

}