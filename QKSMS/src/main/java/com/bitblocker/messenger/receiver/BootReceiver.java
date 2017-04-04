package com.bitblocker.messenger.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;

import com.bitblocker.messenger.service.DeleteOldMessagesService;
import com.bitblocker.messenger.transaction.NotificationManager;
import com.bitblocker.messenger.ui.settings.SettingsFragment;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager.initQuickCompose(context, false, false);
        NotificationManager.create(context);

        SettingsFragment.updateAlarmManager(context, PreferenceManager.getDefaultSharedPreferences(context).getBoolean(SettingsFragment.NIGHT_AUTO, false));

        DeleteOldMessagesService.setupAutoDeleteAlarm(context);
    }
}
