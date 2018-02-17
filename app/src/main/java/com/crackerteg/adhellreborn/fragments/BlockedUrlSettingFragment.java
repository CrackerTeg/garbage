package com.crackerteg.adhellreborn.fragments;

import android.arch.lifecycle.LifecycleFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.crackerteg.adhellreborn.R;
import com.crackerteg.adhellreborn.blocker.ContentBlocker;
import com.crackerteg.adhellreborn.blocker.ContentBlocker57;
import com.crackerteg.adhellreborn.utils.DeviceAdminInteractor;

public class BlockedUrlSettingFragment extends LifecycleFragment {
    private static final String TAG = BlockedUrlSettingFragment.class.getCanonicalName();
    private ContentBlocker contentBlocker;
    private FragmentManager fragmentManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentBlocker = DeviceAdminInteractor.getInstance().getContentBlocker();
        fragmentManager = getActivity().getSupportFragmentManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blocked_url_settings, container, false);
        Button seeStandardPackageButton = (Button) view.findViewById(R.id.seeStandardPackageButton);
        seeStandardPackageButton.setOnClickListener(v -> {
            Log.d(TAG, "Edit button click in Fragment1");
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, new WhitelistFragment());
            fragmentTransaction.addToBackStack("manage_url_to_manage_standard");
            fragmentTransaction.commit();
        });

        Button addCustomBlockedUrl = (Button) view.findViewById(R.id.addCustomBlockedUrl);
        addCustomBlockedUrl.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, new BlockCustomUrlFragment());
            fragmentTransaction.addToBackStack("manage_url_to_add_custom");
            fragmentTransaction.commit();
        });
        Button showCustomUrlProvidersFragmentButton = (Button) view.findViewById(R.id.showCustomUrlProvidersFragmentButton);
        if (contentBlocker instanceof ContentBlocker57) {
            showCustomUrlProvidersFragmentButton.setOnClickListener(v -> {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, new CustomBlockUrlProviderFragment());
                fragmentTransaction.addToBackStack("manage_custom_url_providers");
                fragmentTransaction.commit();
            });
        } else {
            showCustomUrlProvidersFragmentButton.setVisibility(View.GONE);
        }
        return view;
    }
}
