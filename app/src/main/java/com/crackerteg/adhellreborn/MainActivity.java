package com.crackerteg.adhellreborn;

//import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

//import com.crashlytics.android.Crashlytics;
//import com.crashlytics.android.answers.Answers;
import com.crackerteg.adhellreborn.blocker.ContentBlocker;
import com.crackerteg.adhellreborn.blocker.ContentBlocker56;
import com.crackerteg.adhellreborn.blocker.ContentBlocker57;
import com.crackerteg.adhellreborn.dialogfragment.AdhellNotSupportedDialogFragment;
import com.crackerteg.adhellreborn.dialogfragment.AdhellTurnOnDialogFragment;
import com.crackerteg.adhellreborn.dialogfragment.NoInternetConnectionDialogFragment;
import com.crackerteg.adhellreborn.fragments.AdhellNotSupportedFragment;
import com.crackerteg.adhellreborn.fragments.AdhellPermissionInfoFragment;
import com.crackerteg.adhellreborn.fragments.AppSupportFragment;
import com.crackerteg.adhellreborn.fragments.BlockerFragment;
//import com.crackerteg.adhellreborn.fragments.OnlyPremiumFragment;
import com.crackerteg.adhellreborn.fragments.PackageDisablerFragment;
import com.crackerteg.adhellreborn.service.BlockedDomainService;
import com.crackerteg.adhellreborn.utils.AdhellAppIntegrity;
import com.crackerteg.adhellreborn.utils.DeviceAdminInteractor;
import com.crackerteg.adhellreborn.viewmodel.SharedBillingViewModel;
import com.roughike.bottombar.BottomBar;

