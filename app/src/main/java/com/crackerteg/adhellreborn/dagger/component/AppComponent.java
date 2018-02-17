package com.crackerteg.adhellreborn.dagger.component;

import com.crackerteg.adhellreborn.adapter.AdhellPermissionInAppsAdapter;
import com.crackerteg.adhellreborn.blocker.ContentBlocker20;
import com.crackerteg.adhellreborn.blocker.ContentBlocker56;
import com.crackerteg.adhellreborn.dagger.module.AdminModule;
import com.crackerteg.adhellreborn.dagger.module.AppModule;
import com.crackerteg.adhellreborn.dagger.module.EnterpriseModule;
import com.crackerteg.adhellreborn.dagger.module.NetworkModule;
import com.crackerteg.adhellreborn.dagger.scope.AdhellApplicationScope;
import com.crackerteg.adhellreborn.fragments.AdhellPermissionInAppsFragment;
import com.crackerteg.adhellreborn.fragments.BlockedUrlSettingFragment;
import com.crackerteg.adhellreborn.fragments.BlockerFragment;
import com.crackerteg.adhellreborn.fragments.PackageDisablerFragment;
import com.crackerteg.adhellreborn.service.BlockedDomainService;
import com.crackerteg.adhellreborn.utils.AdhellAppIntegrity;
import com.crackerteg.adhellreborn.utils.AppsListDBInitializer;
import com.crackerteg.adhellreborn.utils.DeviceAdminInteractor;
import com.crackerteg.adhellreborn.viewmodel.AdhellWhitelistAppsViewModel;
import com.crackerteg.adhellreborn.viewmodel.SharedAppPermissionViewModel;

import dagger.Component;

@AdhellApplicationScope
@Component(modules = {AppModule.class, AdminModule.class, EnterpriseModule.class, NetworkModule.class})
public interface AppComponent {
    void inject(DeviceAdminInteractor deviceAdminInteractor);

    void inject(ContentBlocker56 contentBlocker56);

    void inject(ContentBlocker20 contentBlocker20);

    void inject(BlockedDomainService blockedDomainService);

    void inject(BlockedUrlSettingFragment blockedUrlSettingFragment);

    void inject(PackageDisablerFragment packageDisablerFragment);

    void inject(AdhellWhitelistAppsViewModel adhellWhitelistAppsViewModel);

    void inject(SharedAppPermissionViewModel sharedAppPermissionViewModel);

    void inject(AdhellPermissionInAppsAdapter adhellPermissionInAppsAdapter);

    void inject(AppsListDBInitializer appsListDBInitializer);

    void inject(BlockerFragment blockerFragment);

    void inject(AdhellAppIntegrity adhellAppIntegrity);

}
