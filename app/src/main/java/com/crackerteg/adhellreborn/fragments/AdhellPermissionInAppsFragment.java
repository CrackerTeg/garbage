package com.crackerteg.adhellreborn.fragments;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crackerteg.adhellreborn.R;
import com.crackerteg.adhellreborn.adapter.AdhellPermissionInAppsAdapter;
import com.crackerteg.adhellreborn.db.entity.AppInfo;
import com.crackerteg.adhellreborn.viewmodel.SharedAppPermissionViewModel;

import java.util.ArrayList;
import java.util.List;

public class AdhellPermissionInAppsFragment extends LifecycleFragment {
    private static final String TAG = AdhellPermissionInAppsFragment.class.getCanonicalName();
    private RecyclerView permissionInAppsRecyclerView;
    private SharedAppPermissionViewModel sharedAppPermissionViewModel;
    private List<AppInfo> appInfos;
    private AppCompatActivity parentActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentActivity = (AppCompatActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (parentActivity.getSupportActionBar() != null) {
            parentActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            parentActivity.getSupportActionBar().setHomeButtonEnabled(true);
        }
        sharedAppPermissionViewModel = ViewModelProviders.of(getActivity()).get(SharedAppPermissionViewModel.class);
        View view = inflater.inflate(R.layout.fragment_permission_in_apps, container, false);
        permissionInAppsRecyclerView = view.findViewById(R.id.permissionInAppsRecyclerView);
        permissionInAppsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);
        permissionInAppsRecyclerView.addItemDecoration(itemDecoration);

        sharedAppPermissionViewModel.getSelected().observe(this, permissionInfo -> {
            getActivity().setTitle(permissionInfo.name);
            appInfos = sharedAppPermissionViewModel.loadPermissionsApps(sharedAppPermissionViewModel.installedApps, permissionInfo.name);
            AdhellPermissionInAppsAdapter adhellPermissionInAppsAdapter = new AdhellPermissionInAppsAdapter(this.getContext(), appInfos);
            adhellPermissionInAppsAdapter.currentPermissionName = permissionInfo.name;
            adhellPermissionInAppsAdapter.updateRestrictedPackages();
            permissionInAppsRecyclerView.setAdapter(adhellPermissionInAppsAdapter);
            adhellPermissionInAppsAdapter.notifyDataSetChanged();
        });
        return view;
    }
}