//import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {
    public static final String ADHELL_STANDARD_PACKAGE = "http://getadhell.com/standard-package.txt";
    private static final String TAG = MainActivity.class.getCanonicalName();
    private static final String BACK_STACK_TAB_TAG = "tab_fragment";
    protected DeviceAdminInteractor mAdminInteractor;
    private FragmentManager fragmentManager;
    private AdhellNotSupportedDialogFragment adhellNotSupportedDialogFragment;
    private AdhellTurnOnDialogFragment adhellTurnOnDialogFragment;
    private NoInternetConnectionDialogFragment noInternetConnectionDialogFragment;
    private SharedBillingViewModel sharedBillingViewModel;
    private BottomBar bottomBar;

    @Override
    public void onBackPressed() {
        int count = fragmentManager.getBackStackEntryCount();
        if (count <= 1) {
            finish();
        } else {
            fragmentManager.popBackStackImmediate();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Fabric.with(this, new Answers(), new Crashlytics());

        fragmentManager = getSupportFragmentManager();
        mAdminInteractor = DeviceAdminInteractor.getInstance();
        adhellNotSupportedDialogFragment = AdhellNotSupportedDialogFragment.newInstance("App not supported");
        if (!mAdminInteractor.isContentBlockerSupported()) {
            Log.i(TAG, "Device not supported");
            return;
        }

        adhellTurnOnDialogFragment = AdhellTurnOnDialogFragment.newInstance("Adhell Turn On");
        noInternetConnectionDialogFragment = NoInternetConnectionDialogFragment.newInstance("No Internet connection");
        adhellNotSupportedDialogFragment.setCancelable(false);
        adhellTurnOnDialogFragment.setCancelable(false);
        noInternetConnectionDialogFragment.setCancelable(false);

        if (!mAdminInteractor.isContentBlockerSupported()) {
            return;
        }

        bottomBar = findViewById(R.id.bottomBar);
        bottomBar.setTabTitleTextAppearance(R.style.bottomBarTextView);
        bottomBar.setOnTabSelectListener(tabId -> {
            if (!mAdminInteractor.isActiveAdmin()) {
                Log.d(TAG, "Admin not active");
                return;
            }

            if (!mAdminInteractor.isKnoxEnbaled()) {
                Log.d(TAG, "Knox disabled");
                return;
            }
            onTabSelected(tabId);
        });

        AsyncTask.execute(() -> {
//        HeartbeatAlarmHelper.scheduleAlarm();
            AdhellAppIntegrity adhellAppIntegrity = new AdhellAppIntegrity();
//            adhellAppIntegrity.check();
            adhellAppIntegrity.checkDefaultPolicyExists();
            adhellAppIntegrity.checkAdhellStandardPackage();
            adhellAppIntegrity.fillPackageDb();
        });
        // com.samsung.android.app.spage
//        sharedBillingViewModel = ViewModelProviders.of(this).get(SharedBillingViewModel.class);
//        sharedBillingViewModel.startBillingConnection();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                fragmentManager.popBackStack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume1");
        if (!mAdminInteractor.isContentBlockerSupported()) {
            Log.i(TAG, "Device not supported");
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, new AdhellNotSupportedFragment());
            fragmentTransaction.commit();
            return;
        }
        showDialog();
        if (!mAdminInteractor.isActiveAdmin()) {
            Log.d(TAG, "Admin not active");
            return;
        }

        if (!mAdminInteractor.isKnoxEnbaled()) {
            Log.d(TAG, "Knox disabled");
            return;
        }
        Log.d(TAG, "Everything is okay");


        ContentBlocker contentBlocker = mAdminInteractor.getContentBlocker();
        if (contentBlocker != null && contentBlocker.isEnabled() && (contentBlocker instanceof ContentBlocker56
                || contentBlocker instanceof ContentBlocker57)) {
            Intent i = new Intent(App.get().getApplicationContext(), BlockedDomainService.class);
            i.putExtra("launchedFrom", "main-activity");
            App.get().getApplicationContext().startService(i);
        }
        Intent intent = getIntent();
        boolean bxIntegration = intent.getBooleanExtra("bxIntegration", false);
        if (bxIntegration) {
            bottomBar.selectTabWithId(R.id.packageDisablerTab);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Destroying activity");
    }

    private void onTabSelected(int tabId) {
        fragmentManager.popBackStack(BACK_STACK_TAB_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        Fragment replacing;
        switch (tabId) {
            case R.id.blockerTab:
                replacing = new BlockerFragment();
                break;
            case R.id.packageDisablerTab:
                replacing = new PackageDisablerFragment();
                break;
//            case R.id.profilesTab:
//                replacing = new ProfilesFragment();
//                break;
            case R.id.appPermissionsTab:
/*                if (sharedBillingViewModel.billingModel.isPremiumLiveData.getValue()) {
                    replacing = new AdhellPermissionInfoFragment();
                } else {
                    replacing = new OnlyPremiumFragment();
                }*/
                replacing = new AdhellPermissionInfoFragment();
                break;
            default:
                replacing = new AppSupportFragment();
        }

        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, replacing)
                .addToBackStack(BACK_STACK_TAB_TAG)
                .commit();
    }

    public void showDialog() {
        if (!(DeviceAdminInteractor.isSamsung() && mAdminInteractor.isKnoxSupported())) {
            Log.i(TAG, "Device not supported");
            if (!adhellNotSupportedDialogFragment.isVisible()) {
                adhellNotSupportedDialogFragment.show(fragmentManager, "dialog_fragment_adhell_not_supported");
            }
            return;
        }
        if (!mAdminInteractor.isActiveAdmin()) {
            Log.d(TAG, "Admin is not active. Request enabling");
            if (!adhellTurnOnDialogFragment.isVisible()) {
                adhellTurnOnDialogFragment.show(fragmentManager, "dialog_fragment_turn_on_adhell");
            }
            return;
        }

        if (!mAdminInteractor.isKnoxEnbaled()) {
            Log.d(TAG, "Knox disabled");
            Log.d(TAG, "Checking if internet connection exists");
            ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            Log.d(TAG, "Is internet connection exists: " + isConnected);
            if (!isConnected) {
                if (!noInternetConnectionDialogFragment.isVisible()) {
                    noInternetConnectionDialogFragment.show(fragmentManager, "dialog_fragment_no_internet_connection");
                }
            } else {
                if (!adhellTurnOnDialogFragment.isVisible()) {
                    adhellTurnOnDialogFragment.show(fragmentManager, "dialog_fragment_turn_on_adhell");
                }
            }
        }
    }
}
