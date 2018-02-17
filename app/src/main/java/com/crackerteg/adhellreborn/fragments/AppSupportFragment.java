package com.crackerteg.adhellreborn.fragments;


import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.crackerteg.adhellreborn.R;
import com.crackerteg.adhellreborn.viewmodel.SharedBillingViewModel;

public class AppSupportFragment extends LifecycleFragment {
    private static final String TAG = AppSupportFragment.class.getCanonicalName();
    private TextView supportDevelopmentTextView;
    private Button subscriptionButton;
    private Button goThreeMonthPremium;
    private SharedBillingViewModel sharedBillingViewModel;
    private AppCompatActivity parentActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedBillingViewModel = ViewModelProviders.of(this).get(SharedBillingViewModel.class);
        parentActivity = (AppCompatActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(getString(R.string.app_support_fragment_title));
        if (parentActivity.getSupportActionBar() != null) {
            parentActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            parentActivity.getSupportActionBar().setHomeButtonEnabled(false);
        }
        View view = inflater.inflate(R.layout.fragment_app_support, container, false);

        supportDevelopmentTextView = view.findViewById(R.id.supportDevelopmentTextView);
        subscriptionButton = view.findViewById(R.id.subscriptionButton);
        goThreeMonthPremium = view.findViewById(R.id.goThreeMonthPremium);
        subscriptionButton.setEnabled(false);
        goThreeMonthPremium.setEnabled(false);

        sharedBillingViewModel.billingModel.isSupportedLiveData.observe(this, (isSupported) -> {
            if (isSupported != null && isSupported) {
                sharedBillingViewModel.billingModel.isPremiumLiveData.observe(this, (isPremium) -> {
                    if (isPremium != null && isPremium) {
                        supportDevelopmentTextView.setText(R.string.premium_subscriber_message);
                        subscriptionButton.setText(R.string.already_premium);
                        subscriptionButton.setEnabled(false);
                        subscriptionButton.setVisibility(View.GONE);
                        goThreeMonthPremium.setText(R.string.already_premium);
                        goThreeMonthPremium.setEnabled(false);
                        goThreeMonthPremium.setVisibility(View.GONE);
                    } else {
                        supportDevelopmentTextView.setText(R.string.help_developers_to_keep_up_development);
                        sharedBillingViewModel.billingModel.priceLiveData.observe(this, (text) -> {
                            subscriptionButton.setText(text);
                        });
                        subscriptionButton.setEnabled(true);
                        subscriptionButton.setOnClickListener(v -> {
                            sharedBillingViewModel.startSubscriptionDialog(this.getActivity(), "basic_pro_subs");
                        });

                        sharedBillingViewModel.billingModel.threeMonthPriceLiveData.observe(this, (text) -> {
                            goThreeMonthPremium.setText(text);
                        });
                        goThreeMonthPremium.setEnabled(true);
                        goThreeMonthPremium.setOnClickListener(v -> {
                            sharedBillingViewModel.startSubscriptionDialog(this.getActivity(), "basic_premium_three_months");
                        });
                    }
                });
            } else {
                supportDevelopmentTextView.setText(R.string.subs_not_supported_text_view);
                subscriptionButton.setText(R.string.billing_not_supported);
                subscriptionButton.setEnabled(false);
                goThreeMonthPremium.setText(R.string.billing_not_supported);
                goThreeMonthPremium.setEnabled(false);
                Log.w(TAG, "Billing not supported");
            }
        });
        return view;
    }
}
