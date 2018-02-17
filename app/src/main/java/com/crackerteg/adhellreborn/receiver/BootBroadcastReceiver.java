package com.crackerteg.adhellreborn.receiver;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.crackerteg.adhellreborn.blocker.ContentBlocker;
import com.crackerteg.adhellreborn.blocker.ContentBlocker56;
import com.crackerteg.adhellreborn.blocker.ContentBlocker57;
import com.crackerteg.adhellreborn.utils.BlockedDomainAlarmHelper;
import com.crackerteg.adhellreborn.utils.DeviceAdminInteractor;

public class BootBroadcastReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ContentBlocker contentBlocker = DeviceAdminInteractor.getInstance().getContentBlocker();
        if (contentBlocker != null && contentBlocker.isEnabled() && (contentBlocker instanceof ContentBlocker56
                || contentBlocker instanceof ContentBlocker57)) {
            BlockedDomainAlarmHelper.scheduleAlarm();
        }
//        HeartbeatAlarmHelper.scheduleAlarm();
    }
}
